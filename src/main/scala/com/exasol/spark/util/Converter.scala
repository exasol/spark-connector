package com.exasol.spark.util

import java.sql.PreparedStatement
import java.sql.ResultSet

import org.apache.spark.internal.Logging
import org.apache.spark.sql.Row
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.encoders.RowEncoder
import org.apache.spark.sql.catalyst.expressions.SpecificInternalRow
import org.apache.spark.sql.catalyst.util.DateTimeUtils
import org.apache.spark.sql.types._
import org.apache.spark.unsafe.types.UTF8String

/**
 * A helper class with functions to convert JDBC ResultSet to/from Spark Row
 *
 * Most of the functions here are adapted from Spark JdbcUtils class,
 *  - org/apache/spark/sql/execution/datasources/jdbc/JdbcUtils.scala
 *
 */
object Converter extends Logging {

  /**
   * Converts a [[java.sql.ResultSet]] into an iterator of [[org.apache.spark.sql.Row]]-s
   */
  def resultSetToRows(resultSet: ResultSet, schema: StructType): Iterator[Row] = {
    val encoder = RowEncoder(schema).resolveAndBind()
    val internalRows = resultSetToSparkInternalRows(resultSet, schema)
    internalRows.map(encoder.fromRow)
  }

  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
  def resultSetToSparkInternalRows(
    resultSet: ResultSet,
    schema: StructType
  ): Iterator[InternalRow] = new NextIterator[InternalRow] {
    private[this] val rs = resultSet
    private[this] val getters: Array[JDBCValueGetter] = makeGetters(schema)
    private[this] val mutableRow = new SpecificInternalRow(schema.fields.map(x => x.dataType))

    override protected def close(): Unit =
      try {
        rs.close()
      } catch {
        case e: Exception => logWarning("Exception closing resultset", e)
      }

    override protected def getNext(): InternalRow =
      if (rs.next()) {
        var i = 0
        while (i < getters.length) {
          getters(i).apply(rs, mutableRow, i)
          if (rs.wasNull) mutableRow.setNullAt(i)
          i = i + 1
        }
        mutableRow
      } else {
        finished = true
        null.asInstanceOf[InternalRow] // scalastyle:ignore null
      }
  }

  // A `JDBCValueGetter` is responsible for getting a value from `ResultSet` into a field for
  // `MutableRow`. The last argument `Int` means the index for the value to be set in the row and
  // also used for the value in `ResultSet`.
  private type JDBCValueGetter = (ResultSet, InternalRow, Int) => Unit

  /**
   * Creates `JDBCValueGetter`s according to [[org.apache.spark.sql.types.StructType]], which can
   * set each value from `ResultSet` to each field of
   * [[org.apache.spark.sql.catalyst.InternalRow]] correctly.
   */
  private def makeGetters(schema: StructType): Array[JDBCValueGetter] =
    schema.fields.map(sf => makeGetter(sf.dataType, sf.metadata))

  // scalastyle:off null
  private def makeGetter(dt: DataType, metadata: Metadata): JDBCValueGetter = dt match {
    case BooleanType =>
      (rs: ResultSet, row: InternalRow, pos: Int) =>
        row.setBoolean(pos, rs.getBoolean(pos + 1))

    case DateType =>
      (rs: ResultSet, row: InternalRow, pos: Int) =>
        // DateTimeUtils.fromJavaDate does not handle null value, so we need to check it.
        val dateVal = rs.getDate(pos + 1)
        if (dateVal != null) {
          row.setInt(pos, DateTimeUtils.fromJavaDate(dateVal))
        } else {
          row.update(pos, null)
        }

    case dt: DecimalType =>
      (rs: ResultSet, row: InternalRow, pos: Int) =>
        val decimal = nullSafeConvert[java.math.BigDecimal](
          rs.getBigDecimal(pos + 1),
          d => Decimal(d, dt.precision, dt.scale)
        )
        row.update(pos, decimal)

    case DoubleType =>
      (rs: ResultSet, row: InternalRow, pos: Int) =>
        row.setDouble(pos, rs.getDouble(pos + 1))

    case FloatType =>
      (rs: ResultSet, row: InternalRow, pos: Int) =>
        row.setFloat(pos, rs.getFloat(pos + 1))

    case IntegerType =>
      (rs: ResultSet, row: InternalRow, pos: Int) =>
        row.setInt(pos, rs.getInt(pos + 1))

    case LongType if metadata.contains("binarylong") =>
      (rs: ResultSet, row: InternalRow, pos: Int) =>
        val bytes = rs.getBytes(pos + 1)
        var ans = 0L
        var j = 0
        while (j < bytes.length) {
          ans = 256 * ans + (255 & bytes(j))
          j = j + 1
        }
        row.setLong(pos, ans)

    case LongType =>
      (rs: ResultSet, row: InternalRow, pos: Int) =>
        row.setLong(pos, rs.getLong(pos + 1))

    case ShortType =>
      (rs: ResultSet, row: InternalRow, pos: Int) =>
        row.setShort(pos, rs.getShort(pos + 1))

    case StringType =>
      (rs: ResultSet, row: InternalRow, pos: Int) =>
        row.update(pos, UTF8String.fromString(rs.getString(pos + 1)))

    case TimestampType =>
      (rs: ResultSet, row: InternalRow, pos: Int) =>
        val t = rs.getTimestamp(pos + 1)
        if (t != null) {
          row.setLong(pos, DateTimeUtils.fromJavaTimestamp(t))
        } else {
          row.update(pos, null)
        }

    case BinaryType =>
      (rs: ResultSet, row: InternalRow, pos: Int) =>
        row.update(pos, rs.getBytes(pos + 1))

    case _ =>
      throw new IllegalArgumentException(
        s"Received an unsupported Spark type ${dt.catalogString}"
      )
  }
  // scalastyle:on null

  // scalastyle:off null
  private def nullSafeConvert[T](input: T, f: T => Any): Any =
    if (input == null) {
      null
    } else {
      f(input)
    }
  // scalastyle:on null

  // A `JDBCValueSetter` is responsible for setting a value from `Row` into a field for
  // `PreparedStatement`. The last argument `Int` means the index for the value to be set in the
  // SQL statement and also used for the value in `Row`.
  private[spark] type JDBCValueSetter = (PreparedStatement, Row, Int) => Unit

  private[spark] def makeSetter(dataType: DataType): JDBCValueSetter = dataType match {
    case IntegerType =>
      (stmt: PreparedStatement, row: Row, pos: Int) =>
        stmt.setInt(pos + 1, row.getInt(pos))

    case LongType =>
      (stmt: PreparedStatement, row: Row, pos: Int) =>
        stmt.setLong(pos + 1, row.getLong(pos))

    case DoubleType =>
      (stmt: PreparedStatement, row: Row, pos: Int) =>
        stmt.setDouble(pos + 1, row.getDouble(pos))

    case FloatType =>
      (stmt: PreparedStatement, row: Row, pos: Int) =>
        stmt.setFloat(pos + 1, row.getFloat(pos))

    case ShortType =>
      (stmt: PreparedStatement, row: Row, pos: Int) =>
        stmt.setShort(pos + 1, row.getShort(pos))

    case ByteType =>
      (stmt: PreparedStatement, row: Row, pos: Int) =>
        stmt.setByte(pos + 1, row.getByte(pos))

    case BooleanType =>
      (stmt: PreparedStatement, row: Row, pos: Int) =>
        stmt.setBoolean(pos + 1, row.getBoolean(pos))

    case StringType =>
      (stmt: PreparedStatement, row: Row, pos: Int) =>
        stmt.setString(pos + 1, row.getString(pos))

    case BinaryType =>
      (stmt: PreparedStatement, row: Row, pos: Int) =>
        stmt.setBytes(pos + 1, row.getAs[Array[Byte]](pos))

    case TimestampType =>
      (stmt: PreparedStatement, row: Row, pos: Int) =>
        stmt.setTimestamp(pos + 1, row.getAs[java.sql.Timestamp](pos))

    case DateType =>
      (stmt: PreparedStatement, row: Row, pos: Int) =>
        stmt.setDate(pos + 1, row.getAs[java.sql.Date](pos))

    case _: DecimalType =>
      (stmt: PreparedStatement, row: Row, pos: Int) =>
        stmt.setBigDecimal(pos + 1, row.getDecimal(pos))

    case _ =>
      (_: PreparedStatement, _: Row, pos: Int) =>
        throw new IllegalArgumentException(s"Cannot translate non-null value for field $pos")
  }

}

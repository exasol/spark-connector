package com.exasol.spark.util

import java.sql.PreparedStatement

import org.apache.spark.internal.Logging
import org.apache.spark.sql.Row
import org.apache.spark.sql.types._

import com.exasol.errorreporting.ExaError

/**
 * A helper object with functions to convert JDBC [[java.sql.ResultSet]] into
 * Spark [[org.apache.spark.sql.Row]] or vice versa.
 *
 * Most of the functions here are adapted from
 * `spark/sql/execution/datasources/jdbc/JdbcUtils.scala` class.
 */
object Converter extends Logging {

  // A `JDBCValueSetter` is responsible for setting a value from `Row` into a
  // field for `PreparedStatement`. The last argument `Int` means the index for
  // the value to be set in the SQL statement and also used for the value in
  // `Row`.
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
        throw new IllegalArgumentException(
          ExaError
            .messageBuilder("F-SEC-10")
            .message(
              "Could not find matching data type for position {{POSITION}}.",
              String.valueOf(pos)
            )
            .ticketMitigation()
            .toString()
        )
  }

}

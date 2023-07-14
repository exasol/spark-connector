package com.exasol.spark.util

import java.sql.ResultSetMetaData

import org.apache.spark.internal.Logging
import org.apache.spark.sql.types._

import com.exasol.errorreporting.ExaError

/**
 * A helper class with mapping functions between Exasol JDBC types and Spark SQL
 * types.
 */
object Types extends Logging {

  private val MAX_PRECISION_EXASOL: Int = 36
  private val MAX_SCALE_EXASOL: Int = 36

  val LongDecimal: DecimalType = DecimalType(20, 0)

  /**
   * Given a [[java.sql.ResultSetMetaData]] returns a Spark
   * [[org.apache.spark.sql.types.StructType]] schema.
   *
   * @param rsmd A result set metadata
   * @return A StructType matching result set types
   */
  def createSparkStructType(rsmd: ResultSetMetaData): StructType = {
    val columnCnt = rsmd.getColumnCount
    val fields = new Array[StructField](columnCnt)
    var idx = 0
    while (idx < columnCnt) {
      val columnName = rsmd.getColumnLabel(idx + 1)
      val columnDataType = rsmd.getColumnType(idx + 1)
      val columnPrecision = rsmd.getPrecision(idx + 1)
      val columnScale = rsmd.getScale(idx + 1)
      val isSigned = rsmd.isSigned(idx + 1)
      val isNullable = rsmd.isNullable(idx + 1) != ResultSetMetaData.columnNoNulls

      val columnType =
        createSparkTypeFromSQLType(columnDataType, columnPrecision, columnScale, isSigned)

      fields(idx) = StructField(columnName, columnType, isNullable)
      idx += 1
    }
    new StructType(fields)
  }

  /**
   * Maps a JDBC type [[java.sql.Types$]] to a Spark SQL
   * [[org.apache.spark.sql.types.DataType]].
   *
   * @param sqlType A JDBC type from [[java.sql.ResultSetMetaData]] column type
   * @param precision A precision value obtained from ResultSetMetaData,
   *        `rsmd.getPrecision(index)`
   * @param scale A scale value obtained from ResultSetMetaData,
   *        `rsmd.getScale(index)`
   * @param isSigned A isSigned value obtained from ResultSetMetaData,
   *        `rsmd.isSigned(index)`
   * @return A Spark SQL DataType corresponding to JDBC SQL type
   */
  def createSparkTypeFromSQLType(
    sqlType: Int,
    precision: Int,
    scale: Int,
    isSigned: Boolean
  ): DataType = sqlType match {
    // Numbers
    case java.sql.Types.TINYINT  => ShortType
    case java.sql.Types.SMALLINT => ShortType
    case java.sql.Types.INTEGER =>
      if (isSigned) {
        IntegerType
      } else {
        LongType
      }
    case java.sql.Types.BIGINT =>
      if (isSigned) {
        LongType
      } else {
        LongDecimal
      }
    case java.sql.Types.DECIMAL =>
      if (precision != 0 || scale != 0) {
        boundedDecimal(precision, scale)
      } else {
        DecimalType.SYSTEM_DEFAULT
      }
    case java.sql.Types.NUMERIC =>
      if (precision != 0 || scale != 0) {
        boundedDecimal(precision, scale)
      } else {
        DecimalType.SYSTEM_DEFAULT
      }
    case java.sql.Types.DOUBLE => DoubleType
    case java.sql.Types.FLOAT  => DoubleType
    case java.sql.Types.REAL   => FloatType

    // Stings
    case java.sql.Types.CHAR         => StringType
    case java.sql.Types.NCHAR        => StringType
    case java.sql.Types.VARCHAR      => StringType
    case java.sql.Types.NVARCHAR     => StringType
    case java.sql.Types.LONGVARCHAR  => StringType
    case java.sql.Types.LONGNVARCHAR => StringType

    // Binaries
    case java.sql.Types.BINARY        => BinaryType
    case java.sql.Types.VARBINARY     => BinaryType
    case java.sql.Types.LONGVARBINARY => BinaryType

    // Booleans
    case java.sql.Types.BIT     => BooleanType
    case java.sql.Types.BOOLEAN => BooleanType

    // Datetime
    case java.sql.Types.DATE      => DateType
    case java.sql.Types.TIME      => TimestampType
    case java.sql.Types.TIMESTAMP => TimestampType

    // Others
    case java.sql.Types.ROWID  => LongType
    case java.sql.Types.STRUCT => StringType
  }

  /**
   * Bounds DecimalType within Spark [[DecimalType.MAX_PRECISION]] and
   * [[DecimalType.MAX_SCALE]] values.
   */
  private[this] def boundedDecimal(precision: Int, scale: Int): DecimalType =
    DecimalType(
      math.min(math.min(precision, DecimalType.MAX_PRECISION), MAX_PRECISION_EXASOL),
      math.min(math.min(scale, DecimalType.MAX_SCALE), MAX_SCALE_EXASOL)
    )

  /**
   * Returns corresponding Jdbc [[java.sql.Types$]] type given Spark
   * [[org.apache.spark.sql.types.DataType]] type.
   *
   * @param dataType A Spark DataType (e.g.
   *        [[org.apache.spark.sql.types.StringType$]])
   * @return A default JdbcType for this DataType
   */
  def jdbcTypeFromSparkDataType(dataType: DataType): Int = dataType match {
    case IntegerType    => java.sql.Types.INTEGER
    case LongType       => java.sql.Types.BIGINT
    case DoubleType     => java.sql.Types.DOUBLE
    case FloatType      => java.sql.Types.FLOAT
    case ShortType      => java.sql.Types.SMALLINT
    case ByteType       => java.sql.Types.TINYINT
    case BooleanType    => java.sql.Types.BIT
    case StringType     => java.sql.Types.VARCHAR
    case BinaryType     => java.sql.Types.BLOB
    case TimestampType  => java.sql.Types.TIMESTAMP
    case DateType       => java.sql.Types.DATE
    case _: DecimalType => java.sql.Types.DECIMAL
    case _              => throw new IllegalArgumentException(getUnsupportedDataTypeErrorCode(dataType))
  }

  /**
   * Returns corresponding Exasol type as a string for a given Spark
   * [[org.apache.spark.sql.types.DataType]] type.
   *
   * @param dataType A Spark DataType (e.g.
   *        [[org.apache.spark.sql.types.StringType$]])
   * @return A default Exasol type as string for this DataType
   */
  def exasolTypeFromSparkDataType(dataType: DataType): String = dataType match {
    case ShortType       => "SMALLINT"
    case ByteType        => "TINYINT"
    case IntegerType     => "INTEGER"
    case LongType        => "BIGINT"
    case DoubleType      => "DOUBLE"
    case FloatType       => "FLOAT"
    case dt: DecimalType => convertSparkPrecisionScaleToExasol(dt)
    case BooleanType     => "BOOLEAN"
    case StringType      => "CLOB"
    case BinaryType      => "CLOB"
    case DateType        => "DATE"
    case TimestampType   => "TIMESTAMP"
    case _               => throw new IllegalArgumentException(getUnsupportedDataTypeErrorCode(dataType))
  }

  private[this] def getUnsupportedDataTypeErrorCode(dataType: DataType): String =
    ExaError
      .messageBuilder("F-SEC-8")
      .message("Spark data type {{DATA_TYPE}} is not supported.", String.valueOf(dataType))
      .ticketMitigation()
      .toString()

  /**
   * Convert Spark Type with Decimal precision,scale to Exasol type.
   *
   * For example:
   * {{{
   *    Spark.DecimalType(5,2) -> "DECIMAL(5,2)"
   * }}}
   *
   * Exasol has a max scale, precision of 36. Spark precision/scale greater than
   * 36 will be truncated.
   *
   * @param decimalType A Spark DecimalType with precision and scale
   * @return The equivalent Exasol type
   */
  def convertSparkPrecisionScaleToExasol(decimalType: DecimalType): String = {
    val boundedType = boundedDecimal(decimalType.precision, decimalType.scale)
    "DECIMAL(" + boundedType.precision.toString + "," + boundedType.scale.toString + ")"
  }

  /**
   * Select only required columns from Spark SQL schema.
   *
   * Adapted from Spark JDBCRDD private function `pruneSchema`.
   *
   * @param columns A list of required columns
   * @param schema A Spark SQL schema
   * @return A new Spark SQL schema with only columns in the order of column
   *         names
   */
  def selectColumns(columns: Array[String], schema: StructType): StructType = {
    val fieldsNames = schema.fieldNames.toSet
    val newFields = columns.filter(fieldsNames.contains(_)).map(schema(_))
    val newSchema = StructType(newFields)
    logDebug(s"Using a new pruned schema $newSchema")
    newSchema
  }

  /**
   * Returns comma separated column name and column types for Exasol
   * table from Spark schema.
   *
   * It skips the `NOT NULL` constraint if the Spark dataframe schema
   * type is a [[org.apache.spark.sql.types.StringType$]] type.
   *
   * @param schema A Spark [[org.apache.spark.sql.types.StructType]] schema
   * @return A comma separated column names and their types
   */
  def createTableSchema(schema: StructType): String =
    schema.fields
      .map { field =>
        val fieldType = Types.exasolTypeFromSparkDataType(field.dataType)
        val nameType = s"${field.name} $fieldType"
        field.nullable match {
          case true                                  => nameType
          case false if field.dataType != StringType => nameType + " NOT NULL"
          case false                                 => nameType
        }
      }
      .mkString(", ")

  def getMaxPrecisionExasol(): Int =
    MAX_PRECISION_EXASOL

  def getMaxScaleExasol(): Int =
    MAX_SCALE_EXASOL

}

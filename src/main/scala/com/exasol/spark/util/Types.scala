package com.exasol.spark.util

import java.sql.ResultSet
import java.sql.ResultSetMetaData

import org.apache.spark.sql.types._

object Types {

  val BIGINT_DECIMAL_PRECISION: Int = 20
  val BIGINT_DECIMAL_SCALE: Int = 0

  val SPARK_DECIMAL_PRECISION: Int = 38
  val SPARK_DECIMAL_SCALE: Int = 18

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
        DecimalType(BIGINT_DECIMAL_PRECISION, BIGINT_DECIMAL_SCALE)
      }
    case java.sql.Types.DECIMAL =>
      if (precision != 0 || scale != 0) {
        DecimalType(precision, scale)
      } else {
        DecimalType(SPARK_DECIMAL_PRECISION, SPARK_DECIMAL_SCALE)
      }
    case java.sql.Types.NUMERIC =>
      if (precision != 0 || scale != 0) {
        DecimalType(precision, scale)
      } else {
        DecimalType(SPARK_DECIMAL_PRECISION, SPARK_DECIMAL_SCALE)
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
    case _ =>
      throw new IllegalArgumentException("Received an unsupported type " + sqlType)
  }

}

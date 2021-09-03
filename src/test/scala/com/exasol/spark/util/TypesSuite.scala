package com.exasol.spark.util

import org.apache.spark.sql.types._

import com.exasol.spark.util.Types._

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class TypesSuite extends AnyFunSuite with Matchers {

  test("test of Spark Decimal types to Exasol Decimal types") {
    assert(exasolTypeFromSparkDataType(DecimalType.apply(5, 2)) === "DECIMAL(5,2)")
    assert(exasolTypeFromSparkDataType(DecimalType.SYSTEM_DEFAULT) === "DECIMAL(36,18)")
    assert(exasolTypeFromSparkDataType(DecimalType.apply(38, 37)) === "DECIMAL(36,36)")
  }

  test("Spark types to JDBC types conversion") {
    val data: Map[DataType, Int] = Map(
      IntegerType -> java.sql.Types.INTEGER,
      LongType -> java.sql.Types.BIGINT,
      DoubleType -> java.sql.Types.DOUBLE,
      FloatType -> java.sql.Types.FLOAT,
      ShortType -> java.sql.Types.SMALLINT,
      ByteType -> java.sql.Types.TINYINT,
      BooleanType -> java.sql.Types.BIT,
      StringType -> java.sql.Types.VARCHAR,
      BinaryType -> java.sql.Types.BLOB,
      TimestampType -> java.sql.Types.TIMESTAMP,
      DateType -> java.sql.Types.DATE,
      DecimalType(18, 0) -> java.sql.Types.DECIMAL
    )
    data.foreach { case (given, expected) =>
      assert(jdbcTypeFromSparkDataType(given) === expected)
    }
  }

  test("Spark types to JDBC types conversion throws for unsupported type") {
    val thrown = intercept[IllegalArgumentException] {
      jdbcTypeFromSparkDataType(MapType(StringType, IntegerType))
    }
    val message = thrown.getMessage()
    assert(message.startsWith("F-SEC-8"))
    assert(message.contains("'MapType(StringType,IntegerType,true)' is not supported"))
  }

  test("Spark types to Exasol types conversion") {
    val data: Map[DataType, String] = Map(
      ShortType -> "SMALLINT",
      ByteType -> "TINYINT",
      IntegerType -> "INTEGER",
      LongType -> "BIGINT",
      DoubleType -> "DOUBLE",
      FloatType -> "FLOAT",
      BooleanType -> "BOOLEAN",
      StringType -> "CLOB",
      BinaryType -> "CLOB",
      DateType -> "DATE",
      TimestampType -> "TIMESTAMP"
    )
    data.foreach { case (given, expected) =>
      assert(exasolTypeFromSparkDataType(given) === expected)
    }
  }

  test("Spark types to Exasol types conversion throws for unsupported type") {
    val thrown = intercept[IllegalArgumentException] {
      exasolTypeFromSparkDataType(ArrayType(FloatType))
    }
    val message = thrown.getMessage()
    assert(message.startsWith("F-SEC-8"))
    assert(message.contains("Spark data type 'ArrayType(FloatType,true)' is not supported"))
  }

  test("test of Int type conversion") {
    assert(createSparkTypeFromSQLType(java.sql.Types.TINYINT, 0, 0, false) === ShortType)
    assert(createSparkTypeFromSQLType(java.sql.Types.SMALLINT, 0, 0, false) === ShortType)

    assert(createSparkTypeFromSQLType(java.sql.Types.INTEGER, 0, 0, true) === IntegerType)
    assert(createSparkTypeFromSQLType(java.sql.Types.INTEGER, 0, 0, false) === LongType)
  }

  test("test of DECIMAL and NUMERIC type conversion") {
    // DECIMAL
    assert(
      createSparkTypeFromSQLType(java.sql.Types.DECIMAL, 0, 0, true)
        === DecimalType.SYSTEM_DEFAULT
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.DECIMAL, 0, 0, false)
        === DecimalType.SYSTEM_DEFAULT
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.DECIMAL, 4, 1, false)
        === DecimalType(4, 1)
    )

    assert(
      createSparkTypeFromSQLType(java.sql.Types.DECIMAL, 36, 10, false)
        === DecimalType(getMaxPrecisionExasol(), 10)
    )

    assert(
      createSparkTypeFromSQLType(java.sql.Types.DECIMAL, 36, 36, false)
        === DecimalType(getMaxPrecisionExasol(), getMaxScaleExasol())
    )

    // NUMERIC
    assert(
      createSparkTypeFromSQLType(java.sql.Types.NUMERIC, 0, 0, true)
        === DecimalType.SYSTEM_DEFAULT
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.NUMERIC, 0, 0, false)
        === DecimalType.SYSTEM_DEFAULT
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.NUMERIC, 4, 1, false)
        === DecimalType(4, 1)
    )
  }

  test("test of DOUBLE, FLOAT and REAL type conversion") {
    assert(
      createSparkTypeFromSQLType(java.sql.Types.DOUBLE, 0, 0, false)
        === DoubleType
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.FLOAT, 0, 0, false)
        === DoubleType
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.REAL, 0, 0, false)
        === FloatType
    )
  }

  test("test of Stings type conversion") {
    assert(
      createSparkTypeFromSQLType(java.sql.Types.CHAR, 0, 0, false)
        === StringType
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.NCHAR, 0, 0, false)
        === StringType
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.VARCHAR, 0, 0, false)
        === StringType
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.NVARCHAR, 0, 0, false)
        === StringType
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.LONGVARCHAR, 0, 0, false)
        === StringType
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.LONGNVARCHAR, 0, 0, false)
        === StringType
    )
  }

  test("test of Binaries type conversion") {
    assert(
      createSparkTypeFromSQLType(java.sql.Types.BINARY, 0, 0, false)
        === BinaryType
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.VARBINARY, 0, 0, false)
        === BinaryType
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.LONGVARBINARY, 0, 0, false)
        === BinaryType
    )
  }

  test("test of Booleans type conversion") {
    assert(
      createSparkTypeFromSQLType(java.sql.Types.BIT, 0, 0, false)
        === BooleanType
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.BOOLEAN, 0, 0, false)
        === BooleanType
    )
  }

  test("test of Datetime type conversion") {
    assert(
      createSparkTypeFromSQLType(java.sql.Types.DATE, 0, 0, false)
        === DateType
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.TIME, 0, 0, false)
        === TimestampType
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.TIMESTAMP, 0, 0, false)
        === TimestampType
    )
  }

  test("test of Others type conversion") {
    assert(
      createSparkTypeFromSQLType(java.sql.Types.ROWID, 0, 0, false)
        === LongType
    )
    assert(
      createSparkTypeFromSQLType(java.sql.Types.STRUCT, 0, 0, false)
        === StringType
    )
  }

  test("createTableSchema should create a comma separated column names and types") {
    val schema: StructType = StructType(
      Seq(
        StructField("bool_col", BooleanType),
        StructField("str_col", StringType),
        StructField("int_col", IntegerType, false),
        StructField("float_col", FloatType),
        StructField("double_col", DoubleType),
        StructField("date_col", DateType),
        StructField("timestamp_col", TimestampType)
      )
    )

    val expectedStr =
      "bool_col BOOLEAN, str_col CLOB, int_col INTEGER NOT NULL, float_col FLOAT," +
        " double_col DOUBLE, date_col DATE, timestamp_col TIMESTAMP"

    assert(createTableSchema(schema) === expectedStr)
  }

  test("createTableSchema returns string type without not null constraint") {
    val schema = StructType(
      Seq(
        StructField("str_col", StringType),
        StructField("text_col", StringType, false)
      )
    )
    val expectedConvertedSchema = "str_col CLOB, text_col CLOB"
    assert(createTableSchema(schema) === expectedConvertedSchema)
  }

}

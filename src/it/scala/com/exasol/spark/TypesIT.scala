package com.exasol.spark

import org.apache.spark.sql.types._

import com.exasol.spark.util.Types._

class TypesIT extends AbstractTableQueryIT {

  private[this] val schema = "TYPES"

  override val tableName = s"$schema.TEST_TABLE"
  override def createTable(): Unit = {
    val maxDecimal = " DECIMAL(" + getMaxPrecisionExasol() + "," + getMaxScaleExasol() + ")"
    exasolConnectionManager.withExecute(
      Seq(
        s"DROP SCHEMA IF EXISTS $schema CASCADE",
        s"CREATE SCHEMA $schema",
        s"""|CREATE OR REPLACE TABLE $tableName (
            |   MYID INTEGER,
            |   MYTINYINT DECIMAL(3,0),
            |   MYSMALLINT DECIMAL(9,0),
            |   MYBIGINT DECIMAL(36,0),
            |   MYDECIMALSystemDefault DECIMAL,
            |   MYDECIMALMAX $maxDecimal,
            |   MYNUMERIC DECIMAL( 5,2 ),
            |   MYDOUBLE DOUBLE PRECISION,
            |   MYCHAR CHAR,
            |   MYNCHAR CHAR(2000),
            |   MYLONGVARCHAR VARCHAR( 2000000),
            |   MYBOOLEAN BOOLEAN,
            |   MYDATE DATE,
            |   MYTIMESTAMP TIMESTAMP,
            |   MYGEOMETRY Geometry,
            |   MYINTERVAL INTERVAL YEAR TO MONTH
            |)""".stripMargin,
        "commit"
      )
    )
  }

  test("converts Exasol types to Spark") {
    val schemaFields = getDataFrame().schema.toList
    val schemaExpected = Map(
      "MYID" -> LongType,
      "MYTINYINT" -> ShortType,
      "MYSMALLINT" -> IntegerType,
      "MYBIGINT" -> DecimalType(36, 0),
      "MYDECIMALMAX" -> DecimalType(36, 36),
      "MYDECIMALSYSTEMDEFAULT" -> LongType,
      "MYNUMERIC" -> DecimalType(5, 2),
      "MYDOUBLE" -> DoubleType,
      "MYCHAR" -> StringType,
      "MYNCHAR" -> StringType,
      "MYLONGVARCHAR" -> StringType,
      "MYBOOLEAN" -> BooleanType,
      "MYDATE" -> DateType,
      "MYTIMESTAMP" -> TimestampType,
      "MYGEOMETRY" -> StringType,
      "MYINTERVAL" -> StringType
    )
    schemaFields.foreach {
      case field =>
        assert(field.dataType === schemaExpected.get(field.name).get)
    }
  }

}

package com.exasol.spark

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

/**
 * Tests for loading data from Exasol query as dataframes using short
 * and long source formats.
 */
class LoadIT extends BaseTableQueryIT {

  test("runs dataframe show action") {
    val df = getDataFrame()
    df.show(10, false)
  }

  test("runs dataframe take action") {
    assert(getDataFrame().take(2).size === 2)
  }

  test("runs dataframe count action") {
    assert(getDataFrame().count === 3)
  }

  test("runs dataframe collect action") {
    val cities = getDataFrame().collect().map(_.getAs[String]("CITY"))
    assert(cities === Seq("Berlin", "Paris", "Lisbon"))
  }

  test("runs dataframe queries twice") {
    val count = getDataFrame().count
    assert(getDataFrame().count === count)
  }

  test("returns dataframe schema names") {
    val schemaNames = getDataFrame().schema.map(_.name)
    assert(schemaNames === Seq("ID", "NAME", "CITY", "DATE_INFO", "UNICODE_COL", "UPDATED_AT"))
  }

  test("returns dataframe schema types") {
    val dataTypes = getDataFrame().schema.map(_.dataType)
    val expectedDataTypes =
      Seq(LongType, StringType, StringType, DateType, StringType, TimestampType)
    assert(dataTypes === expectedDataTypes)
  }

  test("throws if query parameter is not provided") {
    val thrown = intercept[UnsupportedOperationException] {
      spark.read
        .format("com.exasol.spark")
        .option("host", jdbcHost)
        .option("port", jdbcPort)
        .load()
    }
    val expectedMessage = "A query parameter should be specified in order to run the operation"
    assert(thrown.getMessage === expectedMessage)
  }

  test("returns columns from user provided schema") {
    val expectedSchema = new StructType()
      .add("NAME", StringType)
      .add("UPDATED_AT", TimestampType)
    val df = spark.read
      .format("exasol")
      .option("host", jdbcHost)
      .option("port", jdbcPort)
      .option("query", s"SELECT * FROM $tableName")
      .schema(expectedSchema)
      .load()
    assert(df.schema.length === expectedSchema.length)
    assert(df.schema.map(_.name) === expectedSchema.map(_.name))
    assert(df.schema.map(_.dataType) === expectedSchema.map(_.dataType))
    assert(df.collect().map(x => x.getString(0)) === Seq("Germany", "France", "Portugal"))
  }

  test("throws if user provided schema mismatch") {
    val expectedSchema = new StructType()
      .add("NAME", StringType)
      .add("UPDATED_AT", TimestampType)
      .add("DATE_INFORMATION", DateType)
    val df = spark.read
      .format("exasol")
      .option("host", jdbcHost)
      .option("port", jdbcPort)
      .option("query", s"SELECT * FROM $tableName")
      .schema(expectedSchema)
      .load()
      .select("DATE_INFORMATION")
    val thrown = intercept[java.sql.SQLException] {
      df.show(10, false)
    }
    assert(thrown.getMessage().contains("""object "DATE_INFORMATION" not found"""))
  }

  test("uses user provided SparkConf") {
    val sparkConf = new SparkConf()
      .setMaster("local[*]")
      .set("spark.exasol.host", jdbcHost)
      .set("spark.exasol.port", jdbcPort)
      .set("spark.exasol.max_nodes", "200")
    val sparkSession = SparkSession
      .builder()
      .config(sparkConf)
      .getOrCreate()
    val df = sparkSession.read
      .format("exasol")
      .option("query", s"SELECT CITY FROM $tableName")
      .option("port", "falsePortNumber")
      .option("host", "falseHostName")
      .load()
    assert(df.count() === 3)
  }

  test("returns unicode columns") {
    val df = spark.read
      .format("exasol")
      .option("host", jdbcHost)
      .option("port", jdbcPort)
      .option("query", s"""SELECT "UNICODE_COL" FROM $tableName WHERE UNICODE_COL IS NOT NULL""")
      .load()
    assert(df.count() === 3)
    assert(df.collect().map(_(0)) === Seq("öäüß", "Ö", "Ù")) // scalastyle:ignore nonascii
  }

}

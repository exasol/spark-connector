package com.exasol.spark

import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.TimestampType

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.FunSuite

/** Tests for quering an Exasol tables with reserved keywords */
class ReservedKeywordsSuite extends FunSuite with BaseDockerSuite with DataFrameSuiteBase {

  val SCHEMA: String = "RESERVED_KEYWORDS"
  val TABLE: String = "TEST_TABLE"

  test("queries a table with reserved keyword") {
    createTable()

    val expected = Set("True", "False", "Checked")

    val df1 = spark.read
      .format("exasol")
      .option("host", container.host)
      .option("port", s"${container.port}")
      .option("query", s"""SELECT "CONDITION" FROM $SCHEMA.$TABLE""")
      .load()

    assert(df1.collect().map(x => x(0)).toSet === expected)

    val df2 = spark.read
      .format("exasol")
      .option("host", container.host)
      .option("port", s"${container.port}")
      .option("query", s"SELECT * FROM $SCHEMA.$TABLE")
      .load()
      .select("condition")

    assert(df2.collect().map(x => x(0)).toSet === expected)
  }

  def createTable(): Unit = {
    runExaQuery(s"DROP SCHEMA IF EXISTS $SCHEMA CASCADE")
    runExaQuery(s"CREATE SCHEMA $SCHEMA")
    runExaQuery(s"""
                   |CREATE OR REPLACE TABLE $SCHEMA.$TABLE (
                   |   ID INTEGER IDENTITY NOT NULL,
                   |   "CONDITION" VARCHAR(100) UTF8
                   |)""".stripMargin)
    runExaQuery(s"""INSERT INTO $SCHEMA.$TABLE ("CONDITION") VALUES ('True')""")
    runExaQuery(s"""INSERT INTO $SCHEMA.$TABLE ("CONDITION") VALUES ('False')""")
    runExaQuery(s"""INSERT INTO $SCHEMA.$TABLE ("CONDITION") VALUES ('Checked')""")
    runExaQuery("commit")
  }

}

package com.exasol.spark

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.funsuite.AnyFunSuite

/** Tests for quering an Exasol tables with reserved keywords */
class ReservedKeywordsSuite extends AnyFunSuite with BaseDockerSuite with DataFrameSuiteBase {

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

  ignore("queries a table with reserved keyword using where clause") {
    createTable()

    val df = spark.read
      .format("com.exasol.spark")
      .option("host", container.host)
      .option("port", s"${container.port}")
      .option("query", s"SELECT * FROM $SCHEMA.$TABLE")
      .load()
      .select(s""""CONDITION"""")
      .where(s""""CONDITION" LIKE '%Check%'""")

    assert(df.collect().map(x => x(0)).toSet === Set("Checked"))
  }

  def createTable(): Unit =
    exaManager.withExecute(
      Seq(
        s"DROP SCHEMA IF EXISTS $SCHEMA CASCADE",
        s"CREATE SCHEMA $SCHEMA",
        s"""|CREATE OR REPLACE TABLE $SCHEMA.$TABLE (
            |   ID INTEGER IDENTITY NOT NULL,
            |   "CONDITION" VARCHAR(100) UTF8
            |)""".stripMargin,
        s"""INSERT INTO $SCHEMA.$TABLE ("CONDITION") VALUES ('True')""",
        s"""INSERT INTO $SCHEMA.$TABLE ("CONDITION") VALUES ('False')""",
        s"""INSERT INTO $SCHEMA.$TABLE ("CONDITION") VALUES ('Checked')""",
        "commit"
      )
    )

}

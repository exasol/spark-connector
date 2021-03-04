package com.exasol.spark

/**
 * Tests for querying quoted columns from an Exasol tables.
 */
class QuotedColumnsIT extends AbstractTableQueryIT {

  private[this] val expected = Seq("Blue", "Red", "Orange")
  private[this] val schema = "QUOTED_COLUMNS"

  override val tableName = s"$schema.TEST_TABLE"
  override def createTable(): Unit =
    exasolConnectionManager.withExecute(
      Seq(
        s"DROP SCHEMA IF EXISTS $schema CASCADE",
        s"CREATE SCHEMA $schema",
        s"""|CREATE OR REPLACE TABLE $tableName (
            |   ID INTEGER IDENTITY NOT NULL,
            |   "ty_Pe" VARCHAR(10) UTF8
            |)""".stripMargin,
        s"""INSERT INTO $tableName ("ty_Pe") VALUES ('Blue')""",
        s"""INSERT INTO $tableName ("ty_Pe") VALUES ('Red')""",
        s"""INSERT INTO $tableName ("ty_Pe") VALUES ('Orange')""",
        "commit"
      )
    )

  test("returns dataframe with quoted column in a select star") {
    val df = getDataFrame(Option(s"""SELECT * FROM $tableName"""))
    assert(df.collect().map(x => x(1)) === expected)
  }

  test("returns dataframe with quoted column in a query") {
    val df = getDataFrame(Option(s"""SELECT "ty_Pe" FROM $tableName"""))
    assert(df.collect().map(x => x(0)) === expected)
  }

  test("returns dataframe with quoted column in a select") {
    val df = getDataFrame(Option(s"SELECT * FROM $tableName")).select("ty_Pe")
    assert(df.collect().map(x => x(0)) === expected)
  }

  test("returns dataframe with quoted column in a where predicate") {
    val df = getDataFrame(Option(s"SELECT * FROM $tableName")).where("ty_Pe LIKE '%ran%'")
    assert(df.collect().map(x => x(1)) === Seq("Orange"))
  }

}

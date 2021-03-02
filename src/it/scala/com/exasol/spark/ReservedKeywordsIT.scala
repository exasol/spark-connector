package com.exasol.spark

/**
 * Tests for querying an Exasol tables with reserved keywords.
 */
class ReservedKeywordsIT extends AbstractTableQueryIT {

  private[this] val expected = Seq("True", "False", "Checked")
  private[this] val schema = "RESERVED_KEYWORDS"

  override val tableName = s"$schema.TEST_TABLE"
  override def createTable(): Unit =
    connectionManager.withExecute(
      Seq(
        s"DROP SCHEMA IF EXISTS $schema CASCADE",
        s"CREATE SCHEMA $schema",
        s"""|CREATE OR REPLACE TABLE $tableName (
            |   ID INTEGER IDENTITY NOT NULL,
            |   "CONDITION" VARCHAR(100) UTF8
            |)""".stripMargin,
        s"""INSERT INTO $tableName ("CONDITION") VALUES ('True')""",
        s"""INSERT INTO $tableName ("CONDITION") VALUES ('False')""",
        s"""INSERT INTO $tableName ("CONDITION") VALUES ('Checked')""",
        "commit"
      )
    )

  test("returns dataframe with reserved keyword in a select star") {
    val df = getDataFrame(Option(s"""SELECT * FROM $tableName"""))
    assert(df.collect().map(x => x(1)) === expected)
  }

  test("returns dataframe with reserved keyword in a query") {
    val df = getDataFrame(Option(s"""SELECT "CONDITION" FROM $tableName"""))
    assert(df.collect().map(x => x(0)) === expected)
  }

  test("returns dataframe with reserved keyword in a column select") {
    val df = getDataFrame(Option(s"SELECT * FROM $tableName")).select("condition")
    assert(df.collect().map(x => x(0)) === expected)
  }

  test("returns dataframe with reserved keyword in a where predicate") {
    val df = getDataFrame(Option(s"SELECT * FROM $tableName"))
      .select("CONDITION")
      .where("CONDITION LIKE '%Check%'")
    assert(df.collect().map(x => x(0)) === Seq("Checked"))
  }

}

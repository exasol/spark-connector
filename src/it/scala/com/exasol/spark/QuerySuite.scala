package com.exasol.spark

import org.scalatest.FunSuite
import com.holdenkarau.spark.testing.DataFrameSuiteBase

/** Read Exasol query as dataframes using the Spark DataSource / SQL API */
class QuerySuite extends FunSuite with DataFrameSuiteBase with BaseSuite {

  val exa_schema = "test_schema"
  val exa_table = "test_table"

  test("creates dataframe from user query") {
    val df = spark.read
      .format("com.exasol.spark")
      .option("query", s"SELECT * FROM $exa_schema.$exa_table")
      .load()

    assert(df.count == 3)
    assert(df.schema.exists(f => f.name == "name"))
  }

  test("creates dataframe from user query with short name") {
    val df = spark.read
      .format("exasol")
      .option("query", s"SELECT * FROM $exa_schema.$exa_table")
      .load()

    assert(df.count == 3)
    assert(df.schema.exists(f => f.name == "name"))
  }

}

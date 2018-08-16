package com.exasol.spark

import org.scalatest.FunSuite
import com.holdenkarau.spark.testing.DataFrameSuiteBase

/** Tests for loading data from Exasol query as dataframes using short and long source formats */
class LoadSuite extends FunSuite with DataFrameSuiteBase with BaseSuite {

  val exa_schema = "test_schema"
  val exa_table = "test_table"

  test("creates dataframe from user query") {
    val df1 = spark.read
      .format("com.exasol.spark")
      .option("host", container.host)
      .option("port", s"${container.port}")
      .option("query", s"SELECT * FROM $exa_schema.$exa_table")
      .load()

    val cnt1 = df1.count

    val df2 = spark.read
      .format("exasol")
      .option("host", container.host)
      .option("port", s"${container.port}")
      .option("query", s"SELECT * FROM $exa_schema.$exa_table")
      .load()

    assert(cnt1 == 3)
    assert(df2.count == 3)
    assert(df2.schema.exists(f => f.name == "name"))
  }

}

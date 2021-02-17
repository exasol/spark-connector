package com.exasol.spark

import com.holdenkarau.spark.testing.DataFrameSuiteBase

/**
 * Test only required columns selection from queries.
 */
class ColumnPruningSuite extends BaseIntegrationTest with DataFrameSuiteBase {

  test("returns only required columns in query") {
    createDummyTable()

    val df = spark.read
      .format("com.exasol.spark")
      .option("host", jdbcHost)
      .option("port", jdbcPort)
      .option("query", s"SELECT * FROM $EXA_SCHEMA.$EXA_TABLE")
      .load()
      .select("city")

    assert(df.columns.size === 1)
    assert(df.columns.head === "city")
    val result = df.collect().map(x => x.getString(0)).toSet
    assert(result === Set("Berlin", "Paris", "Lisbon"))
  }

}

package com.exasol.spark

import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.TimestampType

import com.holdenkarau.spark.testing.DataFrameSuiteBase

import org.scalatest.FunSuite

/** Test only required columns selection from queries */
class ColumnPruningSuite extends FunSuite with BaseDockerSuite with DataFrameSuiteBase {

  test("returns only required columns in query") {
    createDummyTable()

    val df = spark.read
      .format("com.exasol.spark")
      .option("host", container.host)
      .option("port", s"${container.port}")
      .option("query", s"SELECT * FROM $EXA_SCHEMA.$EXA_TABLE")
      .load()
      .select("city")

    assert(df.columns.size === 1)
    assert(df.columns.head === "city")
    val result = df.collect().map(x => x.getString(0)).toSet
    assert(result === Set("Berlin", "Paris", "Lisbon"))
  }

}

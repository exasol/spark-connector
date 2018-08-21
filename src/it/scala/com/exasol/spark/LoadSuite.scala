package com.exasol.spark

import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.TimestampType

import org.scalatest.FunSuite
import com.holdenkarau.spark.testing.DataFrameSuiteBase

/** Tests for loading data from Exasol query as dataframes using short and long source formats */
class LoadSuite extends FunSuite with DataFrameSuiteBase with BaseSuite {

  test("creates dataframe from user query") {

    createDummyTable()

    val df1 = spark.read
      .format("com.exasol.spark")
      .option("host", container.host)
      .option("port", s"${container.port}")
      .option("query", s"SELECT * FROM $EXA_SCHEMA.$EXA_TABLE")
      .load()

    val cnt1 = df1.count
    val cities = df1.collect().map(x => x(2)).toSet
    assert(cities === Set("Berlin", "Paris", "Lisbon"))

    val df2 = spark.read
      .format("exasol")
      .option("host", container.host)
      .option("port", s"${container.port}")
      .option("query", s"SELECT * FROM $EXA_SCHEMA.$EXA_TABLE")
      .load()

    assert(cnt1 == 3)
    assert(df2.count == cnt1)

    val schema = df2.schema
    assert(schema.exists(f => f.name == "NAME"))
    assert(schema.map(_.name).toSet === Set("ID", "NAME", "CITY", "UPDATED_AT"))
    assert(schema.map(_.dataType).toSet === Set(LongType, StringType, StringType, TimestampType))
  }

}

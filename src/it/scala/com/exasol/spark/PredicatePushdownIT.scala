package com.exasol.spark

import java.sql.Date
import java.sql.Timestamp

import org.apache.spark.sql.functions._

/**
 * Tests predicate pushdown for user queries.
 */
class PredicatePushdownIT extends BaseTableQueryIT {

  import spark.implicits._

  test("returns dataframe with equal-to-filter") {
    val df = getDataFrame().filter($"id" === 1).collect()
    assert(df.map(r => (r.getLong(0), r.getString(1))) === Seq((1, "Germany")))
  }

  test("returns dataframe with not equal to filter") {
    val df = getDataFrame().filter($"name" =!= "Germany").collect()
    assert(df.map(r => r.getString(1)).contains("Germany") === false)
  }

  test("returns dataframe with greater-than-filter") {
    val df = getDataFrame().filter($"id" > 2).collect()
    assert(df.map(r => r.getString(1)) === Seq("Portugal"))
  }

  test("returns dataframe with greater than or equal filter") {
    val df = getDataFrame().filter($"id" >= 2).collect()
    assert(df.map(r => r.getString(2)) === Seq("Paris", "Lisbon"))
  }

  test("returns dataframe with less than filter") {
    val df = getDataFrame().filter($"id" < 2).collect()
    assert(df.map(r => r.getString(2)) === Seq("Berlin"))
  }

  test("returns dataframe with less than or equal filter") {
    val df = getDataFrame().filter($"id" <= 2).collect()
    assert(df.map(r => r.getString(2)) === Seq("Berlin", "Paris"))
  }

  test("returns dataframe with string ends with filter") {
    val df = getDataFrame().filter($"city".endsWith("bon")).collect()
    assert(df.map(r => (r.getString(1), r.getString(2))) === Seq(("Portugal", "Lisbon")))
  }

  test("returns dataframe with string contains filter") {
    val df = getDataFrame().filter($"name".contains("rma")).collect()
    assert(df.map(r => r.getString(1)) === Seq("Germany"))
  }

  test("returns dataframe with string starts with filter") {
    val df = getDataFrame().filter($"name".startsWith("Franc")).collect()
    assert(df.map(r => (r.getString(1), r.getString(2))) === Seq(("France", "Paris")))
  }

  test("returns dataframe with not filter") {
    val df = getDataFrame().where(not($"id" <= 2))
    assert(df.collect().map(r => r.getString(1)) === Seq("Portugal"))
  }

  test("returns dataframe with and filter") {
    val df = getDataFrame().where($"id" < 2 && $"name" === "France")
    assert(df.count === 0)
  }

  test("returns dataframe with or filter") {
    val df = getDataFrame().filter($"id" > 2 or $"name" === "France")
    assert(df.count === 2)
  }

  test("returns dataframe with combined filter") {
    val df = getDataFrame()
      .filter($"id" < 3)
      .filter(col("city").like("Ber%"))
      .select("id", "city")
      .collect()
    assert(df.map(r => (r.getLong(0), r.getString(1))) === Seq((1, "Berlin")))
  }

  test("returns dataframe with createTempView and spark.sql filter") {
    val df = getDataFrame()
    df.createOrReplaceTempView("table")
    val sqlDF = spark
      .sql("SELECT id, city FROM table WHERE id BETWEEN 1 AND 3 AND name < 'Japan'")
      .collect()
    assert(sqlDF.map(r => (r.getLong(0), r.getString(1))) === Seq((1, "Berlin"), (2, "Paris")))
  }

  test("returns dataframe with date filter") {
    val filterDate = Date.valueOf("2017-12-31")
    val df = getDataFrame()
      .filter(col("date_info") === filterDate)
      .select("date_info", "updated_at")
    assert(df.count() === 1)
    assert(df.queryExecution.executedPlan.toString().contains("EqualTo(DATE_INFO,"))
  }

  test("returns dataframe with timestamp filter") {
    val minTimestamp = Timestamp.valueOf("2017-12-30 00:00:00.0000")
    val df = getDataFrame()
      .filter(col("updated_at") < minTimestamp)
      .select("date_info", "updated_at")
    assert(df.count() === 0)
    assert(df.queryExecution.executedPlan.toString().contains("LessThan(UPDATED_AT,"))
  }

  test("returns dataframe with count pushdown") {
    assert(getDataFrame().count() === 3)
  }

}

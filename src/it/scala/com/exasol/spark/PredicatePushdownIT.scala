package com.exasol.spark

import java.sql.Timestamp

import org.apache.spark.sql.functions.col

import com.holdenkarau.spark.testing.DataFrameSuiteBase

/**
 * Test where clause generation for user queries.
 */
class PredicatePushdownIT extends BaseIntegrationTest with DataFrameSuiteBase {

  test("with where clause build from filters: filter") {
    createDummyTable()

    import spark.implicits._

    val df = spark.read
      .format("exasol")
      .option("host", jdbcHost)
      .option("port", jdbcPort)
      .option("query", s"SELECT * FROM $EXA_SCHEMA.$EXA_TABLE")
      .load()
      .filter($"id" < 3)
      .filter(col("city").like("Ber%"))
      .select("id", "city")

    val result = df.collect().map(x => (x.getLong(0), x.getString(1))).toSet
    assert(result.size === 1)
    assert(result === Set((1, "Berlin")))
  }

  test("with where clause build from filters: createTempView and spark.sql") {
    createDummyTable()

    val df = spark.read
      .format("exasol")
      .option("host", jdbcHost)
      .option("port", jdbcPort)
      .option("query", s"SELECT * FROM $EXA_SCHEMA.$EXA_TABLE")
      .load()

    df.createOrReplaceTempView("myTable")

    val myDF = spark
      .sql("SELECT id, city FROM myTable WHERE id BETWEEN 1 AND 3 AND name < 'Japan'")

    val result = myDF.collect().map(x => (x.getLong(0), x.getString(1))).toSet
    assert(result.size === 2)
    assert(result === Set((1, "Berlin"), (2, "Paris")))
  }

  test("date and timestamp should be read and filtered correctly") {
    import java.sql.Date

    createDummyTable()
    val df = spark.read
      .format("exasol")
      .option("host", jdbcHost)
      .option("port", jdbcPort)
      .option("query", s"SELECT date_info, updated_at FROM $EXA_SCHEMA.$EXA_TABLE")
      .load()
    val minTimestamp = Timestamp.valueOf("2017-12-30 00:00:00.0000")
    val testDate = Date.valueOf("2017-12-31")

    val resultDate = df.collect().map(_.getDate(0))
    assert(resultDate.contains(testDate))

    val resultTimestamp = df.collect().map(_.getTimestamp(1)).map(x => x.after(minTimestamp))
    assert(!resultTimestamp.contains(false))

    val filteredByDateDF = df.filter(col("date_info") === testDate)
    assert(filteredByDateDF.count() === 1)

    val filteredByTimestampDF = df.filter(col("updated_at") < minTimestamp)
    assert(filteredByTimestampDF.count() === 0)
  }

  test("count should be performed successfully") {
    createDummyTable()
    val df = spark.read
      .format("exasol")
      .option("host", jdbcHost)
      .option("port", jdbcPort)
      .option("query", s"SELECT * FROM $EXA_SCHEMA.$EXA_TABLE")
      .load()
    val result = df.count()
    assert(result === 3)
  }
}

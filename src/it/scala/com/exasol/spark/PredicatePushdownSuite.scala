package com.exasol.spark

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.TimestampType

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.FunSuite

/** Test where clause generation for user queries */
class PredicatePushdownSuite extends FunSuite with BaseDockerSuite with DataFrameSuiteBase {

  test("with where clause build from filters: filter") {
    createDummyTable()

    import spark.implicits._

    val df = spark.read
      .format("exasol")
      .option("host", container.host)
      .option("port", s"${container.port}")
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
      .option("host", container.host)
      .option("port", s"${container.port}")
      .option("query", s"SELECT * FROM $EXA_SCHEMA.$EXA_TABLE")
      .load()

    df.createOrReplaceTempView("myTable")

    val myDF = spark
      .sql("SELECT id, city FROM myTable WHERE id BETWEEN 1 AND 3 AND name < 'Japan'")

    val result = myDF.collect().map(x => (x.getLong(0), x.getString(1))).toSet
    assert(result.size === 2)
    assert(result === Set((1, "Berlin"), (2, "Paris")))
  }

}

package com.exasol.spark

import java.sql.Date

import org.apache.spark.{SparkConf, SparkException}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

import com.exasol.spark.util.Types

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.FunSuite

/** Integration tests for saving Spark dataframes into Exasol tables */
class SaveSuite extends FunSuite with BaseDockerSuite with DataFrameSuiteBase {

  test("`tableExists` should return correct boolean result") {
    createDummyTable()

    assert(exaManager.tableExists(s"$EXA_SCHEMA.$EXA_TABLE") === true)
    assert(exaManager.tableExists("DUMMY_SCHEMA.DUMMYTABLE") === false)
  }

  test("`tuncateTable` should perform table truncation") {
    createDummyTable()

    assert(exaManager.withCountQuery(s"SELECT COUNT(*) FROM $EXA_SCHEMA.$EXA_TABLE") > 0)

    exaManager.truncateTable(s"$EXA_SCHEMA.$EXA_TABLE")
    assert(exaManager.withCountQuery(s"SELECT COUNT(*) FROM $EXA_SCHEMA.$EXA_TABLE") === 0)

    // Idempotent
    exaManager.truncateTable(s"$EXA_SCHEMA.$EXA_TABLE")
    assert(exaManager.withCountQuery(s"SELECT COUNT(*) FROM $EXA_SCHEMA.$EXA_TABLE") === 0)
  }

  test("`dropTable` should drop table") {
    createDummyTable()
    assert(exaManager.tableExists(s"$EXA_SCHEMA.$EXA_TABLE") === true)
    exaManager.dropTable(s"$EXA_SCHEMA.$EXA_TABLE")
    assert(exaManager.tableExists(s"$EXA_SCHEMA.$EXA_TABLE") === false)

    // Idempotent
    exaManager.dropTable(s"$EXA_SCHEMA.$EXA_TABLE")
    assert(exaManager.tableExists(s"$EXA_SCHEMA.$EXA_TABLE") === false)
  }

  test("`createTable` should create a table") {
    createDummyTable()
    val tableName = s"$EXA_SCHEMA.new_table"
    assert(exaManager.tableExists(tableName) === false)

    import sqlContext.implicits._
    val df = sc
      .parallelize(Seq(("a", 103, Date.valueOf("2019-01-14"))))
      .toDF("str_col", "int_col", "date_col")

    val tableSchema = Types.createTableSchema(df.schema)
    exaManager.createTable(tableName, tableSchema)
    assert(exaManager.tableExists(tableName) === true)
  }

  // scalastyle:off nonascii
  val testData: Seq[(String, String, Date, String)] = Seq(
    ("name1", "city1", Date.valueOf("2019-01-11"), "äpişge"),
    ("name1", "city2", Date.valueOf("2019-01-12"), "gül"),
    ("name2", "city1", Date.valueOf("2019-02-25"), "çigit"),
    ("name2", "city2", Date.valueOf("2019-02-25"), "okay")
  )
  // scalastyle:on

  def runWithSaveMode(mode: String, partitionCnt: Int, tableExists: Boolean): Long = {
    import sqlContext.implicits._

    val tableName = if (tableExists) s"$EXA_SCHEMA.$EXA_TABLE" else s"$EXA_SCHEMA.NONEXIST_TABLE"

    if (tableExists) {
      createDummyTable()
    } else {
      exaManager.dropTable(tableName)
    }

    val df = sc
      .parallelize(testData, partitionCnt)
      .toDF("name", "city", "date_info", "unicode_col")

    df.write
      .mode(mode)
      .option("host", container.host)
      .option("port", s"${container.port}")
      .option("table", tableName)
      .format("exasol")
      .save()

    exaManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName")
  }

  test("dataframe save with different modes when Exasol table exists") {
    createDummyTable()
    val initialCnt =
      exaManager.withCountQuery(s"SELECT COUNT(*) FROM $EXA_SCHEMA.$EXA_TABLE")

    val results: Map[(String, Int), Long] = Map(
      ("ignore", 1) -> initialCnt,
      ("overwrite", 2) -> testData.size.toLong,
      ("append", 3) -> (initialCnt + testData.size)
    )

    results.foreach {
      case ((mode, parts), expected) =>
        assert(runWithSaveMode(mode, parts, true) === expected)
    }

    // Error if table exists
    val thrown = intercept[RuntimeException] {
      runWithSaveMode("errorifexists", 4, true)
    }
    val expectedMsg = s"Table $EXA_SCHEMA.$EXA_TABLE already exists. " +
      "Use one of other SaveMode modes: 'append', 'overwrite' or 'ignore'."
    assert(thrown.getMessage === expectedMsg)
  }

  test("dataframe save with different modes when Exasol table does not exist") {
    val cnt = testData.size

    val results: Seq[(String, Int)] = Seq(
      ("ignore", 1),
      ("overwrite", 2),
      ("append", 3),
      ("errorifexists", 4)
    )

    results.foreach {
      case (mode, parts) =>
        assert(runWithSaveMode(mode, parts, false) === cnt)
    }
  }

}

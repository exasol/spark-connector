package com.exasol.spark

import java.sql.Date

import com.exasol.spark.util.Types

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.funsuite.AnyFunSuite

/** Integration tests for saving Spark dataframes into Exasol tables */
class SaveSuite extends AnyFunSuite with BaseDockerSuite with DataFrameSuiteBase {

  private[this] val tableName = s"$EXA_SCHEMA.$EXA_TABLE"

  private[this] val saveModes = Seq("append", "errorifexists", "ignore", "overwrite")

  private[this] val defaultOptions = Map(
    "host" -> container.host,
    "port" -> s"${container.port}",
    "table" -> tableName
  )

  // scalastyle:off nonascii
  private[this] val dataframeTestData: Seq[(String, String, Date, String)] = Seq(
    ("name1", "city1", Date.valueOf("2019-01-11"), "äpişge"),
    ("name1", "city2", Date.valueOf("2019-01-12"), "gül"),
    ("name2", "city1", Date.valueOf("2019-02-25"), "çigit"),
    ("name2", "city2", Date.valueOf("2019-02-25"), "okay")
  )
  // scalastyle:on nonascii

  test("`tableExists` should return correct boolean result") {
    createDummyTable()
    assert(exaManager.tableExists(tableName) === true)
    assert(exaManager.tableExists("DUMMY_SCHEMA.DUMMYTABLE") === false)
  }

  test("`truncateTable` should perform table truncation") {
    createDummyTable()
    assert(exaManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName") > 0)
    exaManager.truncateTable(tableName)
    assert(exaManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName") === 0)
    // Ensure it is idempotent
    exaManager.truncateTable(tableName)
    assert(exaManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName") === 0)
  }

  test("`dropTable` should drop table") {
    createDummyTable()
    assert(exaManager.tableExists(tableName) === true)
    exaManager.dropTable(tableName)
    assert(exaManager.tableExists(tableName) === false)
    // Ensure it is idempotent
    exaManager.dropTable(tableName)
    assert(exaManager.tableExists(tableName) === false)
  }

  test("`createTable` should create a table") {
    createDummyTable()
    val newTableName = s"$EXA_SCHEMA.new_table"
    assert(exaManager.tableExists(newTableName) === false)

    import sqlContext.implicits._
    val df = sc
      .parallelize(Seq(("a", 103, Date.valueOf("2019-01-14"))))
      .toDF("str_col", "int_col", "date_col")

    val newTableSchema = Types.createTableSchema(df.schema)
    exaManager.createTable(newTableName, newTableSchema)
    assert(exaManager.tableExists(newTableName) === true)
  }

  test("save mode 'ignore' does not insert data if table exists") {
    createDummyTable()
    val initialRecordsCount =
      exaManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName")
    assert(runDataFrameSave("ignore", 1) === initialRecordsCount)
  }

  test("save mode 'overwrite' overwrite if table exists") {
    createDummyTable()
    assert(runDataFrameSave("overwrite", 2) === dataframeTestData.size.toLong)
  }

  test("save mode 'append' appends data if table exists") {
    createDummyTable()
    val initialRecordsCount =
      exaManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName")
    val totalRecords = initialRecordsCount + dataframeTestData.size
    assert(runDataFrameSave("append", 3) === totalRecords)
  }

  test("save mode 'errorifexists' throws exception if table exists") {
    createDummyTable()
    val thrown = intercept[UnsupportedOperationException] {
      runDataFrameSave("errorifexists", 4)
    }
    assert(thrown.getMessage.contains(s"Table $tableName already exists"))
  }

  test("save throws without 'create_table' or 'drop_table' option when table does not exist") {
    exaManager.dropTable(tableName)
    saveModes.foreach {
      case mode =>
        val thrown = intercept[UnsupportedOperationException] {
          runDataFrameSave(mode, 2)
        }
        assert(
          thrown.getMessage.contains(s"Table $tableName does not exist. Please enable")
        )
    }
  }

  test("save with 'create_table' option creates a new table before saving dataframe") {
    val newOptions = defaultOptions ++ Map("create_table" -> "true")
    saveModes.foreach {
      case mode =>
        exaManager.dropTable(tableName)
        assert(runDataFrameSave(mode, 2, newOptions) === dataframeTestData.size.toLong)
    }
  }

  test("save with 'drop_table' option drops and creates a new table before saving dataframe") {
    val newOptions = defaultOptions ++ Map("drop_table" -> "true")
    saveModes.foreach {
      case mode =>
        createDummyTable()
        assert(runDataFrameSave(mode, 3, newOptions) === dataframeTestData.size)
    }
  }

  private[this] def runDataFrameSave(
    mode: String,
    partitionCount: Int,
    options: Map[String, String] = defaultOptions
  ): Long = {
    import sqlContext.implicits._
    val df = sc
      .parallelize(dataframeTestData, partitionCount)
      .toDF("name", "city", "date_info", "unicode_col")

    df.write
      .mode(mode)
      .options(options)
      .format("exasol")
      .save()

    exaManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName")
  }

}

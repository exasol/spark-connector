package com.exasol.spark

import java.sql.Date

import com.exasol.spark.util.Types

import org.scalatest.BeforeAndAfterEach

/**
 * Integration tests for saving Spark DataFrames into Exasol tables.
 */
class SaveOptionsIT extends BaseTableQueryIT with BeforeAndAfterEach {

  private[this] val saveModes = Seq("append", "errorifexists", "ignore", "overwrite")

  // Required for save mode tests, since initial table can be deleted on other tests
  override def beforeEach(): Unit =
    createTable()

  private[this] val dataframeTestData: Seq[(String, String, Date, String)] = Seq(
    ("name1", "city1", Date.valueOf("2019-01-11"), "äpişge"),
    ("name1", "city2", Date.valueOf("2019-01-12"), "gül"),
    ("name2", "city1", Date.valueOf("2019-02-25"), "çigit"),
    ("name2", "city2", Date.valueOf("2019-02-25"), "okay")
  )

  test("`tableExists` should return correct boolean result") {
    assert(exasolConnectionManager.tableExists(tableName) === true)
    assert(exasolConnectionManager.tableExists("DUMMY_SCHEMA.DUMMYTABLE") === false)
  }

  test("`truncateTable` should perform table truncation") {
    assert(exasolConnectionManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName") > 0)
    exasolConnectionManager.truncateTable(tableName)
    assert(exasolConnectionManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName") === 0)
    // Ensure it is idempotent
    exasolConnectionManager.truncateTable(tableName)
    assert(exasolConnectionManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName") === 0)
  }

  test("`dropTable` should drop table") {
    assert(exasolConnectionManager.tableExists(tableName) === true)
    exasolConnectionManager.dropTable(tableName)
    assert(exasolConnectionManager.tableExists(tableName) === false)
    // Ensure it is idempotent
    exasolConnectionManager.dropTable(tableName)
    assert(exasolConnectionManager.tableExists(tableName) === false)
  }

  test("`createTable` should create a table") {
    val newTableName = s"$schema.new_table"
    assert(exasolConnectionManager.tableExists(newTableName) === false)

    import sqlContext.implicits._
    val df = getSparkContext()
      .parallelize(Seq(("a", 103, Date.valueOf("2019-01-14"))))
      .toDF("str_col", "int_col", "date_col")

    val newTableSchema = Types.createTableSchema(df.schema)
    exasolConnectionManager.createTable(newTableName, newTableSchema)
    assert(exasolConnectionManager.tableExists(newTableName) === true)
  }

  test("save mode 'ignore' does not insert data if table exists") {
    val initialRecordsCount = exasolConnectionManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName")
    assert(runDataFrameSave("ignore", 1) === initialRecordsCount)
  }

  test("save mode 'overwrite' overwrite if table exists") {
    assert(runDataFrameSave("overwrite", 2) === dataframeTestData.size.toLong)
  }

  test("save mode 'append' appends data if table exists") {
    val initialRecordsCount = exasolConnectionManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName")
    val totalRecords = initialRecordsCount + dataframeTestData.size
    assert(runDataFrameSave("append", 3) === totalRecords)
  }

  test("save mode 'errorifexists' throws exception if table exists") {
    val thrown = intercept[UnsupportedOperationException] {
      runDataFrameSave("errorifexists", 4)
    }
    assert(thrown.getMessage().startsWith("E-SEC-3"))
    assert(thrown.getMessage().contains(s"Table '$tableName' already exists"))
  }

  test("save throws without 'create_table' or 'drop_table' option when table does not exist") {
    exasolConnectionManager.dropTable(tableName)
    saveModes.foreach { case mode =>
      val thrown = intercept[UnsupportedOperationException] {
        runDataFrameSave(mode, 2)
      }
      assert(thrown.getMessage().startsWith("E-SEC-2"))
      assert(thrown.getMessage().contains(s"Table '$tableName' does not exist."))
    }
  }

  test("save with 'create_table' option creates a new table before saving dataframe") {
    val newOptions = getOptions() ++ Map("create_table" -> "true")
    saveModes.foreach { case mode =>
      exasolConnectionManager.dropTable(tableName)
      assert(runDataFrameSave(mode, 2, newOptions) === dataframeTestData.size.toLong)
    }
  }

  test("save with 'drop_table' option drops and creates a new table before saving dataframe") {
    val newOptions = getOptions() ++ Map("drop_table" -> "true")
    saveModes.foreach { case mode =>
      createTable()
      assert(runDataFrameSave(mode, 3, newOptions) === dataframeTestData.size)
    }
  }

  private[this] def runDataFrameSave(
    mode: String,
    partitionCount: Int,
    options: Map[String, String] = getOptions()
  ): Long = {
    import sqlContext.implicits._
    val df = getSparkContext()
      .parallelize(dataframeTestData, partitionCount)
      .toDF("name", "city", "date_info", "unicode_col")

    df.write
      .mode(mode)
      .options(options)
      .format("exasol")
      .save()

    exasolConnectionManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName")
  }

  // Lazily obtain options after the container is initialized
  private[this] def getOptions(): Map[String, String] = getDefaultOptions() ++ Map("table" -> tableName)

}

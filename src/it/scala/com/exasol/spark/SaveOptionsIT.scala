package com.exasol.spark

import java.sql.Date

import com.exasol.spark.util.Types

/**
 * Integration tests for saving Spark DataFrames into Exasol tables.
 */
class SaveOptionsIT extends BaseTableQueryIT {

  private[this] val saveModes = Seq("append", "errorifexists", "ignore", "overwrite")
  private[this] var defaultOptions: Map[String, String] = _

  override def beforeEach(): Unit = {
    super.beforeEach()
    defaultOptions = Map(
      "host" -> jdbcHost,
      "port" -> jdbcPort,
      "table" -> tableName
    )
  }

  // scalastyle:off nonascii
  private[this] val dataframeTestData: Seq[(String, String, Date, String)] = Seq(
    ("name1", "city1", Date.valueOf("2019-01-11"), "äpişge"),
    ("name1", "city2", Date.valueOf("2019-01-12"), "gül"),
    ("name2", "city1", Date.valueOf("2019-02-25"), "çigit"),
    ("name2", "city2", Date.valueOf("2019-02-25"), "okay")
  )
  // scalastyle:on nonascii

  test("`tableExists` should return correct boolean result") {
    assert(connectionManager.tableExists(tableName) === true)
    assert(connectionManager.tableExists("DUMMY_SCHEMA.DUMMYTABLE") === false)
  }

  test("`truncateTable` should perform table truncation") {
    assert(connectionManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName") > 0)
    connectionManager.truncateTable(tableName)
    assert(connectionManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName") === 0)
    // Ensure it is idempotent
    connectionManager.truncateTable(tableName)
    assert(connectionManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName") === 0)
  }

  test("`dropTable` should drop table") {
    assert(connectionManager.tableExists(tableName) === true)
    connectionManager.dropTable(tableName)
    assert(connectionManager.tableExists(tableName) === false)
    // Ensure it is idempotent
    connectionManager.dropTable(tableName)
    assert(connectionManager.tableExists(tableName) === false)
  }

  test("`createTable` should create a table") {
    val newTableName = s"$schema.new_table"
    assert(connectionManager.tableExists(newTableName) === false)

    import sqlContext.implicits._
    val df = sc
      .parallelize(Seq(("a", 103, Date.valueOf("2019-01-14"))))
      .toDF("str_col", "int_col", "date_col")

    val newTableSchema = Types.createTableSchema(df.schema)
    connectionManager.createTable(newTableName, newTableSchema)
    assert(connectionManager.tableExists(newTableName) === true)
  }

  test("save mode 'ignore' does not insert data if table exists") {
    val initialRecordsCount =
      connectionManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName")
    assert(runDataFrameSave("ignore", 1) === initialRecordsCount)
  }

  test("save mode 'overwrite' overwrite if table exists") {
    assert(runDataFrameSave("overwrite", 2) === dataframeTestData.size.toLong)
  }

  test("save mode 'append' appends data if table exists") {
    val initialRecordsCount =
      connectionManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName")
    val totalRecords = initialRecordsCount + dataframeTestData.size
    assert(runDataFrameSave("append", 3) === totalRecords)
  }

  test("save mode 'errorifexists' throws exception if table exists") {
    val thrown = intercept[UnsupportedOperationException] {
      runDataFrameSave("errorifexists", 4)
    }
    assert(thrown.getMessage.contains(s"Table $tableName already exists"))
  }

  test("save throws without 'create_table' or 'drop_table' option when table does not exist") {
    connectionManager.dropTable(tableName)
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
        connectionManager.dropTable(tableName)
        assert(runDataFrameSave(mode, 2, newOptions) === dataframeTestData.size.toLong)
    }
  }

  test("save with 'drop_table' option drops and creates a new table before saving dataframe") {
    val newOptions = defaultOptions ++ Map("drop_table" -> "true")
    saveModes.foreach {
      case mode =>
        createTable()
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

    connectionManager.withCountQuery(s"SELECT COUNT(*) FROM $tableName")
  }

}

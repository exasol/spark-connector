package com.exasol.spark

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.DataFrameReader

import com.exasol.spark.util.ExasolConnectionManager

import org.scalatest.BeforeAndAfterAll

abstract class AbstractTableQueryIT extends BaseIntegrationTest with BeforeAndAfterAll with SparkSessionSetup {

  val tableName: String
  def createTable(): Unit

  override def beforeAll(): Unit = {
    super.beforeAll()
    val options = getExasolOptions(getDefaultOptions() ++ Map("table" -> tableName))
    exasolConnectionManager = ExasolConnectionManager(options)
    createTable()
  }

  private[spark] def getDataFrameReader(query: String): DataFrameReader =
    spark.read
      .format("exasol")
      .options(getDefaultOptions())
      .option("query", query)

  private[spark] def getDataFrame(queryOpt: Option[String] = None): DataFrame = {
    val query = queryOpt.fold(s"SELECT * FROM $tableName")(identity)
    getDataFrameReader(query).load()
  }

}

package com.exasol.spark

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.DataFrameReader

import org.scalatest.BeforeAndAfterEach

abstract class AbstractTableQueryIT extends BaseIntegrationTest with SparkSessionSetup with BeforeAndAfterEach {

  val tableName: String
  def createTable(): Unit

  override def beforeEach(): Unit =
    createTable()

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

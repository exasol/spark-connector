package com.exasol.spark

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.DataFrameReader

import org.scalatest.BeforeAndAfterEach

abstract class AbstractTableQueryIT extends BaseIntegrationTest with SparkSessionSetup with BeforeAndAfterEach {

  val tableName: String
  def createTable(): Unit

  override def beforeEach(): Unit =
    createTable()

  private[spark] def getDataFrameReader(query: String): DataFrameReader = {
    val reader = spark.read
      .format("exasol")
      .option("host", jdbcHost)
      .option("port", jdbcPort)
      .option("query", query)

    if (imageSupportsFingerprint()) {
      reader.option("fingerprint", getFingerprint())
    } else {
      reader.option("jdbc_options", "validateservercertificate=0")
    }
  }

  private[spark] def getDataFrame(queryOpt: Option[String] = None): DataFrame = {
    val query = queryOpt.fold(s"SELECT * FROM $tableName")(identity)
    getDataFrameReader(query).load()
  }

}

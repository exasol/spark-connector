package com.exasol.spark

import org.apache.spark.sql.DataFrame

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.BeforeAndAfterEach

abstract class AbstractTableQueryIT extends BaseIntegrationTest with DataFrameSuiteBase with BeforeAndAfterEach {

  val tableName: String
  def createTable(): Unit

  override def beforeEach(): Unit = {
    createTable()
    ()
  }

  private[spark] def getDataFrame(query: Option[String] = None): DataFrame =
    spark.read
      .format("exasol")
      .option("host", jdbcHost)
      .option("port", jdbcPort)
      .option("jdbc_options", "validateservercertificate=0")
      .option("query", query.fold(s"SELECT * FROM $tableName")(identity))
      .load()

}

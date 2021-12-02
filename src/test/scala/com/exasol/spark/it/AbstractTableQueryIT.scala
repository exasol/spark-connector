package com.exasol.spark

import org.apache.spark.sql.DataFrame

import org.scalatest.BeforeAndAfterEach
import org.apache.spark.sql.SparkSession
import org.scalatest.BeforeAndAfterAll

abstract class AbstractTableQueryIT extends BaseIntegrationTest with BeforeAndAfterAll with BeforeAndAfterEach {

  private[this] var spark: SparkSession = _
  val tableName: String
  def createTable(): Unit

  def getSpark(): SparkSession = spark

  override def beforeAll(): Unit = {
    super.beforeAll()
    spark = SparkSessionProvider.getSparkSession()
  }

  override def beforeEach(): Unit =
    createTable()

  override def afterAll(): Unit = {
    spark.close()
    super.afterAll()
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

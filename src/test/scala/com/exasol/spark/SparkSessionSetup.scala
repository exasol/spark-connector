package com.exasol.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContextHelper
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SparkSession

import com.exasol.spark.SparkSessionProvider

import org.scalatest.BeforeAndAfterAll
import org.scalatest.Suite

/**
 * A trait that provides Spark session setup across tests.
 */
trait SparkSessionSetup extends BeforeAndAfterAll { self: Suite =>
  @transient lazy val spark: SparkSession = SparkSessionProvider.getSparkSession(getSparkConf())
  @transient lazy val sqlContext: SQLContext = SparkSession.builder().getOrCreate().sqlContext
  @transient private var sparkContext: SparkContext = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    setupSparkContext()
  }

  override def afterAll(): Unit = {
    stopSparkContext()
    spark.stop()
    super.afterAll()
  }

  /**
   * Returns {@link SparkContext} from managed session.
   */
  def getSparkContext(): SparkContext = sparkContext

  private[this] def setupSparkContext(): Unit = {
    SparkContextHelper.stopActiveSparkContext()
    sparkContext = SparkContext.getOrCreate(getSparkConf())
  }

  private[this] def stopSparkContext(): Unit =
    if (sparkContext != null) {
      sparkContext.stop()
      sparkContext = null
    }

  private[this] def getSparkConf(): SparkConf =
    new SparkConf()
      .setMaster("local[*]")
      .setAppName("test")
      .set("spark.ui.enabled", "false")
      .set("spark.app.id", getRandomAppId())
      .set("spark.driver.host", "localhost")

  private[this] def getRandomAppId(): String =
    this.getClass().getName() + math.floor(math.random() * 1000).toLong.toString()

}

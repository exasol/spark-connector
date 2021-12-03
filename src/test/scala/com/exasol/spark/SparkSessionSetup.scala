package com.exasol.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContextHelper
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SQLContext

import org.scalatest.BeforeAndAfterAll
import org.scalatest.Suite

/**
 * A trait that provides Spark session setup accross tests.
 */
trait SparkSessionSetup extends BeforeAndAfterAll { self: Suite =>
  @transient lazy val spark: SparkSession = SparkSessionProvider.getSparkSession()
  @transient lazy val sqlContext: SQLContext = SparkSessionProvider.getSQLContext()
  @transient private var sparkContext: SparkContext = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    setupSparkContext()
    setupSparkSession()
  }

  override def afterAll(): Unit = {
    stopSparkContext()
    SparkSessionProvider.setSparkSession(null)
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

  private[this] def setupSparkSession(): Unit =
    if (SparkSessionProvider.getSparkSession() != null && !isSparkContextStopped()) {
      // do nothing
    } else {
      val builder = SparkSession.builder()
      SparkSessionProvider.setSparkSession(builder.getOrCreate())
    }

  private[this] def stopSparkContext(): Unit =
    if (sparkContext != null) {
      sparkContext.stop()
      sparkContext = null
    }

  private[this] def isSparkContextStopped(): Boolean =
    SparkSessionProvider.getSparkSession().sparkContext.isStopped

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

/**
 * An object that manages transient Spark session.
 */
object SparkSessionProvider {
  @transient var sparkSession: SparkSession = _

  def getSparkSession(): SparkSession = sparkSession

  def setSparkSession(session: SparkSession): Unit =
    sparkSession = session

  def getSQLContext(): SQLContext =
    SparkSession.builder().getOrCreate().sqlContext

}

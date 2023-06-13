package org.apache.spark

/**
 * Class in the spark package to access internal methods.
 */
object SparkContextHelper {

  /**
   * Stops active Spark contextes.
   */
  def stopActiveSparkContext(): Unit =
    SparkContext.getActive.foreach(_.stop())

}

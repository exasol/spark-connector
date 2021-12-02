package com.exasol.spark

import org.apache.spark.sql.SparkSession

/**
 * Creates on demand Spark session that is maintained by caller.
 */
object SparkSessionProvider {

  /**
   * Creates and returns a {@link SparkSession}.
   *
   * @return a spark session
   */
  def getSparkSession(): SparkSession =
    SparkSession
      .builder()
      .appName("SparkSessionProvider")
      .master("local[2]")
      .getOrCreate()

}

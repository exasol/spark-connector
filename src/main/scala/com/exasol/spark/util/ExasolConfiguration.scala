package com.exasol.spark.util

import java.net.InetAddress

/**
 * The configuration parameters for Spark Exasol connector
 *
 * These can be user provided when loading or defined in Spark configurations.
 *
 * For example, user provided:
 *
 * {{{
 *    df = sparkSession
 *      .read
 *      .format("exasol")
 *      .option("host", "127.0.0.1")
 *      .option("port", "8888")
 *      // ...
 *      .load
 * }}}
 *
 * From Spark configuration:
 *
 * {{{
 *    val sparkConf = new SparkConf()
 *      .setMaster("local[*]")
 *      .setAppName("spark-exasol-connector")
 *      .set("spark.exasol.host", "localhost")
 *      .set("spark.exasol.port", "1234")
 *      // ...
 *
 *    val sparkSession = SparkSession
 *      .builder()
 *      .config(sparkConf)
 *      .getOrCreate()
 * }}}
 *
 * If both are defined, user properties are used. If nothing is defined, then default values are
 * used.
 *
 */
final case class ExasolConfiguration(
  host: String,
  port: Int,
  username: String,
  password: String,
  max_nodes: Int
)

object ExasolConfiguration {

  def getLocalHost(): String = InetAddress.getLocalHost.getHostAddress

  @SuppressWarnings(
    Array("org.wartremover.warts.Overloading", "org.danielnixon.extrawarts.StringOpsPartial")
  )
  def apply(opts: Map[String, String]): ExasolConfiguration =
    ExasolConfiguration(
      host = opts.getOrElse("host", getLocalHost()),
      port = opts.getOrElse("port", "8888").toInt,
      username = opts.getOrElse("username", "sys"),
      password = opts.getOrElse("password", "exasol"),
      max_nodes = opts.getOrElse("max_nodes", "200").toInt
    )

}

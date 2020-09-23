package com.exasol.spark.util

import java.net.InetAddress

import scala.util.matching.Regex

/**
 * The configuration parameters for Spark Exasol connector.
 *
 * These can be user provided when loading or defined in Spark configurations.
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
 * If both are defined, spark configs are used. If nothing is defined, then
 * default values are used.
 */
final case class ExasolConfiguration(
  host: String,
  port: Int,
  jdbc_options: String,
  username: String,
  password: String,
  max_nodes: Int,
  create_table: Boolean,
  drop_table: Boolean,
  batch_size: Int
)

object ExasolConfiguration {

  val IPv4_DIGITS: String = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)"
  val IPv4_REGEX: Regex = raw"""^$IPv4_DIGITS\.$IPv4_DIGITS\.$IPv4_DIGITS\.$IPv4_DIGITS$$""".r

  def getLocalHost(): String = InetAddress.getLocalHost.getHostAddress

  def checkHost(host: String): String = host match {
    case IPv4_REGEX(_*) => host
    case _ =>
      throw new IllegalArgumentException(
        "The host value should be an ip address of the first Exasol data node!"
      )
  }

  def checkJdbcOptions(str: String): String = {
    if (str.endsWith(";") || str.startsWith(";")) {
      throw new IllegalArgumentException(
        "Jdbc options should not start or end with semicolon"
      )
    }

    if (str.length > 0) {
      str
        .split(";")
        .foreach(kv => {
          if (kv.filter(_ == '=').length != 1) {
            throw new IllegalArgumentException(
              s"Invalid property: $kv does not have key=value format"
            )
          }
        })
    }
    str
  }

  @SuppressWarnings(
    Array("org.wartremover.warts.Overloading", "org.danielnixon.extrawarts.StringOpsPartial")
  )
  def apply(opts: Map[String, String]): ExasolConfiguration =
    ExasolConfiguration(
      host = checkHost(opts.getOrElse("host", getLocalHost())),
      port = opts.getOrElse("port", "8888").toInt,
      jdbc_options = checkJdbcOptions(opts.getOrElse("jdbc_options", "")),
      username = opts.getOrElse("username", "sys"),
      password = opts.getOrElse("password", "exasol"),
      max_nodes = opts.getOrElse("max_nodes", "200").toInt,
      create_table = opts.getOrElse("create_table", "false").toBoolean,
      drop_table = opts.getOrElse("drop_table", "false").toBoolean,
      batch_size = opts.getOrElse("batch_size", "1000").toInt
    )

}

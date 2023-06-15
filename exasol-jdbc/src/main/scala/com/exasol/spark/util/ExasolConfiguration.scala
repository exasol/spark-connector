package com.exasol.spark.util

import java.net.InetAddress

import scala.util.matching.Regex

import com.exasol.errorreporting.ExaError

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
  fingerprint: String,
  max_nodes: Int,
  create_table: Boolean,
  drop_table: Boolean,
  batch_size: Int
)

/**
 * A companion object that creates {@link ExasolConfiguration}.
 */
object ExasolConfiguration {

  private[this] val IPv4_DIGITS: String = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)"
  private[this] val IPv4_REGEX: Regex = raw"""^$IPv4_DIGITS\.$IPv4_DIGITS\.$IPv4_DIGITS\.$IPv4_DIGITS$$""".r
  private[this] val DEFAULT_MAX_NODES = "200"
  private[this] val DEFAULT_BATCH_SIZE = "1000"

  /**
   * Returns {@link ExasolConfiguration} from key-value options.
   *
   * It also validates key-value parameters.
   *
   * @param options key value options
   * @return an instance of {@link ExasolConfiguration}
   */
  def apply(options: Map[String, String]): ExasolConfiguration = {
    val host = options.getOrElse("host", getLocalHost())
    val jdbc_options = options.getOrElse("jdbc_options", "")
    checkHost(host)
    checkJdbcOptions(jdbc_options)

    ExasolConfiguration(
      host = host,
      port = options.getOrElse("port", "8888").toInt,
      jdbc_options = jdbc_options,
      username = options.getOrElse("username", "sys"),
      password = options.getOrElse("password", "exasol"),
      fingerprint = options.getOrElse("fingerprint", ""),
      max_nodes = options.getOrElse("max_nodes", DEFAULT_MAX_NODES).toInt,
      create_table = options.getOrElse("create_table", "false").toBoolean,
      drop_table = options.getOrElse("drop_table", "false").toBoolean,
      batch_size = options.getOrElse("batch_size", DEFAULT_BATCH_SIZE).toInt
    )
  }

  private[this] def getLocalHost(): String = InetAddress.getLocalHost().getHostAddress()

  private[util] def checkHost(host: String): String = host match {
    case IPv4_REGEX(_*) => host
    case _ =>
      throw new IllegalArgumentException(
        ExaError
          .messageBuilder("E-SEC-4")
          .message("The host value is not an IPv4 address.")
          .mitigation("The host value should be an IPv4 address of the first Exasol datanode.")
          .toString()
      )
  }

  private[this] def checkJdbcOptions(options: String): Unit = {
    checkStartsOrEndsWith(options, ";")
    if (!options.isEmpty()) {
      val keyValuePairs = options.split(";")
      checkContainsKeyValuePairs(keyValuePairs, "=")
    }
  }

  private[this] def checkStartsOrEndsWith(input: String, pattern: String): Unit =
    if (input.endsWith(pattern) || input.startsWith(pattern)) {
      throw new IllegalArgumentException(
        ExaError
          .messageBuilder("E-SEC-5")
          .message("JDBC options should not start or end with semicolon.")
          .mitigation("Please remove from beginning or end of JDBC options.")
          .toString()
      )
    }

  private[this] def checkContainsKeyValuePairs(options: Array[String], pattern: String): Unit =
    options.foreach { case keyValue =>
      if (keyValue.split(pattern).length != 2) {
        throw new IllegalArgumentException(
          ExaError
            .messageBuilder("E-SEC-6")
            .message("Parameter {{PARAMETER}} does not have key=value format.", keyValue)
            .mitigation("Please make sure parameters are encoded as key=value pairs.")
            .toString()
        )
      }
    }

}

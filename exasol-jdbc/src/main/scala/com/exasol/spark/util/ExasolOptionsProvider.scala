package com.exasol.spark.util

import java.net.InetAddress

import scala.util.matching.Regex

import com.exasol.errorreporting.ExaError
import com.exasol.spark.common.ExasolOptions
import com.exasol.spark.common.Option

import org.apache.spark.sql.util.CaseInsensitiveStringMap;

/**
 * A companion object that creates {@link ExasolOptions}.
 *
 * It creates the configuration parameters for Spark Exasol connector.
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
object ExasolOptionsProvider {

  private[this] val IPv4_DIGITS: String = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)"
  private[this] val IPv4_REGEX: Regex = raw"""^$IPv4_DIGITS\.$IPv4_DIGITS\.$IPv4_DIGITS\.$IPv4_DIGITS$$""".r

  /**
   * Returns {@link ExasolConfiguration} from key-value options.
   *
   * @param map key value map
   * @return an instance of {@link ExasolConfiguration}
   */
  def apply(map: Map[String, String]): ExasolOptions = {
    val javaMap = new java.util.HashMap[String, String]()
    map.foreach { case (key, value) => javaMap.put(key, value) }
    ExasolOptionsProvider(new CaseInsensitiveStringMap(javaMap))
  }

  /**
   * Returns {@link ExasolConfiguration} from key-value options.
   *
   * It also validates key-value parameters.
   *
   * @param map key value case-insensitive map
   * @return an instance of {@link ExasolConfiguration}
   */
  def apply(map: CaseInsensitiveStringMap): ExasolOptions = {
    val host = map.getOrDefault(Option.HOST.key(), getLocalHost())
    val jdbc_options = map.getOrDefault(Option.JDBC_OPTIONS.key(), "")
    checkHost(host)
    checkJdbcOptions(jdbc_options)
    ExasolOptions.from(map)
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

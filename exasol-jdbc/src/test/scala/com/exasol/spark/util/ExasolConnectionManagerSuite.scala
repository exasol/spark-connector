package com.exasol.spark.util

import org.apache.spark.sql.util.CaseInsensitiveStringMap

import com.exasol.jdbc.EXAConnection
import com.exasol.spark.common.ExasolOptions

import org.mockito.Mockito._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

class ExasolConnectionManagerSuite extends AnyFunSuite with Matchers with MockitoSugar {

  private[this] def getExasolOptions(map: Map[String, String]): ExasolOptions = {
    val javaMap = new java.util.HashMap[String, String]()
    map.foreach { case (key, value) => javaMap.put(key, value) }
    ExasolOptionsProvider(new CaseInsensitiveStringMap(javaMap))
  }

  private[this] def getManager(map: Map[String, String]): ExasolConnectionManager =
    ExasolConnectionManager(getExasolOptions(map))

  @SuppressWarnings(Array("scala:S1313")) // Hardcoded IP addresses are safe in tests
  private[this] val requiredOptions: Map[String, String] =
    Map("host" -> "10.0.0.1", "port" -> "8888", "query" -> "SELECT * FROM DUAL")

  test("check empty jdbc options returns correctly configured jdbc url") {
    assert(getExasolOptions(requiredOptions).getJdbcUrl() === "jdbc:exa:10.0.0.1:8888")
  }

  test("check extra jdbc options are correctly configured for establishing connection") {
    Map(
      "debug=1" -> "jdbc:exa:10.0.0.1:8888;debug=1",
      "debug=1;encryption=0" -> "jdbc:exa:10.0.0.1:8888;debug=1;encryption=0"
    ).foreach { case (jdbc_options, expectedJdbcUrl) =>
      val options = requiredOptions ++ Map("jdbc_options" -> jdbc_options)
      assert(getExasolOptions(options).getJdbcUrl() === expectedJdbcUrl)
    }
  }

  test("throws when jdbc options have invalid key-value property format") {
    val incorrectOpt = requiredOptions ++ Map("jdbc_options" -> "debug==1;encryption=0")
    val thrown = intercept[IllegalArgumentException] {
      getExasolOptions(incorrectOpt).getJdbcUrl()
    }
    val message = thrown.getMessage()
    assert(message.startsWith("E-SEC-6"))
    assert(message.contains("Parameter 'debug==1' does not have key=value format"))
  }

  test("throws when jdbc options start or end with semicolon") {
    Seq(";debug=1;encryption=0", "encryption=1;").foreach { case options =>
      val incorrectOpt = requiredOptions ++ Map("jdbc_options" -> options)
      val thrown = intercept[IllegalArgumentException] {
        getExasolOptions(incorrectOpt).getJdbcUrl()
      }
      val message = thrown.getMessage()
      assert(message.startsWith("E-SEC-5"))
      assert(message.contains("JDBC options should not start or end with semicolon"))
    }
  }

  private[this] def getMockedConnection(): EXAConnection = {
    val connection = mock[EXAConnection]
    when(connection.GetWorkerHosts()).thenReturn(Array("worker1", "worker2"))
    when(connection.GetWorkerPorts()).thenReturn(Array(21001, 21010))
    when(connection.GetWorkerToken()).thenReturn(12345L)
    connection
  }

  test("returns list of worker connections") {
    val expected = Seq(getWorkerJdbcUrl("worker1", 21001, 0, 12345L), getWorkerJdbcUrl("worker2", 21010, 1, 12345L))
    assert(getManager(requiredOptions).subConnections(getMockedConnection()) === expected)
  }

  test("returns jdbc url with fingerprint") {
    val options = requiredOptions ++ Map("fingerprint" -> "dummy_fingerprint")
    assert(getExasolOptions(options).getJdbcUrl() === "jdbc:exa:10.0.0.1/dummy_fingerprint:8888")
  }

  test("returns jdbc url without fingerprint if validateservercertificate=0") {
    val options = requiredOptions ++ Map(
      "jdbc_options" -> "validateservercertificate=0",
      "fingerprint" -> "dummy_fingerprint"
    )
    assert(getExasolOptions(options).getJdbcUrl() === "jdbc:exa:10.0.0.1:8888;validateservercertificate=0")
  }

  test("returns list of worker connections with fingerprint") {
    val options = requiredOptions ++ Map("fingerprint" -> "fp", "jdbc_options" -> "debug=1")
    val expected = Seq(
      s"${getWorkerJdbcUrl("worker1/fp", 21001, 0, 12345L)};debug=1",
      s"${getWorkerJdbcUrl("worker2/fp", 21010, 1, 12345L)};debug=1"
    )
    assert(getManager(options).subConnections(getMockedConnection()) === expected)
  }

  private[this] def getWorkerJdbcUrl(host: String, port: Int, id: Int, token: Long): String =
    s"jdbc:exa-worker:$host:$port;workerID=$id;workertoken=$token"

}

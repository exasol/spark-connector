package com.exasol.spark.util

import com.exasol.jdbc.EXAConnection

import org.mockito.Mockito._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

class ExasolConnectionManagerSuite extends AnyFunSuite with Matchers with MockitoSugar {

  def getManager(options: Map[String, String]): ExasolConnectionManager =
    ExasolConnectionManager(ExasolConfiguration(options))

  def getJdbcUrl(options: Map[String, String]): String =
    getManager(options).getJdbcConnectionString()

  @SuppressWarnings(Array("scala:S1313")) // Hardcoded IP addresses are safe in tests
  val requiredOptions: Map[String, String] = Map("host" -> "10.0.0.1", "port" -> "8888")

  test("check empty jdbc options returns correctly configured jdbc url") {
    assert(getJdbcUrl(requiredOptions) === "jdbc:exa:10.0.0.1:8888")
  }

  test("check extra jdbc options are correctly configured for establishing connection") {
    Map(
      "debug=1" -> "jdbc:exa:10.0.0.1:8888;debug=1",
      "debug=1;encryption=0" -> "jdbc:exa:10.0.0.1:8888;debug=1;encryption=0"
    ).foreach { case (jdbc_options, expectedJdbcUrl) =>
      val options = requiredOptions ++ Map("jdbc_options" -> jdbc_options)
      assert(getJdbcUrl(options) === expectedJdbcUrl)
    }
  }

  test("throws when jdbc options have invalid key-value property format") {
    val incorrectOpt = requiredOptions ++ Map("jdbc_options" -> "debug==1;encryption=0")
    val thrown = intercept[IllegalArgumentException] {
      getJdbcUrl(incorrectOpt)
    }
    val message = thrown.getMessage()
    assert(message.startsWith("E-SEC-6"))
    assert(message.contains("Parameter 'debug==1' does not have key=value format"))
  }

  test("throws when jdbc options start or end with semicolon") {
    Seq(";debug=1;encryption=0", "encryption=1;").foreach { case options =>
      val incorrectOpt = requiredOptions ++ Map("jdbc_options" -> options)
      val thrown = intercept[IllegalArgumentException] {
        getJdbcUrl(incorrectOpt)
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
    assert(getJdbcUrl(options) === "jdbc:exa:10.0.0.1/dummy_fingerprint:8888")
  }

  test("returns jdbc url without fingerprint if validateservercertificate=0") {
    val options = requiredOptions ++ Map(
      "jdbc_options" -> "validateservercertificate=0",
      "fingerprint" -> "dummy_fingerprint"
    )
    assert(getJdbcUrl(options) === "jdbc:exa:10.0.0.1:8888;validateservercertificate=0")
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

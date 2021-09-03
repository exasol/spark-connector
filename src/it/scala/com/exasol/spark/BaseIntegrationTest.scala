package com.exasol.spark

import com.exasol.containers.ExasolContainer
import com.exasol.spark.util.ExasolConfiguration
import com.exasol.spark.util.ExasolConnectionManager

import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

/**
 * A base integration suite with Exasol docker container setup.
 */
trait BaseIntegrationTest extends AnyFunSuite with BeforeAndAfterAll {

  private[this] val DEFAULT_EXASOL_DOCKER_IMAGE = "7.1.0-d1"

  val network = DockerNamedNetwork("spark-it-network", true)
  val container = {
    val c: ExasolContainer[_] = new ExasolContainerWithReuse(getExasolDockerImageVersion())
    c.withExposedPorts(8563)
    c.withNetwork(network)
    c
  }

  var jdbcHost: String = _
  var jdbcPort: String = _
  var exasolConnectionManager: ExasolConnectionManager = _

  def prepareExasolDatabase(): Unit = {
    container.start()
    jdbcHost = container.getDockerNetworkInternalIpAddress()
    jdbcPort = s"${container.getDefaultInternalDatabasePort()}"
    exasolConnectionManager = ExasolConnectionManager(ExasolConfiguration(getConfiguration()))
  }

  def getConfiguration(): Map[String, String] = Map(
    "host" -> jdbcHost,
    "port" -> jdbcPort,
    "username" -> container.getUsername(),
    "password" -> container.getPassword(),
    "jdbc_options" -> "validateservercertificate=0",
    "max_nodes" -> "200"
  )

  override def beforeAll(): Unit =
    prepareExasolDatabase()

  override def afterAll(): Unit = {
    container.stop()
    network.close()
  }

  private[this] def getExasolDockerImageVersion(): String =
    System.getProperty("EXASOL_DOCKER_VERSION", DEFAULT_EXASOL_DOCKER_IMAGE)

}

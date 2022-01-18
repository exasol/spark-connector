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

  private[this] val DEFAULT_EXASOL_DOCKER_IMAGE = "7.1.4"
  private[this] val JDBC_URL_PATTERN = raw"""jdbc:exa:[^/]+/([^:]+):.*""".r

  val network = DockerNamedNetwork("spark-it-network", true)
  val container = {
    val c: ExasolContainer[_] = new ExasolContainerWithReuse(getExasolDockerImageVersion())
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

  def getConfiguration(): Map[String, String] = {
    val defaultOptions = Map(
      "host" -> jdbcHost,
      "port" -> jdbcPort,
      "username" -> container.getUsername(),
      "password" -> container.getPassword(),
      "max_nodes" -> "200"
    )
    if (imageSupportsFingerprint()) {
      defaultOptions ++ Map("fingerprint" -> getFingerprint())
    } else {
      defaultOptions ++ Map("jdbc_options" -> "validateservercertificate=0")
    }
  }

  def getFingerprint(): String =
    JDBC_URL_PATTERN.findFirstMatchIn(container.getJdbcUrl()) match {
      case Some(m) => m.group(1)
      case _       => ""
    }

  def imageSupportsFingerprint(): Boolean = {
    val image = container.getDockerImageReference()
    (image.getMajor() >= 7) && (image.getMinor() >= 1)
  }

  override def beforeAll(): Unit =
    prepareExasolDatabase()

  override def afterAll(): Unit = {
    container.stop()
    network.close()
  }

  private[this] def getExasolDockerImageVersion(): String = {
    val dockerVersion = System.getenv("EXASOL_DOCKER_VERSION")
    if (dockerVersion == null) {
      DEFAULT_EXASOL_DOCKER_IMAGE
    } else {
      dockerVersion
    }
  }

}

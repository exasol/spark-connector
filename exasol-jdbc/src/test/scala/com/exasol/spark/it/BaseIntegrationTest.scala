package com.exasol.spark

import org.apache.spark.sql.util.CaseInsensitiveStringMap

import com.exasol.containers.ExasolContainer
import com.exasol.spark.common.ExasolOptions
import com.exasol.spark.util.ExasolConnectionManager
import com.exasol.spark.util.ExasolOptionsProvider

import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

/**
 * A base integration suite with Exasol docker container setup.
 */
trait BaseIntegrationTest extends AnyFunSuite with BeforeAndAfterAll {

  private[this] val DEFAULT_EXASOL_DOCKER_IMAGE = "8.18.1"

  val network = DockerNamedNetwork("spark-it-network", true)
  val container = {
    val c: ExasolContainer[_] = new ExasolContainerWithReuse(getExasolDockerImageVersion())
    c.withNetwork(network)
    c
  }

  var jdbcHost: String = _
  var jdbcPort: String = _
  var exasolConnectionManager: ExasolConnectionManager = _

  override def beforeAll(): Unit =
    prepareExasolDatabase()

  override def afterAll(): Unit = {
    container.stop()
    network.close()
  }

  def prepareExasolDatabase(): Unit = {
    container.start()
    jdbcHost = container.getDockerNetworkInternalIpAddress()
    jdbcPort = s"${container.getDefaultInternalDatabasePort()}"
    exasolConnectionManager = ExasolConnectionManager(getExasolOptions())
  }

  def getDefaultOptions(): Map[String, String] = Map(
    "host" -> jdbcHost,
    "port" -> jdbcPort,
    "username" -> container.getUsername(),
    "password" -> container.getPassword(),
    "fingerprint" -> getFingerprint(),
    "max_nodes" -> "200"
  )

  def getExasolOptions(): ExasolOptions = {
    val javaMap = new java.util.HashMap[String, String]()
    getDefaultOptions().foreach { case (key, value) => javaMap.put(key, value) }
    ExasolOptionsProvider(new CaseInsensitiveStringMap(javaMap))
  }

  def getFingerprint(): String =
    container.getTlsCertificateFingerprint().get()

  private[this] def getExasolDockerImageVersion(): String = {
    val dockerVersion = System.getenv("EXASOL_DOCKER_VERSION")
    if (dockerVersion == null) {
      DEFAULT_EXASOL_DOCKER_IMAGE
    } else {
      dockerVersion
    }
  }

}

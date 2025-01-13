package com.exasol.spark

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

  private[this] val DEFAULT_EXASOL_DOCKER_IMAGE = "8.32.0"

  val network = DockerNamedNetwork("spark-it-network", true)
  val container = {
    val c: ExasolContainer[_] = new ExasolContainerWithReuse(getExasolDockerImageVersion())
    c.withNetwork(network)
    c
  }

  var exasolConnectionManager: ExasolConnectionManager = _

  override def beforeAll(): Unit =
    container.start()

  override def afterAll(): Unit = {
    container.stop()
    network.close()
  }

  def getDefaultOptions(): Map[String, String] = {
    val options = Map(
      "host" -> container.getDockerNetworkInternalIpAddress(),
      "port" -> s"${container.getDefaultInternalDatabasePort()}",
      "username" -> container.getUsername(),
      "password" -> container.getPassword(),
      "max_nodes" -> "200"
    )
    if (getFingerprint().isPresent()) {
      options ++ Map("fingerprint" -> getFingerprint().get())
    } else {
      options
    }
  }

  def getExasolOptions(map: Map[String, String]): ExasolOptions =
    ExasolOptionsProvider(map)

  def getFingerprint(): java.util.Optional[String] =
    container.getTlsCertificateFingerprint()

  private[this] def getExasolDockerImageVersion(): String = {
    val dockerVersion = System.getenv("EXASOL_DOCKER_VERSION")
    if (dockerVersion == null) {
      DEFAULT_EXASOL_DOCKER_IMAGE
    } else {
      dockerVersion
    }
  }

}

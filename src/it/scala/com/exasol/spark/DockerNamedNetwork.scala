package com.exasol.spark

import java.util.concurrent.ConcurrentHashMap

import org.apache.spark.internal.Logging

import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.testcontainers.DockerClientFactory
import org.testcontainers.containers.Network
import org.testcontainers.utility.ResourceReaper

/**
 * A reusable docker network.
 *
 * At the moment, the docker container {@code reuse} is ignored when a
 * network is attached to a container. This class creates docker network
 * that can be attached to reusable container.
 *
 * @param name name of the docker network
 * @param reuse boolean value to indicate reusability
 */
class DockerNamedNetwork(name: String, reuse: Boolean) extends Network with Logging {

  private[this] val id = getNetworkId()

  override def getId(): String = id

  override def close(): Unit =
    if (reuse) {
      logWarning(
        "Skipping the network termination because 'reuse' is enabled. Please destroy "
          + s"the network manually using 'docker network rm $id'."
      )
    } else {
      ResourceReaper.instance().removeNetworkById(id)
    }

  override def apply(base: Statement, description: Description): Statement =
    throw new UnsupportedOperationException()

  private[this] def getNetworkId(): String = {
    val network = DockerClientFactory
      .lazyClient()
      .listNetworksCmd()
      .withNameFilter(name)
      .exec()
      .stream()
      .findAny()

    if (network.isPresent()) {
      network.get().getId()
    } else {
      createNetwork()
    }
  }

  private[this] def createNetwork(): String =
    DockerClientFactory
      .lazyClient()
      .createNetworkCmd()
      .withName(name)
      .exec()
      .getId()
}

object DockerNamedNetwork extends Logging {

  private[this] val namedNetworks: ConcurrentHashMap[String, DockerNamedNetwork] =
    new ConcurrentHashMap()

  def apply(name: String): DockerNamedNetwork =
    apply(name, false)

  def apply(name: String, reuse: Boolean): DockerNamedNetwork = {
    if (!namedNetworks.containsKey(name)) {
      namedNetworks.put(name, new DockerNamedNetwork(name, reuse))
    }
    namedNetworks.get(name)
  }
}

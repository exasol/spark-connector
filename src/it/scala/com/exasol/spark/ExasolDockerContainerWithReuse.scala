package com.exasol.spark

import org.apache.spark.internal.Logging

import com.exasol.containers.ExasolContainer

import org.testcontainers.utility.TestcontainersConfiguration

/**
 * An Exasol Testcontainer with {@code reuse} enabled.
 */
class ExasolContainerWithReuse(imageName: String)
    extends ExasolContainer(imageName)
    with Logging {

  override def configure(): Unit = {
    super.configure()
    withReuse(true)
  }

  override def stop(): Unit =
    if (isShouldBeReused() && TestcontainersConfiguration
          .getInstance()
          .environmentSupportsReuse()) {
      logWarning(
        "Keeping the container running because 'reuse' is enabled. Please stop and "
          + "remove the container manually using 'docker rm -f <CONTAINER_ID>'."
      )
    } else {
      super.stop()
    }

}

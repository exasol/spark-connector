package com.exasol.spark

import org.scalatest.Suite
import com.dimafeng.testcontainers.Container
import com.dimafeng.testcontainers.ExasolDockerContainer
import com.dimafeng.testcontainers.ForAllTestContainer

/** A Base Testing Suite with Exasol DB Docker Container */
trait BaseSuite extends ForAllTestContainer { self: Suite =>

  override val container = ExasolDockerContainer()

}

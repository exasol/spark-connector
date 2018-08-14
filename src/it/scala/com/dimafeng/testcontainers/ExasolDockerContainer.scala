package com.dimafeng.testcontainers

import scala.language.existentials
import org.testcontainers.containers.{ExasolDockerContainer => OTCExasolDockerContainer}

class ExasolDockerContainer(imageName: Option[String] = None)
    extends SingleContainer[OTCExasolDockerContainer[_]] {

  type OTCContainer = OTCExasolDockerContainer[T] forSome {
    type T <: OTCExasolDockerContainer[T]
  }

  override val container: OTCContainer = imageName match {
    case Some(imageName) => new OTCExasolDockerContainer(imageName)
    case None            => new OTCExasolDockerContainer()
  }

  def driverClassName: String = container.getDriverClassName()

  def jdbcUrl: String = container.getJdbcUrl()

  def username: String = container.getUsername()

  def password: String = container.getPassword()

  def testQueryString: String = container.getTestQueryString()
}

object ExasolDockerContainer {
  def apply(imageName: String = null): ExasolDockerContainer =
    new ExasolDockerContainer(Option(imageName))
}

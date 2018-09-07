package com.exasol.spark

import java.sql.DriverManager
import java.sql.SQLException

import org.scalatest.FunSuite

class ExasolDockerContainerSuite extends FunSuite with BaseDockerSuite {

  test("exasol/docker-db container should be started") {
    Class.forName(container.driverClassName) // scalastyle:ignore classForName

    val connectionStr =
      s"${container.jdbcUrl};user=${container.username};password=${container.password}"
    val connection = DriverManager.getConnection(connectionStr)
    val prepareStatement = connection.prepareStatement(container.testQueryString)

    try {
      val resultSet = prepareStatement.executeQuery()
      while (resultSet.next()) {
        assert(resultSet.getInt(1) == 1)
      }
      resultSet.close()
    } catch {
      case ex: SQLException => ex.printStackTrace()
    } finally {
      prepareStatement.close()
    }
    connection.close()
  }

}

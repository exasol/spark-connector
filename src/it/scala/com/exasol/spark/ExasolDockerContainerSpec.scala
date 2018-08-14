package com.exasol.spark

import java.sql.DriverManager
import java.sql.SQLException
import org.scalatest.FunSuite

class ExasolDockerContainerSpec extends FunSuite with BaseSuite {

  test("exasol/docker-db container should be started") {
    Class.forName(container.driverClassName)

    val connection = DriverManager.getConnection(container.jdbcUrl);
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

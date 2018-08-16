package com.exasol.spark.util

import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.ConcurrentHashMap

import com.exasol.jdbc.EXAConnection

case class ExasolConnectionManager(config: ExasolConfiguration) {

  def mainConnectionUrl(): String =
    s"jdbc:exa:${config.host}:${config.port}"

  def mainConnection(): EXAConnection =
    ExasolConnectionManager.makeConnection(mainConnectionUrl, config.username, config.password)

  def initParallel(mainConn: EXAConnection): Unit =
    mainConn.EnterParallel(config.max_nodes)

  def subConnections(mainConn: EXAConnection): Seq[String] = {
    val hosts = mainConn.GetSlaveHosts()
    val ports = mainConn.GetSlavePorts()
    hosts
      .zip(ports)
      .zipWithIndex
      .map {
        case ((host, port), idx) =>
          s"jdbc:exa-slave:$host:$port;slaveID=$idx;slaveToken=${mainConn.GetSlaveToken()}"
      }
  }

  def subConnection(subConnectionUrl: String): EXAConnection =
    ExasolConnectionManager.makeConnection(subConnectionUrl, config.username, config.password)

}

object ExasolConnectionManager {

  private[this] val JDBC_LOGIN_TIMEOUT: Int = 30

  private[this] val connections: ConcurrentHashMap[String, EXAConnection] =
    new ConcurrentHashMap()

  private[this] def createConnection(
    url: String,
    username: String,
    password: String
  ): EXAConnection = {
    Class.forName("com.exasol.jdbc.EXADriver") // scalastyle:off classForName
    DriverManager.setLoginTimeout(JDBC_LOGIN_TIMEOUT)
    val conn = DriverManager.getConnection(url, username, password)
    conn.asInstanceOf[EXAConnection]
  }

  def makeConnection(url: String, username: String, password: String): EXAConnection = {
    if (!connections.containsKey(url)) {
      connections.putIfAbsent(url, createConnection(url, username, password))
    }
    connections.get(url)
  }

}

package com.exasol.spark.util

import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.ConcurrentHashMap

import com.exasol.jdbc.EXAConnection

import com.typesafe.scalalogging.LazyLogging

final case class ExasolConnectionManager(config: ExasolConfiguration) {

  def mainConnectionUrl(): String =
    s"jdbc:exa:${config.host}:${config.port}"

  def mainConnection(): EXAConnection =
    ExasolConnectionManager.makeConnection(mainConnectionUrl, config.username, config.password)

  def initParallel(mainConn: EXAConnection): Int =
    mainConn.EnterParallel(config.max_nodes)

  def subConnections(mainConn: EXAConnection): Seq[String] = {
    val hosts = mainConn.GetSlaveHosts()
    val ports = mainConn.GetSlavePorts()
    hosts
      .zip(ports)
      .zipWithIndex
      .map {
        case ((host, port), idx) =>
          s"jdbc:exa-slave:$host:$port;slaveID=$idx;slavetoken=${mainConn.GetSlaveToken()}"
      }
  }

  def subConnection(subConnectionUrl: String): EXAConnection =
    ExasolConnectionManager.makeConnection(subConnectionUrl, config.username, config.password)

  def withConnection[T](handle: EXAConnection => T): T =
    ExasolConnectionManager
      .withConnection(mainConnectionUrl, config.username, config.password)(handle)

}

object ExasolConnectionManager extends LazyLogging {

  private[this] val JDBC_LOGIN_TIMEOUT: Int = 30

  private[this] val connections: ConcurrentHashMap[String, EXAConnection] =
    new ConcurrentHashMap()

  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
  private[this] def createConnection(
    url: String,
    username: String,
    password: String
  ): EXAConnection = {
    val _ = Class.forName("com.exasol.jdbc.EXADriver") // scalastyle:ignore classForName
    DriverManager.setLoginTimeout(JDBC_LOGIN_TIMEOUT)
    val conn = DriverManager.getConnection(url, username, password)
    conn.asInstanceOf[EXAConnection]
  }

  def makeConnection(url: String, username: String, password: String): EXAConnection = {
    logger.debug(s"Making a connection using $url")
    val _ = connections.putIfAbsent(url, createConnection(url, username, password))
    connections.get(url)
  }

  def withConnection[T](url: String, username: String, password: String)(
    handle: EXAConnection => T
  ): T =
    using(createConnection(url, username, password))(handle)

  def using[A <: AutoCloseable, T](resource: A)(fn: A => T): T =
    try {
      fn(resource)
    } finally {
      resource.close()
    }

}

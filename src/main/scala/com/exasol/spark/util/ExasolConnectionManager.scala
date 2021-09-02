package com.exasol.spark.util

import java.sql.DriverManager
import java.util.concurrent.ConcurrentHashMap

import scala.util.Try

import org.apache.spark.internal.Logging

import com.exasol.errorreporting.ExaError
import com.exasol.jdbc.EXAConnection
import com.exasol.jdbc.EXAResultSet
import com.exasol.jdbc.EXAStatement

/**
 * A class that provides and manages Exasol connections.
 *
 * It is okay to serialize this class to Spark workers, it will create Exasol
 * jdbc connections within each executor JVM.
 *
 * @param config An [[ExasolConfiguration]] with user provided or runtime
 *        configuration parameters
 */
final case class ExasolConnectionManager(config: ExasolConfiguration) {

  private[this] val mainJdbcConnectionUrl = s"jdbc:exa:${config.host}:${config.port}"

  /** A regular Exasol jdbc connection string */
  def getJdbcConnectionString(): String =
    getConnectionStringWithOptions(mainJdbcConnectionUrl)

  def mainConnection(): EXAConnection =
    ExasolConnectionManager.makeConnection(
      getJdbcConnectionString(),
      config.username,
      config.password
    )

  def writerMainConnection(): EXAConnection =
    ExasolConnectionManager.makeConnection(
      s"${getJdbcConnectionString()};autocommit=0",
      config.username,
      config.password
    )

  /**
   * A single non-pooled [[com.exasol.jdbc.EXAConnection]] connection.
   *
   * Maintaining and gracefully closing the connection is a responsibility of
   * the user.
   */
  def getConnection(): EXAConnection =
    ExasolConnectionManager.createConnection(
      getJdbcConnectionString(),
      config.username,
      config.password
    )

  /**
   * Starts a parallel sub-connections from the main JDBC connection.
   *
   * @param mainConnection the main connection
   * @return the number of parallel connections
   */
  def initParallel(mainConnection: EXAConnection): Int =
    mainConnection.EnterParallel(config.max_nodes)

  /**
   * Returns the list of all parallel sub-connection URLs.
   *
   * @param mainConnection the main connection
   * @return the list of sub-connections URLs
   */
  def subConnections(mainConnection: EXAConnection): Seq[String] = {
    val hosts = mainConnection.GetWorkerHosts()
    val ports = mainConnection.GetWorkerPorts()
    val token = mainConnection.GetWorkerToken()
    hosts
      .zip(ports)
      .zipWithIndex
      .map { case ((host, port), idx) =>
        getConnectionStringWithOptions(s"jdbc:exa-worker:$host:$port;workerID=$idx;workertoken=$token")
      }
  }

  /**
   * Creates a JDBC connection using one of the sub-connection URL
   * strings.
   *
   * @param subConnectionUrl one of the sub-connection strings
   * @return a JDBC connection on the separate parallel connection
   */
  def subConnection(subConnectionUrl: String): EXAConnection =
    ExasolConnectionManager.makeConnection(subConnectionUrl, config.username, config.password)

  /**
   * A method to run with a new connection.
   *
   * This method closes the connection afterwards.
   *
   * @param handle A code block that needs to be run with a connection
   * @tparam T A result type of the `handle` function
   * @return A result of `handle` function
   */
  def withConnection[T](handle: EXAConnection => T): T =
    ExasolConnectionManager.using(getConnection)(handle)

  /**
   * A helper method to run with a new statement.
   *
   * This method closes the resources afterwards.
   *
   * @param handle A code block that needs to be run with a given statement
   * @tparam T A result type of the `handle` function
   * @return A result of `handle` function
   */
  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
  def withStatement[T](handle: EXAStatement => T): T = withConnection[T] { conn =>
    val stmt = conn.createStatement().asInstanceOf[EXAStatement]
    ExasolConnectionManager.using(stmt)(handle)
  }

  /**
   * A helper method to run `stmt.execute` given a list of queries.
   *
   * @param queries A list of SQL queries to run
   * @return A [[scala.Unit]] result
   */
  def withExecute(queries: Seq[String]): Unit = withStatement[Unit] { stmt =>
    queries.foreach { query =>
      stmt.execute(query)
    }
    ()
  }

  /**
   * A helper method to run `stmt.executeQuery` given a query.
   *
   * @param query A query string to executeQuery
   * @tparam T A result type of the `handle` function
   * @return A result of `handle` function
   */
  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
  def withExecuteQuery[T](query: String)(handle: EXAResultSet => T): T = withStatement[T] { stmt =>
    val rs = stmt.executeQuery(query).asInstanceOf[EXAResultSet]
    ExasolConnectionManager.using(rs)(handle)
  }

  /** Given a query with `count(*)` returns the result. */
  def withCountQuery(query: String): Long = withExecuteQuery[Long](query) { rs =>
    val cnt = if (rs.next()) {
      rs.getLong(1)
    } else {
      throw new IllegalStateException(
        ExaError
          .messageBuilder("E-SEC-9")
          .message("Could not run 'count' query.")
          .mitigation(
            "Please check that JDBC connection is available or query statement is valid."
          )
          .toString()
      )
    }
    cnt
  }

  /**
   * Checks if table already exists, if so should return true otherwise false.
   *
   * TODO: This should be changed to Exasol specific checks. For example, by
   *       using EXA_USER_TABLES.
   *
   * @param tableName A Exasol table name including schema, e.g.
   *        `schema.tableName`
   * @return `true` if table exists, otherwise return `false`
   */
  def tableExists(tableName: String): Boolean = withConnection[Boolean] { conn =>
    val tryEither = Try {
      val stmt = conn.prepareStatement(s"SELECT * FROM $tableName WHERE 1=0")
      try {
        stmt.executeQuery()
      } finally {
        stmt.close()
      }
    }

    tryEither.isSuccess
  }

  /**
   * Given an Exasol table name (with schema, e.g mySchema.myTable format),
   * truncates it.
   */
  def truncateTable(tableName: String): Unit = withStatement[Unit] { stmt =>
    val _ = stmt.executeUpdate(s"TRUNCATE TABLE $tableName")
    ()
  }

  /**
   * Given an Exasol table name (with schema, e.g mySchema.myTable format), drop
   * it.
   */
  def dropTable(tableName: String): Unit = withStatement[Unit] { stmt =>
    val _ = stmt.executeUpdate(s"DROP TABLE IF EXISTS $tableName")
    ()
  }

  /**
   * Creates a table in Exasol.
   *
   * @param tableName A table name (with both schema and table, e.g.
   *        myschem.my_table)
   * @param tabelSchema A schema string of table with column names and types
   */
  def createTable(tableName: String, tableSchema: String): Unit = withStatement[Unit] { stmt =>
    val _ = stmt.executeUpdate(s"CREATE TABLE $tableName ($tableSchema)")
    ()
  }

  private[this] def getConnectionStringWithOptions(url: String): String = {
    val jdbcOptions = config.jdbc_options
    if (jdbcOptions == null || jdbcOptions.isEmpty) {
      url
    } else {
      s"$url;$jdbcOptions"
    }
  }

}

/**
 * The companion object to [[ExasolConnectionManager]].
 */
object ExasolConnectionManager extends Logging {

  private[this] val JDBC_LOGIN_TIMEOUT: Int = 30

  private[this] val connections: ConcurrentHashMap[String, EXAConnection] =
    new ConcurrentHashMap()

  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
  private[util] def createConnection(
    url: String,
    username: String,
    password: String
  ): EXAConnection = {
    val _ = Class.forName("com.exasol.jdbc.EXADriver") // scalastyle:ignore classForName
    DriverManager.setLoginTimeout(JDBC_LOGIN_TIMEOUT)
    val conn = DriverManager.getConnection(url, username, password)
    conn.asInstanceOf[EXAConnection]
  }

  private[this] def removeIfClosed(url: String): Unit = {
    val conn = connections.get(url)
    if (conn != null && conn.isClosed) {
      logInfo(s"Connection $url is closed, removing it from the pool")
      val _ = connections.remove(url)
    }
  }

  def makeConnection(url: String, username: String, password: String): EXAConnection = {
    logDebug(s"Making a connection using url = $url")
    removeIfClosed(url)
    if (!connections.containsKey(url)) {
      val _ = connections.put(url, createConnection(url, username, password))
    }
    connections.get(url)
  }

  def using[A <: AutoCloseable, T](resource: A)(fn: A => T): T =
    try {
      fn(resource)
    } finally {
      resource.close()
    }

}

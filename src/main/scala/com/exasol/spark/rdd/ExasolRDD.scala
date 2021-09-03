package com.exasol.spark.rdd

import java.sql.ResultSet
import java.sql.Statement

import scala.util.control.NonFatal

import org.apache.spark.Partition
import org.apache.spark.SparkContext
import org.apache.spark.TaskContext
import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD
import org.apache.spark.scheduler.SparkListener
import org.apache.spark.scheduler.SparkListenerApplicationEnd
import org.apache.spark.sql.Row
import org.apache.spark.sql.execution.datasources.jdbc.JdbcUtils
import org.apache.spark.sql.types.StructType

import com.exasol.errorreporting.ExaError
import com.exasol.jdbc.EXAConnection
import com.exasol.jdbc.EXAResultSet
import com.exasol.spark.util.ExasolConnectionManager

/**
 * An [[org.apache.spark.rdd.RDD]] reads / writes data from an Exasol tables.
 *
 * The [[com.exasol.spark.rdd.ExasolRDD]] holds data in parallel from each
 * Exasol physical nodes.
 */
class ExasolRDD(
  @transient val sc: SparkContext,
  queryString: String,
  querySchema: StructType,
  manager: ExasolConnectionManager
) extends RDD[Row](sc, Nil)
    with Logging {

  // scalastyle:off null
  @transient private var mainConnection: EXAConnection = null
  @transient private var mainStatement: Statement = null
  @transient private var mainResultSet: EXAResultSet = null
  // scalastyle:on

  def closeMainResources(): Unit = {
    if (mainConnection != null) {
      mainConnection.close()
    }
    if (mainStatement != null) {
      mainStatement.close()
    }
    if (mainResultSet != null) {
      mainResultSet.close()
    }
  }

  def createMainConnection(): EXAConnection = {
    val conn = manager.mainConnection()
    if (conn == null) {
      logError("Main EXAConnection is null!")
      throw new RuntimeException(
        ExaError
          .messageBuilder("F-SEC-11")
          .message("Could not establish main JDBC connection for query.")
          .mitigation("Please make sure that there is a network connection between Spark and Exasol clusters.")
          .toString()
      )
    }

    val cnt = manager.initParallel(conn)
    logInfo(s"Initiated $cnt parallel exasol (sub) connections")

    // Close Exasol main connection when SparkContext finishes. This is a
    // lifetime of a Spark application.
    sc.addSparkListener(new SparkListener {
      override def onApplicationEnd(appEnd: SparkListenerApplicationEnd): Unit =
        closeMainResources()
    })

    conn
  }

  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
  override def getPartitions: Array[Partition] = {
    mainConnection = createMainConnection()
    mainStatement = mainConnection.createStatement()
    logInfo(s"Executing enriched query '$queryString'.")
    mainResultSet = mainStatement.executeQuery(queryString).asInstanceOf[EXAResultSet]
    val handle = mainResultSet.GetHandle()
    val partitions = manager
      .subConnections(mainConnection)
      .zipWithIndex
      .map { case (url, idx) => ExasolRDDPartition(idx, handle, url) }
    logInfo(s"The number of partitions is ${partitions.size}")
    partitions.toArray
  }

  // scalastyle:off null return
  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf", "org.wartremover.warts.Return"))
  override def compute(split: Partition, context: TaskContext): Iterator[Row] = {
    var closed = false
    var resultSet: ResultSet = null
    val stmt: Statement = null
    var conn: EXAConnection = null

    def close(): Unit = {
      if (closed) {
        return
      }

      try {
        if (resultSet != null) { // && !resultSet.isClosed) {
          resultSet.close()
        }
      } catch {
        case e: Exception => logWarning("Received an exception closing sub resultSet", e)
      }

      try {
        if (stmt != null && !stmt.isClosed) {
          stmt.close()
        }
      } catch {
        case e: Exception => logWarning("Received an exception closing sub statement", e)
      }

      try {
        if (conn != null) {
          if (!conn.isClosed && !conn.getAutoCommit) {
            try {
              conn.commit()
            } catch {
              case NonFatal(e) => logWarning("Received exception committing sub connection", e)
            }
          }
          conn.close()
          logInfo("Closed a sub connection")
        }
      } catch {
        case e: Exception => logWarning("Received an exception closing sub connection", e)
      }

      closed = true
    }

    val _ = context.addTaskCompletionListener[Unit] { _ =>
      close()
    }

    val partition: ExasolRDDPartition = split.asInstanceOf[ExasolRDDPartition]
    val subHandle: Int = partition.handle

    logInfo(s"Sub connection with url = ${partition.connectionUrl} and handle = $subHandle")

    if (subHandle == -1) {
      logInfo("Sub connection handle is -1, no results, return empty iterator")
      return Iterator.empty
    }

    conn = manager.subConnection(partition.connectionUrl)
    resultSet = conn.DescribeResult(subHandle)

    JdbcUtils.resultSetToRows(resultSet, querySchema)
  }
  // scalastyle:on null return

}

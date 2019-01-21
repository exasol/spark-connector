package com.exasol.spark.rdd

import java.sql.ResultSet
import java.sql.Statement

import scala.util.control.NonFatal

import org.apache.spark.Partition
import org.apache.spark.SparkContext
import org.apache.spark.TaskContext
import org.apache.spark.rdd.RDD
import org.apache.spark.scheduler.SparkListener
import org.apache.spark.scheduler.SparkListenerApplicationEnd
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StructType

import com.exasol.jdbc.EXAConnection
import com.exasol.jdbc.EXAResultSet
import com.exasol.spark.util.Converter
import com.exasol.spark.util.ExasolConnectionManager

import com.typesafe.scalalogging.LazyLogging

/**
 * An [[org.apache.spark.rdd.RDD]] reads / writes data from an Exasol tables
 *
 * The [[com.exasol.spark.rdd.ExasolRDD]] holds data in parallel from each Exasol physical nodes.
 *
 */
class ExasolRDD(
  @transient val sc: SparkContext,
  queryString: String,
  querySchema: StructType,
  manager: ExasolConnectionManager
) extends RDD[Row](sc, Nil)
    with LazyLogging {

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
      logger.error("Main EXAConnection is null!")
      throw new RuntimeException("Could not establish main connection to Exasol!")
    }

    val cnt = manager.initParallel(conn)
    logger.info(s"Initiated $cnt parallel exasol (sub) connections")

    // Close Exasol main connection when SparkContext finishes. This is a lifetime of a Spark
    // application.
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
    mainResultSet = mainStatement.executeQuery(queryString).asInstanceOf[EXAResultSet]

    val handle = mainResultSet.GetHandle()

    val partitions = manager
      .subConnections(mainConnection)
      .zipWithIndex
      .map { case (url, idx) => ExasolRDDPartition(idx, handle, url) }

    logger.info(s"The number of partitions is ${partitions.size}")

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
        case e: Exception => logger.warn("Received an exception closing sub resultSet", e)
      }

      try {
        if (stmt != null && !stmt.isClosed) {
          stmt.close()
        }
      } catch {
        case e: Exception => logger.warn("Received an exception closing sub statement", e)
      }

      try {
        if (conn != null) {
          if (!conn.isClosed && !conn.getAutoCommit) {
            try {
              conn.commit()
            } catch {
              case NonFatal(e) => logger.warn("Received exception committing sub connection", e)
            }
          }
          conn.close()
          logger.info("Closed a sub connection")
        }
      } catch {
        case e: Exception => logger.warn("Received an exception closing sub connection", e)
      }

      closed = true
    }

    val _ = context.addTaskCompletionListener { context =>
      close()
    }

    val partition: ExasolRDDPartition = split.asInstanceOf[ExasolRDDPartition]
    val subHandle: Int = partition.handle

    logger.info(s"Sub connection with url = ${partition.connectionUrl} and handle = $subHandle")

    if (subHandle == -1) {
      logger.info("Sub connection handle is -1, no results, return empty iterator")
      return Iterator.empty
    }

    conn = manager.subConnection(partition.connectionUrl)
    resultSet = conn.DescribeResult(subHandle)

    Converter.resultSetToRows(resultSet, querySchema)
  }
  // scalastyle:on null return

}

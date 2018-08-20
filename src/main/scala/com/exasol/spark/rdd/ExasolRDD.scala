package com.exasol.spark.rdd

import scala.reflect.ClassTag
import scala.util.control.NonFatal

import org.apache.spark.Partition
import org.apache.spark.SparkContext
import org.apache.spark.TaskContext
import org.apache.spark.rdd.RDD
import org.apache.spark.scheduler.SparkListener
import org.apache.spark.scheduler.SparkListenerApplicationEnd

import com.exasol.jdbc.EXAConnection
import com.exasol.spark.util.ExasolConnectionManager
import com.exasol.spark.util.NextIterator

import com.typesafe.scalalogging.LazyLogging

/**
 * An [[org.apache.spark.rdd.RDD]] reads / writes data from an Exasol tables
 *
 * The [[com.exasol.spark.rdd.ExasolRDD]] holds data in parallel from each Exasol physical nodes.
 *
 */
class ExasolRDD[T: ClassTag](
  @transient val sc: SparkContext,
  queryString: String,
  manager: ExasolConnectionManager
) extends RDD[T](sc, Nil)
    with LazyLogging {

  @transient lazy val mainExaConnection: EXAConnection = {
    val conn = manager.mainConnection()

    manager.initParallel(conn)
    logger.info("Initiated parallel exasol (sub) connections")

    // Close Exasol main connection when SparkContext finishes. This is a lifetime of a Spark
    // application.
    sc.addSparkListener(new SparkListener {
      override def onApplicationEnd(appEnd: SparkListenerApplicationEnd): Unit = conn.close()
    })

    conn
  }

  override def getPartitions(): Array[Partition] = {
    val partitions = manager
      .subConnections(mainExaConnection)
      .zipWithIndex
      .map { case (url, idx) => ExasolRDDPartition(idx, url) }

    logger.debug(s"The number of partitions is ${partitions.size}")

    partitions.toArray
  }

  override def compute(split: Partition, context: TaskContext): Iterator[T] =
    new NextIterator[T] {
      context.addTaskCompletionListener(context => closeIfNeeded())

      val partition: ExasolRDDPartition = split.asInstanceOf[ExasolRDDPartition]

      val conn: EXAConnection = manager.subConnection(partition.connectionUrl)
      val stmt = conn.createStatement()
      val resultSet = stmt.executeQuery(queryString)

      override def getNext(): T =
        if (resultSet.next()) {
          null.asInstanceOf[T] // scalastyle:off null
        } else {
          finished = true
          null.asInstanceOf[T] // scalastyle:off null
        }

      override def close(): Unit = {
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
          }
          logger.info("Closed a sub connection")
        } catch {
          case e: Exception => logger.warn("Received an exception closing sub connection", e)
        }
      }
    }

}

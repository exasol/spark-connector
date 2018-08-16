package com.exasol.spark.rdd

import scala.reflect.ClassTag

import org.apache.spark.Partition
import org.apache.spark.SparkContext
import org.apache.spark.TaskContext
import org.apache.spark.rdd.RDD
import org.apache.spark.scheduler.SparkListener
import org.apache.spark.scheduler.SparkListenerApplicationEnd

import com.exasol.jdbc.EXAConnection
import com.exasol.spark.util.ExasolConnectionManager
import com.exasol.spark.util.NextIterator

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
) extends RDD[T](sc, Nil) {

  @transient lazy val mainExaConnection: EXAConnection = {
    val conn = manager.mainConnection()

    manager.initParallel(conn)

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

    partitions.toArray
  }

  override def compute(split: Partition, context: TaskContext): Iterator[T] =
    new NextIterator[T] {
      context.addTaskCompletionListener(context => closeIfNeeded())

      val partition: ExasolRDDPartition = split.asInstanceOf[ExasolRDDPartition]

      override def getNext(): T = {
        finished = true
        null.asInstanceOf[T] // scalastyle:off null
      }

      override def close(): Unit = {
        // close a connection
      }
    }

}

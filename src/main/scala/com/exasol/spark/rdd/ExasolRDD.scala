package com.exasol.spark.rdd

import scala.reflect.ClassTag

import org.apache.spark.Partition
import org.apache.spark.SparkContext
import org.apache.spark.TaskContext
import org.apache.spark.rdd.RDD

import com.exasol.spark.util.NextIterator

/**
 * An [[org.apache.spark.rdd.RDD]] reads / writes data from an Exasol tables.
 *
 * The [[com.exasol.spark.rdd.ExasolRDD]] holds data in parallel from each Exasol physical nodes.
 *
 */
class ExasolRDD[T: ClassTag](@transient val sc: SparkContext) extends RDD[T](sc, Nil) {

  override def getPartitions(): Array[Partition] =
    Array[Partition](ExasolRDDPartition(0))

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

  override def getPreferredLocations(split: Partition): Seq[String] =
    Nil

}

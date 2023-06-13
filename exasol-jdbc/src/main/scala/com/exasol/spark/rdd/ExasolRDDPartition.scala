package com.exasol.spark.rdd

import org.apache.spark.Partition

/**
 * A class that holds [[com.exasol.spark.rdd.ExasolRDD]] partition information.
 *
 * Each partition is mapped to a single Exasol node. The
 * [[com.exasol.spark.rdd.ExasolRDD]] materializes the data of a node based on
 * the information represented by a partition.
 *
 * @param idx A partition identifier (0 based)
 * @param handle A Exasol sub connection query identifier handle
 * @param connectionUrl An exasol jdbc (sub) connection string
 */
final case class ExasolRDDPartition(val idx: Int, val handle: Int, val connectionUrl: String) extends Partition {

  override def index: Int = idx

}

package com.exasol.spark.rdd

import org.apache.spark.Partition

/**
 * This serializable class represents a GeodeRDD partition. Each partition is mapped to one or
 * more buckets of region. The GeodeRDD can materialize the data of the partition based on all
 * information contained here.
 *
 * A class that holds [[com.exasol.spark.rdd.ExasolRDD]] partition information.
 *
 * Each partition is mapped to a single Exasol node. The [[com.exasol.spark.rdd.ExasolRDD]]
 * materializes the data of a node based on the information represented by a partition.
 *
 * @param partitionId A partition identifier (0 based).
 *
 */
case class ExasolRDDPartition(partitionId: Int) extends Partition {

  override def index(): Int = partitionId

}

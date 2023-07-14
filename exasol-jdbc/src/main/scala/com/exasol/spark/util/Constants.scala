package com.exasol.spark.util

/**
 * An object for providing contants.
 */
object Constants {

  /** Boolean {@code create_table} parameter for creating a table if it does not exist. */
  val CREATE_TABLE = "create_table"

  /** Boolean {@code drop_table} parameter for dropping a table if it exists. */
  val DROP_TABLE = "drop_table"

  /** Integer {@code batch_size} parameter for batching write queries. */
  val BATCH_SIZE = "batch_size"

  /** Default value for {@code batch_size} parameter. */
  val DEFAULT_BATCH_SIZE = 1000

  /** Integer {@code max_nodes} parameter for connecting to Exasol data nodes. */
  val MAX_NODES = "max_nodes"

  /** Default value for {@code max_nodes} parameter. */
  val DEFAULT_MAX_NODES = 200
}

package com.exasol.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources.BaseRelation
import org.apache.spark.sql.types.StructType

class ExasolRelation(context: SQLContext) extends BaseRelation with Serializable {
  override def sqlContext: SQLContext = context
  override def schema: StructType = StructType(Nil)
}

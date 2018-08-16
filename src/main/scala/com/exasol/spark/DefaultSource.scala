package com.exasol.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources.BaseRelation
import org.apache.spark.sql.sources.DataSourceRegister
import org.apache.spark.sql.sources.RelationProvider

class DefaultSource extends RelationProvider with DataSourceRegister {
  override def shortName(): String = "exasol"

  override def createRelation(
    sqlContext: SQLContext,
    parameters: Map[String, String]
  ): BaseRelation =
    new ExasolRelation(sqlContext)
}

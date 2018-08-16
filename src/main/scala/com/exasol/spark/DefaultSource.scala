package com.exasol.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources.BaseRelation
import org.apache.spark.sql.sources.DataSourceRegister
import org.apache.spark.sql.sources.RelationProvider

import com.exasol.spark.util.ExasolConfiguration
import com.exasol.spark.util.ExasolConnectionManager

class DefaultSource extends RelationProvider with DataSourceRegister {
  override def shortName(): String = "exasol"

  override def createRelation(
    sqlContext: SQLContext,
    parameters: Map[String, String]
  ): BaseRelation = {

    val queryString = parameters.get("query") match {
      case Some(sql) => sql
      case None =>
        throw new UnsupportedOperationException(
          "A sql query string should be specified when loading from Exasol"
        )
    }
    val config = ExasolConfiguration(parameters)
    val manager = ExasolConnectionManager(config)

    new ExasolRelation(sqlContext, queryString, manager)
  }
}

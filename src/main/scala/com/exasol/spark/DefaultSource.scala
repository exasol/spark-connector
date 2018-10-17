package com.exasol.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources.{
  BaseRelation,
  DataSourceRegister,
  RelationProvider,
  SchemaRelationProvider
}
import org.apache.spark.sql.types.StructType

import com.exasol.spark.util.{ExasolConfiguration, ExasolConnectionManager}

class DefaultSource extends RelationProvider with DataSourceRegister with SchemaRelationProvider {

  override def shortName(): String = "exasol"

  override def createRelation(
    sqlContext: SQLContext,
    parameters: Map[String, String]
  ): BaseRelation = {

    val (queryString, manager) = fromParameters(parameters)
    new ExasolRelation(sqlContext, queryString, None, manager)
  }

  override def createRelation(
    sqlContext: SQLContext,
    parameters: Map[String, String],
    schema: StructType
  ): BaseRelation = {

    val (queryString, manager) = fromParameters(parameters)
    new ExasolRelation(sqlContext, queryString, Option(schema), manager)
  }

  private def fromParameters(
    parameters: Map[String, String]
  ): (String, ExasolConnectionManager) = {
    val queryString = parameters.get("query") match {
      case Some(sql) => sql
      case None =>
        throw new UnsupportedOperationException(
          "A sql query string should be specified when loading from Exasol"
        )
    }
    val config = ExasolConfiguration(parameters)
    val manager = ExasolConnectionManager(config)
    (queryString, manager)
  }
}

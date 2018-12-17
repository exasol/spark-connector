package com.exasol.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources._
import org.apache.spark.sql.types.StructType

import com.exasol.spark.util.ExasolConfiguration
import com.exasol.spark.util.ExasolConnectionManager

/**
 * A data source for creating integration between Exasol and Spark
 *
 * It also serves as a factory class to create [[ExasolRelation]] instances for Spark application.
 */
class DefaultSource extends RelationProvider with DataSourceRegister with SchemaRelationProvider {

  override def shortName(): String = "exasol"

  /**
   * Creates an [[ExasolRelation]] using provided Spark [[org.apache.spark.sql.SQLContext]] and
   * parameters
   *
   * The schema is inferred by running the Exasol query with `LIMIT 1` clause.
   *
   * @param sqlContext A Spark [[org.apache.spark.sql.SQLContext]] context
   * @param parameters The parameters provided as options
   * @return A [[ExasolRelation]]
   */
  override def createRelation(
    sqlContext: SQLContext,
    parameters: Map[String, String]
  ): BaseRelation = {
    val (queryString, manager) = fromParameters(parameters, sqlContext)
    new ExasolRelation(sqlContext, queryString, None, manager)
  }

  /**
   * Creates an [[ExasolRelation]] using the provided Spark [[org.apache.spark.sql.SQLContext]],
   * parameters and schema
   *
   * @param sqlContext A Spark [[org.apache.spark.sql.SQLContext]] context
   * @param parameters The parameters provided as options
   * @param schema A user provided schema used to select columns for the relation
   * @return A [[ExasolRelation]]
   */
  override def createRelation(
    sqlContext: SQLContext,
    parameters: Map[String, String],
    schema: StructType
  ): BaseRelation = {
    val (queryString, manager) = fromParameters(parameters, sqlContext)
    new ExasolRelation(sqlContext, queryString, Option(schema), manager)
  }

  private[spark] def mergeConfiguration(
    parameters: Map[String, String],
    sparkConf: Map[String, String]
  ): Map[String, String] =
    parameters ++ sparkConf
      .filter { case (key, _) => key.startsWith(s"spark.exasol.") }
      .map { case (key, value) => key.substring(s"spark.exasol.".length) -> value }

  private[this] def fromParameters(
    parameters: Map[String, String],
    sqlContext: SQLContext
  ): (String, ExasolConnectionManager) = {
    val queryString = parameters.get("query") match {
      case Some(sql) => sql
      case None =>
        throw new UnsupportedOperationException(
          "A sql query string should be specified when loading from Exasol"
        )
    }
    val config = ExasolConfiguration(mergeConfiguration(parameters, sqlContext.getAllConfs))
    val manager = ExasolConnectionManager(config)
    (queryString, manager)
  }

}

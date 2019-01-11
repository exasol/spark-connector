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
   * @param parameters The parameters provided as options, `query` parameter is required for read
   * @return An [[ExasolRelation]] relation
   */
  override def createRelation(
    sqlContext: SQLContext,
    parameters: Map[String, String]
  ): BaseRelation = {
    val queryString = getKValue("query", parameters)
    val manager = createManager(parameters, sqlContext)
    new ExasolRelation(sqlContext, queryString, None, manager)
  }

  /**
   * Creates an [[ExasolRelation]] using the provided Spark [[org.apache.spark.sql.SQLContext]],
   * parameters and schema
   *
   * @param sqlContext A Spark [[org.apache.spark.sql.SQLContext]] context
   * @param parameters The parameters provided as options, `query` parameter is required for read
   * @param schema A user provided schema used to select columns for the relation
   * @return An [[ExasolRelation]] relation
   */
  override def createRelation(
    sqlContext: SQLContext,
    parameters: Map[String, String],
    schema: StructType
  ): BaseRelation = {
    val queryString = getKValue("query", parameters)
    val manager = createManager(parameters, sqlContext)
    new ExasolRelation(sqlContext, queryString, Option(schema), manager)
  }

  private[this] def getKValue(key: String, parameters: Map[String, String]): String =
    parameters.get(key) match {
      case Some(str) => str
      case None =>
        throw new UnsupportedOperationException(
          s"A $key parameter should be specified in order to run the operation"
        )
    }

  // Creates an ExasolConnectionManager with merged configuration values
  private[this] def createManager(
    parameters: Map[String, String],
    sqlContext: SQLContext
  ): ExasolConnectionManager = {
    val config = ExasolConfiguration(mergeConfigurations(parameters, sqlContext.getAllConfs))
    ExasolConnectionManager(config)
  }

  // Merges user provided parameters with `spark.exasol.*` runtime configurations. If both of them
  // define a key=value pair, then the one provided at runtime is used.
  private[spark] def mergeConfigurations(
    parameters: Map[String, String],
    sparkConf: Map[String, String]
  ): Map[String, String] =
    parameters ++ sparkConf
      .filter { case (key, _) => key.startsWith(s"spark.exasol.") }
      .map { case (key, value) => key.substring(s"spark.exasol.".length) -> value }

}

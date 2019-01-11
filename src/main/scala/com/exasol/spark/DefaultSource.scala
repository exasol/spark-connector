package com.exasol.spark

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Row
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources._
import org.apache.spark.sql.types.StructType

import com.exasol.spark.util.ExasolConfiguration
import com.exasol.spark.util.ExasolConnectionManager
import com.exasol.spark.writer.ExasolWriter

/**
 * A data source for creating integration between Exasol and Spark
 *
 * It also serves as a factory class to create [[ExasolRelation]] instances for Spark application.
 */
class DefaultSource
    extends RelationProvider
    with DataSourceRegister
    with SchemaRelationProvider
    with CreatableRelationProvider {

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

  /**
   * Creates an [[ExasolRelation]] after saving a Spark dataframe into Exasol table
   *
   * @param sqlContext A Spark [[org.apache.spark.sql.SQLContext]] context
   * @param mode One of Spark save modes, [[org.apache.spark.sql.SaveMode]]
   * @param parameters The parameters provided as options, `table` parameter is required for write
   * @param data A Spark [[org.apache.spark.sql.DataFrame]] to save as a Exasol table
   * @return An [[ExasolRelation]] relation
   */
  override def createRelation(
    sqlContext: SQLContext,
    mode: SaveMode,
    parameters: Map[String, String],
    data: DataFrame
  ): BaseRelation = {
    val tableName = getKValue("table", parameters)
    val manager = createManager(parameters, sqlContext)

    val isTableExist = manager.tableExists(tableName)

    mode match {
      case SaveMode.Overwrite =>
        if (!isTableExist) {
          // maybe createTable?
          throw new RuntimeException(
            s"Table $tableName does not exist when saving with 'overwrite' mode"
          )
        }
        manager.truncateTable(tableName)
        saveDFTable(sqlContext, data, tableName, manager)

      case SaveMode.Append =>
        if (!isTableExist) {
          // maybe createTable?
          throw new RuntimeException(
            s"Table $tableName does not exist when saving with 'append' mode"
          )
        }
        saveDFTable(sqlContext, data, tableName, manager)

      case SaveMode.ErrorIfExists =>
        if (isTableExist) {
          throw new RuntimeException(
            s"Table $tableName already exists." +
              " Use one of other SaveMode modes: 'append', 'overwrite' or 'ignore'"
          )
        }
      // createTable
      // saveDFTable

      case SaveMode.Ignore =>
        if (!isTableExist) {
          // createTable
          // saveDFTable
        }
    }

    val newParams = parameters ++ Map("query" -> s"SELECT * FROM $tableName")
    createRelation(sqlContext, newParams, data.schema)
  }

  def saveDFTable(
    sqlContext: SQLContext,
    df: DataFrame,
    tableName: String,
    manager: ExasolConnectionManager
  ): Unit = {
    val writer = new ExasolWriter(sqlContext.sparkContext, tableName, df.schema, manager)
    val exaNodes = writer.startParallel()
    val newDF = repartitionPerNode(df, exaNodes)

    newDF.rdd.foreachPartition(iter => writer.insertPartition(iter))
  }

  def repartitionPerNode(df: DataFrame, nodesCnt: Int): DataFrame =
    if (nodesCnt < df.rdd.getNumPartitions) {
      df.coalesce(nodesCnt)
    } else {
      df
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

package com.exasol.spark

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources._
import org.apache.spark.sql.types.StructType

import com.exasol.errorreporting.ExaError
import com.exasol.spark.util.ExasolConfiguration
import com.exasol.spark.util.ExasolConnectionManager
import com.exasol.spark.util.Types
import com.exasol.spark.writer.ExasolWriter
import com.exasol.sql.StatementFactory
import com.exasol.sql.dql.select.rendering.SelectRenderer
import com.exasol.sql.rendering.StringRendererConfig

/**
 * The default entry source for creating integration between Exasol and Spark.
 *
 * Additionally, it serves as a factory class to create [[ExasolRelation]]
 * instances for Spark application.
 */
class DefaultSource
    extends RelationProvider
    with DataSourceRegister
    with SchemaRelationProvider
    with CreatableRelationProvider {

  override def shortName(): String = "exasol"

  /**
   * Creates an [[ExasolRelation]] using provided Spark
   * [[org.apache.spark.sql.SQLContext]] and parameters.
   *
   * Since the '''schema''' is not provided, it is inferred by running an Exasol
   * query with `LIMIT 1` clause.
   *
   * @param sqlContext A Spark [[org.apache.spark.sql.SQLContext]] context
   * @param parameters The parameters provided as options, `query` parameter is
   *        required for read
   * @return An [[ExasolRelation]] relation
   */
  override def createRelation(
    sqlContext: SQLContext,
    parameters: Map[String, String]
  ): BaseRelation = {
    val queryString = getKeyValue("query", parameters)
    val manager = createManager(parameters, sqlContext)
    new ExasolRelation(sqlContext, queryString, None, manager)
  }

  /**
   * Creates an [[ExasolRelation]] using the provided Spark
   * [[org.apache.spark.sql.SQLContext]], parameters and schema.
   *
   * @param sqlContext A Spark [[org.apache.spark.sql.SQLContext]] context
   * @param parameters The parameters provided as options, `query` parameter is
   *        required for read
   * @param schema A user provided schema used to select columns for the
   *        relation
   * @return An [[ExasolRelation]] relation
   */
  override def createRelation(
    sqlContext: SQLContext,
    parameters: Map[String, String],
    schema: StructType
  ): BaseRelation = {
    val queryString = getKeyValue("query", parameters)
    val manager = createManager(parameters, sqlContext)
    new ExasolRelation(sqlContext, queryString, Option(schema), manager)
  }

  /**
   * Creates an [[ExasolRelation]] after saving a
   * [[org.apache.spark.sql.DataFrame]] into Exasol table.
   *
   * @param sqlContext A Spark [[org.apache.spark.sql.SQLContext]] context
   * @param mode One of Spark save modes, [[org.apache.spark.sql.SaveMode]]
   * @param parameters The parameters provided as options, `table` parameter is
   *        required for write
   * @param data A Spark [[org.apache.spark.sql.DataFrame]] to save as a Exasol
   *        table
   * @return An [[ExasolRelation]] relation
   */
  override def createRelation(
    sqlContext: SQLContext,
    mode: SaveMode,
    parameters: Map[String, String],
    data: DataFrame
  ): BaseRelation = {
    val tableName = getKeyValue("table", parameters)
    val manager = createManager(parameters, sqlContext)
    if (manager.config.drop_table) {
      manager.dropTable(tableName)
    }
    val isTableExist = manager.tableExists(tableName)

    mode match {
      case SaveMode.Overwrite =>
        if (!isTableExist) {
          createExasolTable(data, tableName, manager)
        }
        manager.truncateTable(tableName)
        saveDataFrame(sqlContext, data, tableName, manager)

      case SaveMode.Append =>
        if (!isTableExist) {
          createExasolTable(data, tableName, manager)
        }
        saveDataFrame(sqlContext, data, tableName, manager)

      case SaveMode.ErrorIfExists =>
        if (isTableExist) {
          throw new UnsupportedOperationException(
            ExaError
              .messageBuilder("E-SEC-3")
              .message(
                "Table {{TABLE}} already exists in 'errorifexists' or 'default' write modes."
              )
              .parameter("TABLE", tableName)
              .mitigation(
                "Please use one of the following write modes: 'append', 'overwrite', 'ignore'."
              )
              .toString()
          )
        }
        createExasolTable(data, tableName, manager)
        saveDataFrame(sqlContext, data, tableName, manager)

      case SaveMode.Ignore =>
        if (!isTableExist) {
          createExasolTable(data, tableName, manager)
          saveDataFrame(sqlContext, data, tableName, manager)
        }
    }

    val newParams = parameters ++ Map("query" -> getSelectFromTableQuery(tableName))
    createRelation(sqlContext, newParams, data.schema)
  }

  private[this] def getSelectFromTableQuery(tableName: String): String = {
    val select = StatementFactory.getInstance().select().all().from().table(tableName)
    val rendererConfig = StringRendererConfig.builder().quoteIdentifiers(true).build()
    val renderer = new SelectRenderer(rendererConfig)
    select.accept(renderer)
    renderer.render()
  }

  // Saves Spark dataframe into an Exasol table
  private[this] def saveDataFrame(
    sqlContext: SQLContext,
    df: DataFrame,
    tableName: String,
    manager: ExasolConnectionManager
  ): Unit = {
    val writer = new ExasolWriter(sqlContext.sparkContext, tableName, df.schema, manager)
    val exaNodesCnt = writer.startParallel()
    val newDF = repartitionPerNode(df, exaNodesCnt)

    newDF.rdd.foreachPartition(iter => writer.insertPartition(iter))
  }

  // Creates an Exasol table that match Spark dataframe
  private[this] def createExasolTable(
    df: DataFrame,
    tableName: String,
    manager: ExasolConnectionManager
  ): Unit =
    if (manager.config.create_table || manager.config.drop_table) {
      manager.createTable(tableName, Types.createTableSchema(df.schema))
    } else {
      throw new UnsupportedOperationException(
        ExaError
          .messageBuilder("E-SEC-2")
          .message("Table {{TABLE}} does not exist.", tableName)
          .mitigation(
            "Please create table before hand or enable table creation by setting 'create_table' option."
          )
          .toString()
      )
    }

  /**
   * Rearrange dataframe partitions into Exasol data nodes count.
   *
   * If `nodesCnt` < `df.rdd.getNumPartitions` then perform
   *
   * {{{
   *   df.coalesce(nodesCnt)
   * }}}
   *
   * in order to reduce the partition counts.
   *
   * If `nodesCnt` > `df.rdd.getNumPartitions` then perform
   *
   * {{{
   *   df.repartition(nodesCnt)
   * }}}
   *
   * so that there a partition for each data node.
   *
   * If the number of partitions and nodes are same, then do nothing.
   */
  def repartitionPerNode(df: DataFrame, nodesCnt: Int): DataFrame = {
    val rddPartitionCnt = df.rdd.getNumPartitions
    if (nodesCnt < rddPartitionCnt) {
      df.coalesce(nodesCnt)
    } else if (nodesCnt > rddPartitionCnt) {
      df.repartition(nodesCnt)
    } else {
      df
    }
  }

  private[this] def getKeyValue(key: String, parameters: Map[String, String]): String =
    parameters.get(key) match {
      case Some(str) => str
      case None =>
        throw new UnsupportedOperationException(
          ExaError
            .messageBuilder("E-SEC-1")
            .message("Parameter {{PARAMETER}} is missing.", key)
            .mitigation("Please provide required parameter.")
            .toString()
        )
    }

  // Creates an ExasolConnectionManager with merged configuration values.
  private[this] def createManager(
    parameters: Map[String, String],
    sqlContext: SQLContext
  ): ExasolConnectionManager = {
    val config = ExasolConfiguration(mergeConfigurations(parameters, sqlContext.getAllConfs))
    ExasolConnectionManager(config)
  }

  // Merges user provided parameters with `spark.exasol.*` runtime
  // configurations. If both of them define a key=value pair, then the one
  // provided at runtime is used.
  private[spark] def mergeConfigurations(
    parameters: Map[String, String],
    sparkConf: Map[String, String]
  ): Map[String, String] =
    parameters ++ sparkConf
      .filter { case (key, _) => key.startsWith(s"spark.exasol.") }
      .map { case (key, value) => key.substring(s"spark.exasol.".length) -> value }

}

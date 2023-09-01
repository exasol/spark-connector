package com.exasol.spark

import org.apache.spark.sql._
import org.apache.spark.sql.sources._
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.util.CaseInsensitiveStringMap
import com.exasol.errorreporting.ExaError
import com.exasol.spark.common.ExasolOptions
import com.exasol.spark.util.Constants._
import com.exasol.spark.util.ExasolConnectionManager
import com.exasol.spark.util.ExasolOptionsProvider
import com.exasol.spark.util.Types
import com.exasol.spark.writer.ExasolWriter
import com.exasol.sql.StatementFactory
import com.exasol.sql.dql.select.rendering.SelectRenderer
import com.exasol.sql.rendering.StringRendererConfig
import org.apache.spark.internal.Logging

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
    with CreatableRelationProvider
    with Logging {

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
  override def createRelation(sqlContext: SQLContext, parameters: Map[String, String]): BaseRelation = {
    val options = createOptions(parameters, sqlContext)
    val manager = ExasolConnectionManager(options)
    new ExasolRelation(sqlContext, options.getQuery(), None, manager)
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
    val options = createOptions(parameters, sqlContext)
    val manager = ExasolConnectionManager(options)
    new ExasolRelation(sqlContext, options.getQuery(), Option(schema), manager)
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
    val options = createOptions(parameters, sqlContext)
    val tableName = options.getTable()
    val manager = ExasolConnectionManager(options)
    if (options.hasEnabled(DROP_TABLE)) {
      manager.dropTable(tableName)
    }
    val isTableExist = manager.tableExists(tableName)

    mode match {
      case SaveMode.Overwrite =>
        if (!isTableExist) {
          createExasolTable(data, tableName, options, manager)
        }
        manager.truncateTable(tableName)
        saveDataFrame(sqlContext, data, tableName, options, manager)

      case SaveMode.Append =>
        if (!isTableExist) {
          createExasolTable(data, tableName, options, manager)
        }
        saveDataFrame(sqlContext, data, tableName, options, manager)

      case SaveMode.ErrorIfExists =>
        if (isTableExist) {
          throw new UnsupportedOperationException(
            ExaError
              .messageBuilder("E-SEC-3")
              .message(
                "Table {{TABLE}} already exists in 'errorifexists' or 'default' write modes.",
                tableName
              )
              .mitigation(
                "Please use one of the following write modes: 'append', 'overwrite', 'ignore'."
              )
              .toString()
          )
        }
        createExasolTable(data, tableName, options, manager)
        saveDataFrame(sqlContext, data, tableName, options, manager)

      case SaveMode.Ignore =>
        if (!isTableExist) {
          createExasolTable(data, tableName, options, manager)
          saveDataFrame(sqlContext, data, tableName, options, manager)
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
    options: ExasolOptions,
    manager: ExasolConnectionManager
  ): Unit = {
    val maxParallel = sqlContext.sparkContext.defaultParallelism
    logInfo(s"saveDataFrame, maxParallellelism=$maxParallel")
    val mainConnection = manager.writerMainConnection()

    if (mainConnection == null) {
      throw new RuntimeException(
        ExaError
          .messageBuilder("F-SEC-7")
          .message("Could not create main JDBC connection to Exasol cluster.")
          .mitigation("Please make sure that there is a network connection between Spark and Exasol clusters.")
          .toString()
      )
    }

    try {
      val exaNodesCnt = manager.initParallel(mainConnection, maxParallel)
      val hosts = manager.subConnections(mainConnection)
      val newDF = repartitionPerNode(df, exaNodesCnt)
      val writer = new ExasolWriter(sqlContext.sparkContext, tableName, df.schema, options, hosts, manager)

      logInfo(s"save with nodes=$exaNodesCnt")
      newDF.rdd.foreachPartition(iter => writer.insertPartition(iter))
      mainConnection.commit()
    } catch {
      case ex: Exception => {
        logError("Exception during writing, roll back transaction", ex)
        mainConnection.rollback()
      }
    } finally {
      mainConnection.close()
    }
  }

  // Creates an Exasol table that match Spark dataframe
  private[this] def createExasolTable(
    df: DataFrame,
    tableName: String,
    options: ExasolOptions,
    manager: ExasolConnectionManager
  ): Unit =
    if (options.hasEnabled(CREATE_TABLE) || options.hasEnabled(DROP_TABLE)) {
      manager.createTable(tableName, Types.createTableSchema(df.schema))
    } else {
      throw new UnsupportedOperationException(
        ExaError
          .messageBuilder("E-SEC-2")
          .message("Table {{TABLE}} does not exist.", tableName)
          .mitigation(
            "Please create table beforehand or enable table creation by setting 'create_table' option."
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

  private[this] def createOptions(parameters: Map[String, String], sqlContext: SQLContext): ExasolOptions = {
    val hashMap = new java.util.HashMap[String, String]()
    mergeConfigurations(parameters, sqlContext.getAllConfs).foreach { case (key, value) =>
      hashMap.put(key, value)
    }
    ExasolOptionsProvider(new CaseInsensitiveStringMap(hashMap))
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

package com.exasol.spark

import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.execution.datasources.jdbc.JdbcUtils
import org.apache.spark.sql.sources.BaseRelation
import org.apache.spark.sql.sources.Filter
import org.apache.spark.sql.sources.PrunedFilteredScan
import org.apache.spark.sql.sources.PrunedScan
import org.apache.spark.sql.sources.TableScan
import org.apache.spark.sql.types.StructType

import com.exasol.spark.rdd.ExasolRDD
import com.exasol.spark.util.ExasolConnectionManager
import com.exasol.spark.util.Filters
import com.exasol.spark.util.Types

/**
 * The Exasol specific implementation of Spark
 * [[org.apache.spark.sql.sources.BaseRelation]].
 *
 * @param context A Spark [[org.apache.spark.sql.SQLContext]]
 * @param queryString A user provided Exasol SQL query string
 * @param configSchema An optional user provided '''schema''
 * @param manager An Exasol connection manager
 */
class ExasolRelation(
  context: SQLContext,
  queryString: String,
  configSchema: Option[StructType],
  manager: ExasolConnectionManager
) extends BaseRelation
    with PrunedFilteredScan
    with PrunedScan
    with TableScan
    with Logging {

  override def sqlContext: SQLContext = context

  private[this] lazy val inferSchema: StructType = {
    val queryStringLimit = s"SELECT * FROM ($queryString) A LIMIT 1"
    manager.withConnection[StructType] { conn =>
      val stmt = conn.createStatement()
      val resultSet = stmt.executeQuery(queryStringLimit)
      val metadata = resultSet.getMetaData
      val sparkStruct = Types.createSparkStructType(metadata)
      resultSet.close()
      stmt.close()
      sparkStruct
    }
  }

  override def schema: StructType = configSchema.fold(inferSchema) { userSchema =>
    logInfo(s"Using provided schema $userSchema")
    userSchema
  }

  override def buildScan(): RDD[Row] =
    buildScan(Array.empty, Array.empty)

  override def buildScan(requiredColumns: Array[String]): RDD[Row] =
    buildScan(requiredColumns, Array.empty)

  override def buildScan(requiredColumns: Array[String], filters: Array[Filter]): RDD[Row] =
    if (requiredColumns.isEmpty) {
      makeEmptyRDD(filters)
    } else if (manager.config.isSingleNode()) {
      makeSingleRDD(requiredColumns, filters)
    } else {
      new ExasolRDD(
        sqlContext.sparkContext,
        enrichQuery(requiredColumns, filters),
        Types.selectColumns(requiredColumns, schema),
        manager
      )
    }

  override def unhandledFilters(filters: Array[Filter]): Array[Filter] = {
    val dataTypes = schema.map(field => field.name -> field.dataType).toMap
    filters.filterNot(Filters.filterExpr(_, dataTypes).isDefined)
  }

  /**
   * When a count action is run from Spark dataframe we do not have to read the
   * actual data and perform all serializations through the network. Instead we
   * can create a RDD with empty Row-s with expected number of rows from actual
   * query.
   *
   * This also called count pushdown.
   *
   * @param filters A list of [[org.apache.spark.sql.sources.Filter]]-s that can
   *        be pushed as where clause
   * @return An RDD of empty Row-s which has as many elements as count(*) from
   *         enriched query
   */
  private[this] def makeEmptyRDD(filters: Array[Filter]): RDD[Row] = {
    val cntQuery = enrichQuery(Array.empty[String], filters)
    val cnt = manager.withCountQuery(cntQuery)
    sqlContext.sparkContext.parallelize(1L to cnt, 4).map(_ => Row.empty)
  }

  private[this] def makeSingleRDD(columns: Array[String], filters: Array[Filter]): RDD[Row] = {
    val query = enrichQuery(columns, filters)
    val rows = manager.withExecuteQuery(query)(
      JdbcUtils.resultSetToRows(_, Types.selectColumns(columns, schema))
    )
    sqlContext.sparkContext.parallelize(rows.toSeq, 4)
  }

  /**
   * Improves the original query with column pushdown and predicate pushdown.
   *
   * It will use provided column names to create a sub select query and
   * similarly add where clause if filters are provided.
   *
   * Additionally, if no column names are provided it creates a `COUNT(*)`
   * query.
   *
   * @param columns A list of column names
   * @param filters A list of Spark [[org.apache.spark.sql.sources.Filter]]-s
   * @return An enriched query with column selection and where clauses
   */
  private[this] def enrichQuery(columns: Array[String], filters: Array[Filter]): String = {
    val columnStr = if (columns.isEmpty) "COUNT(*)" else columns.map(c => s"A.$c").mkString(", ")
    val filterStr = Filters.createWhereClause(schema, filters)
    val whereClause = if (filterStr.trim.isEmpty) "" else s"WHERE $filterStr"
    val enrichedQuery = s"SELECT $columnStr FROM ($queryString) A $whereClause"
    logInfo(s"Running with enriched query: $enrichedQuery")
    enrichedQuery
  }

}

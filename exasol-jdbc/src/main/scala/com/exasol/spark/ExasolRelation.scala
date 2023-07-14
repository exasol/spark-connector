package com.exasol.spark

import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources._
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
    } else {
      new ExasolRDD(
        sqlContext.sparkContext,
        getEnrichedQuery(requiredColumns, filters),
        Types.selectColumns(requiredColumns, schema),
        manager
      )
    }

  override def unhandledFilters(filters: Array[Filter]): Array[Filter] =
    filters.filterNot(Filters.filterToBooleanExpression(_).isDefined)

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
    val cntQuery = getEnrichedQuery(Array.empty[String], filters)
    val cnt = manager.withCountQuery(cntQuery)
    sqlContext.sparkContext.parallelize(1L to cnt, 4).map(_ => Row.empty)
  }

  private[this] def getEnrichedQuery(columns: Array[String], filters: Array[Filter]): String =
    ExasolQueryEnricher(queryString).enrichQuery(columns, filters)

}

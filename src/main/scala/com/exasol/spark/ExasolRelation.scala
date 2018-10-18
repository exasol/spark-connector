package com.exasol.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.apache.spark.sql.SQLContext
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

import com.typesafe.scalalogging.LazyLogging

class ExasolRelation(
  context: SQLContext,
  queryString: String,
  configSchema: Option[StructType],
  manager: ExasolConnectionManager
) extends BaseRelation
    with PrunedFilteredScan
    with PrunedScan
    with TableScan
    with LazyLogging {

  override def sqlContext: SQLContext = context

  private[this] lazy val inferSchema: StructType = {
    val queryStringLimit = s"SELECT * FROM ($queryString) A LIMIT 1"
    manager.withConnection[StructType] { conn =>
      val stmt = conn.createStatement()
      val resultSet = stmt.executeQuery(queryStringLimit)
      val metadata = resultSet.getMetaData
      Types.createSparkStructType(metadata)
    }
  }

  override def schema: StructType = configSchema.fold(inferSchema) { userSchema =>
    logger.info(s"Using provided schema $userSchema")
    userSchema
  }

  override def buildScan(): RDD[Row] =
    buildScan(Array.empty, Array.empty)

  override def buildScan(requiredColumns: Array[String]): RDD[Row] =
    buildScan(requiredColumns, Array.empty)

  override def buildScan(requiredColumns: Array[String], filters: Array[Filter]): RDD[Row] =
    new ExasolRDD(
      sqlContext.sparkContext,
      enrichQuery(requiredColumns, filters),
      Types.selectColumns(requiredColumns, schema),
      manager
    )

  private[this] def enrichQuery(columns: Array[String], filters: Array[Filter]): String = {
    val columnStr = if (columns.isEmpty) "*" else columns.map(c => s"A.$c").mkString(", ")
    val filterStr = Filters.createWhereClause(schema, filters)
    val whereClause = if (filterStr.trim.isEmpty) "" else s"WHERE $filterStr"
    val enrichedQuery = s"SELECT $columnStr FROM ($queryString) A $whereClause"
    logger.info(s"Running with enriched query: $enrichedQuery")
    enrichedQuery
  }

}

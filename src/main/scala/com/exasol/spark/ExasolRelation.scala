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
import com.exasol.spark.util.Types

class ExasolRelation(context: SQLContext, queryString: String, manager: ExasolConnectionManager)
    extends BaseRelation
    with PrunedFilteredScan
    with PrunedScan
    with TableScan {

  override def sqlContext: SQLContext = context

  private[this] lazy val querySchema: StructType = {
    val queryStringLimit = s"SELECT * FROM ($queryString) A LIMIT 1"
    manager.withConnection[StructType] { conn =>
      val stmt = conn.createStatement()
      val resultSet = stmt.executeQuery(queryStringLimit)
      val metadata = resultSet.getMetaData
      Types.createSparkStructType(metadata)
    }
  }

  override def schema: StructType = querySchema

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
    val columnStr = if (columns.isEmpty) "*" else columns.mkString(", ")
    s"SELECT $columnStr FROM ($queryString)"
  }

}

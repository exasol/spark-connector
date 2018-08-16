package com.exasol.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources.BaseRelation
import org.apache.spark.sql.sources.Filter
import org.apache.spark.sql.sources.PrunedFilteredScan
import org.apache.spark.sql.types.StructType

import com.exasol.spark.rdd.ExasolRDD
import com.exasol.spark.util.ExasolConnectionManager

class ExasolRelation(context: SQLContext, queryString: String, manager: ExasolConnectionManager)
    extends BaseRelation
    with PrunedFilteredScan {

  override def sqlContext: SQLContext = context

  override def schema: StructType = StructType(Nil)

  def buildScan(): RDD[Row] =
    new ExasolRDD(sqlContext.sparkContext, queryString, manager)

  def buildScan(requiredColumns: Array[String]): RDD[Row] =
    buildScan(requiredColumns, Array.empty)

  def buildScan(requiredColumns: Array[String], filters: Array[Filter]): RDD[Row] =
    new ExasolRDD(sqlContext.sparkContext, queryString, manager)

}

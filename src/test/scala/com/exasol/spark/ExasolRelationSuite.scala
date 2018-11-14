package com.exasol.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types.StructType

import com.exasol.spark.util.ExasolConnectionManager

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.Matchers
import org.scalatest.mockito.MockitoSugar

class ExasolRelationSuite
    extends FunSuite
    with Matchers
    with MockitoSugar
    with DataFrameSuiteBase {

  test("buildScan returns RDD of empty Row-s when requiredColumns is empty (count pushdown)") {
    val query = "SELECT 1"
    val cntQuery = "SELECT COUNT(*) FROM (SELECT 1) A "
    val cnt = 5L

    val manager = mock[ExasolConnectionManager]
    when(manager.withCountQuery(cntQuery)).thenReturn(cnt)

    val relation = new ExasolRelation(spark.sqlContext, query, Option(new StructType), manager)
    val rdd = relation.buildScan()

    assert(rdd.isInstanceOf[RDD[Row]])
    assert(rdd.partitions.size === 4)
    assert(rdd.count === cnt)
    verify(manager, times(1)).withCountQuery(cntQuery)
  }

}

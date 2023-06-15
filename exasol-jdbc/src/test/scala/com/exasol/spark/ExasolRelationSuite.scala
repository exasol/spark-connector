package com.exasol.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources._

import com.exasol.spark.util.ExasolConnectionManager

import org.mockito.Mockito._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

class ExasolRelationSuite extends AnyFunSuite with Matchers with MockitoSugar {

  test("unhandledFilters keeps non-pushed filters") {
    val filters = Array[Filter](
      LessThanOrEqual("c", "3"),
      EqualTo("b", "abc"),
      Not(EqualTo("a", false))
    )
    val nullFilters = Array(EqualNullSafe("b", "xyz"))
    val relation = new ExasolRelation(null, "", None, null)
    assert(relation.unhandledFilters(filters) === Array.empty[Filter])
    assert(relation.unhandledFilters(filters ++ nullFilters) === nullFilters)
  }

  test("buildScan returns empty RDD with empty columns (count pushdown)") {
    val userQuery = "SELECT FROM DUAL"
    val countQuery = s"""SELECT COUNT('*') FROM ($userQuery) A"""
    val expectedCount = 5L
    val manager = mock[ExasolConnectionManager]
    when(manager.withCountQuery(countQuery)).thenReturn(expectedCount)
    val conf = new SparkConf().setMaster("local[1]").setAppName("test-relation")
    val sparkContext = new SparkContext(conf)
    val sqlContext = mock[SQLContext]
    when(sqlContext.sparkContext).thenReturn(sparkContext)
    val rdd = new ExasolRelation(sqlContext, userQuery, None, manager).buildScan()
    assert(rdd.isInstanceOf[RDD[Row]])
    assert(rdd.partitions.size === 4)
    assert(rdd.count() === expectedCount)
    verify(manager, times(1)).withCountQuery(countQuery)
  }

}

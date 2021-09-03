package com.exasol.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.apache.spark.sql.sources._

import com.exasol.spark.util.ExasolConnectionManager

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.mockito.Mockito._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

class ExasolRelationSuite extends AnyFunSuite with Matchers with MockitoSugar with DataFrameSuiteBase {

  test("unhandledFilters keeps non-pushed filters") {
    val filters = Array[Filter](
      LessThanOrEqual("c", "3"),
      EqualTo("b", "abc"),
      Not(EqualTo("a", false))
    )
    val nullFilters = Array(EqualNullSafe("b", "xyz"))
    val relation = new ExasolRelation(spark.sqlContext, "", None, null)
    assert(relation.unhandledFilters(filters) === Array.empty[Filter])
    assert(relation.unhandledFilters(filters ++ nullFilters) === nullFilters)
  }

  test("buildScan returns empty RDD with empty columns (count pushdown)") {
    val userQuery = "SELECT FROM DUAL"
    val countQuery = s"""SELECT COUNT('*') FROM ($userQuery) A"""
    val expectedCount = 5L
    val manager = mock[ExasolConnectionManager]
    when(manager.withCountQuery(countQuery)).thenReturn(expectedCount)
    val rdd = new ExasolRelation(spark.sqlContext, userQuery, None, manager).buildScan()
    assert(rdd.isInstanceOf[RDD[Row]])
    assert(rdd.partitions.size === 4)
    assert(rdd.count === expectedCount)
    verify(manager, times(1)).withCountQuery(countQuery)
  }

}

package com.exasol.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.apache.spark.sql.sources._
import org.apache.spark.sql.types._

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

  test("unhandledFilters should keep non-pushed filters") {
    val schema: StructType = new StructType()
      .add("a", BooleanType)
      .add("b", StringType)
      .add("c", IntegerType)

    val filters = Array[Filter](
      LessThanOrEqual("c", "3"),
      EqualTo("b", "abc"),
      Not(EqualTo("a", false))
    )

    val nullFilters = Array(EqualNullSafe("b", "xyz"))

    val rel = new ExasolRelation(spark.sqlContext, "", Option(schema), null)

    assert(rel.unhandledFilters(filters) === Array.empty[Filter])
    assert(rel.unhandledFilters(filters ++ nullFilters) === nullFilters)
  }

}

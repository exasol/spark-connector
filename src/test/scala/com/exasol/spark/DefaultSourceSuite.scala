package com.exasol.spark

import org.apache.spark.sql.SQLContext

import org.scalatest.{FunSuite, Matchers}
import org.scalatest.mockito.MockitoSugar

class DefaultSourceSuite extends FunSuite with Matchers with MockitoSugar {
  test("fromParameters should throw Exception when no query is provided") {
    val sqlContext = mock[SQLContext]

    val thrown = intercept[UnsupportedOperationException] {
      new DefaultSource().createRelation(sqlContext, Map[String, String]())
    }

    assert(
      thrown.getMessage === "A sql query string should be specified when loading from Exasol"
    )
  }
}

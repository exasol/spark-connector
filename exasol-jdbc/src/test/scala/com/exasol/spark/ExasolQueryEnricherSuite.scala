package com.exasol.spark

import org.apache.spark.sql.sources._

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ExasolQueryEnricherSuite extends AnyFunSuite with Matchers {

  private[this] val userQuery = "SELECT 1"
  private[this] val queryEnricher = ExasolQueryEnricher(userQuery)

  test("enriches empty filters") {
    val query = queryEnricher.enrichQuery(Array("a", "b"), Array.empty[Filter])
    assert(query === s"""SELECT "a", "b" FROM ($userQuery) A""")
  }

  test("enriches empty columns") {
    val query = queryEnricher.enrichQuery(Array.empty[String], Array.empty[Filter])
    assert(query === s"""SELECT COUNT('*') FROM ($userQuery) A""")
  }

  test("enriches empty columns with a filter") {
    val query = queryEnricher.enrichQuery(Array.empty[String], Array(Not(EqualTo("c", 3))))
    assert(query === s"""SELECT COUNT('*') FROM ($userQuery) A WHERE ("c" <> 3)""")
  }

  test("enriches a single filter") {
    val query = queryEnricher.enrichQuery(Array("a", "b"), Array(EqualTo("a", 1)))
    assert(query === s"""SELECT "a", "b" FROM ($userQuery) A WHERE ("a" = 1)""")
  }

  test("enriches many filters") {
    val query = queryEnricher.enrichQuery(Array("a"), Array(EqualTo("a", 1), LessThan("b", 2)))
    assert(query === s"""SELECT "a" FROM ($userQuery) A WHERE ("a" = 1) AND ("b" < 2)""")
  }

}

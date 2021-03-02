package com.exasol.spark.util

import org.apache.spark.sql.sources._

import com.exasol.sql.expression.BooleanTerm
import com.exasol.sql.expression.rendering.ValueExpressionRenderer
import com.exasol.sql.rendering.StringRendererConfig

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class FiltersSuite extends AnyFunSuite with Matchers {

  private[this] def getWhereClause(filters: Seq[Filter]): String = {
    val booleanExpressions = Filters.booleanExpressionFromFilters(filters)
    val andExpression = BooleanTerm.and(booleanExpressions: _*)
    val rendererConfig = StringRendererConfig.builder().quoteIdentifiers(true).build()
    val renderer = new ValueExpressionRenderer(rendererConfig)
    andExpression.accept(renderer)
    renderer.render()
  }

  test("renders empty filters") {
    assert(getWhereClause(Seq.empty[Filter]) === "")
  }

  test("renders equal to") {
    assert(getWhereClause(Seq(EqualTo("field", "a"))) === """("field" = 'a')""")
  }

  // scalastyle:off nonascii
  test("renders equal to with different data types") {
    val filters = Seq(
      EqualTo("bool_col", false),
      EqualTo("str_col", "XYZ"),
      EqualTo("str_col", "\u00d6"),
      EqualTo("str_col", "He said 'good morning'"),
      EqualTo("int_col", 42),
      EqualTo("float_col", 13.0f),
      EqualTo("double_col", 100.0),
      EqualTo("date_col", "2018-01-01"),
      EqualTo("datetime_col", "2018-01-01 00:00:59.123")
    )
    val expected =
      """
        |    ("bool_col" = FALSE)
        |AND ("str_col" = 'XYZ')
        |AND ("str_col" = 'Ö')
        |AND ("str_col" = 'He said 'good morning'')
        |AND ("int_col" = 42)
        |AND ("float_col" = 13.0)
        |AND ("double_col" = 100.0)
        |AND ("date_col" = '2018-01-01')
        |AND ("datetime_col" = '2018-01-01 00:00:59.123')
      """.stripMargin.replaceAll("\\s+", " ").trim()
    assert(getWhereClause(filters) === expected)
  }
  // scalastyle:on nonascii

  test("renders not equal to") {
    assert(getWhereClause(Seq(Not(EqualTo("field", 1.0)))) === """("field" <> 1.0)""")
  }

  test("renders greater than") {
    assert(getWhereClause(Seq(GreaterThan("field", 1))) === """("field" > 1)""")
  }

  test("renders greater than or equal") {
    assert(getWhereClause(Seq(GreaterThanOrEqual("field", 3L))) === """("field" >= 3)""")
  }

  test("renders less than") {
    assert(getWhereClause(Seq(LessThan("field", 2.1f))) === """("field" < 2.1)""")
  }

  test("renders less than or equal") {
    assert(getWhereClause(Seq(LessThanOrEqual("field", "e"))) === """("field" <= 'e')""")
  }

  ignore("renders is null") {
    assert(getWhereClause(Seq(IsNull("field"))) === """("field" IS NULL)""")
  }

  ignore("renders is not null") {
    assert(getWhereClause(Seq(IsNotNull("field"))) === """("field" IS NOT NULL)""")
  }

  test("renders string ends with") {
    assert(getWhereClause(Seq(StringEndsWith("field", "xyz"))) === """("field" LIKE '%xyz')""")
  }

  test("renders string contains") {
    assert(getWhereClause(Seq(StringContains("field", "in"))) === """("field" LIKE '%in%')""")
  }

  test("renders string starts with") {
    assert(getWhereClause(Seq(StringStartsWith("field", "abc"))) === """("field" LIKE 'abc%')""")
  }

  test("renders not") {
    assert(
      getWhereClause(Seq(Not(StringEndsWith("field", ".")))) === """NOT(("field" LIKE '%.'))"""
    )
  }

  test("renders and") {
    val filter = And(LessThan("field1", 1), EqualTo("field2", "one"))
    val expected = """(("field1" < 1) AND ("field2" = 'one'))"""
    assert(getWhereClause(Seq(filter)) === expected)
  }

  test("renders or") {
    val filter = Or(GreaterThanOrEqual("field1", 13.0), Not(EqualTo("field2", "one")))
    val expected = """(("field1" >= 13.0) OR ("field2" <> 'one'))"""
    assert(getWhereClause(Seq(filter)) === expected)
  }

  test("renders nested list of filters") {
    val filters = Seq(
      Or(EqualTo("str_col", "abc"), EqualTo("int_col", 123)),
      Or(Not(LessThan("int_col", 1)), GreaterThan("str_col", "a")),
      Or(EqualTo("str_col", "xyz"), And(EqualTo("float_col", 3.14), Not(EqualTo("int_col", 3))))
    )
    val expected =
      """
        |    (("str_col" = 'abc') OR ("int_col" = 123))
        |AND (NOT(("int_col" < 1)) OR ("str_col" > 'a'))
        |AND (("str_col" = 'xyz') OR (("float_col" = 3.14) AND ("int_col" <> 3)))
      """.stripMargin.replaceAll("\\s+", " ").trim()
    assert(getWhereClause(filters) === expected)
  }

}

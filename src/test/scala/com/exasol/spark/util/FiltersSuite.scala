package com.exasol.spark.util

import org.apache.spark.sql.sources._

import com.exasol.spark.util.Filters._

import org.scalatest.FunSuite
import org.scalatest.Matchers

@SuppressWarnings(Array("org.wartremover.warts.Any"))
class FiltersSuite extends FunSuite with Matchers {

  test("creates where clause from an empty list of filters") {
    assert(createWhereClause(Seq.empty[Filter]) === "")
  }

  test("creates where clause from a list of filters") {
    val inValues: Array[Any] = Array(1, 2, 3)

    val filters = Seq[Filter](
      EqualTo("str_col", "abc"),
      LessThan("int_col", 15),
      GreaterThan("int_col", 5),
      LessThanOrEqual("float_col", 100.0),
      GreaterThanOrEqual("float_col", 10.0),
      IsNull("null_col"),
      IsNotNull("null_col"),
      StringEndsWith("str_col", "suffix"),
      StringContains("str_col", "inside"),
      StringStartsWith("str_col", "prefix"),
      In("in_col", inValues)
    )
    val expected =
      """
        |    str_col = 'abc'
        |AND int_col < 15
        |AND int_col > 5
        |AND float_col <= 100.0
        |AND float_col >= 10.0
        |AND (null_col IS NULL)
        |AND (null_col IS NOT NULL)
        |AND (str_col LIKE '%suffix')
        |AND (str_col LIKE '%inside%')
        |AND (str_col LIKE 'prefix%')
        |AND (in_col IN ('1','2','3'))
      """.stripMargin.lines.mkString(" ").trim

    assert(createWhereClause(filters) === expected)
  }

  test("creates where clause from a nested list of filters") {
    val filters = Seq(
      Or(EqualTo("str_col", "abc"), EqualTo("int_col", 123)),
      Or(Not(IsNull("int_col")), IsNotNull("str_col")),
      Or(EqualTo("str_col", "xyz"), And(EqualTo("float_col", 3.14), Not(EqualTo("int_col", 3))))
    )
    val expected =
      """
        |((str_col = 'abc') OR (int_col = '123'))
        |AND (((NOT ((int_col IS NULL)))) OR ((str_col IS NOT NULL)))
        |AND ((str_col = 'xyz') OR (((float_col = '3.14') AND ((NOT (int_col = '3'))))))
    """.stripMargin.lines.mkString(" ").trim

    assert(createWhereClause(filters) === expected)
  }

}

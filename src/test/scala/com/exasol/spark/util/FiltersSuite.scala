package com.exasol.spark.util

import org.apache.spark.sql.sources._
import org.apache.spark.sql.types._

import com.exasol.spark.util.Filters._

import org.scalatest.FunSuite
import org.scalatest.Matchers

@SuppressWarnings(Array("org.wartremover.warts.Any"))
class FiltersSuite extends FunSuite with Matchers {

  val inValues: Array[Any] = Array(1, 2, 3)

  val testSchema: StructType = new StructType()
    .add("bool_col", BooleanType)
    .add("str_col", StringType)
    .add("int_col", IntegerType)
    .add("float_col", FloatType)
    .add("double_col", DoubleType)
    .add("null_col", StringType)
    .add("in_col", IntegerType)

  test("creates where clause from an empty list of filters") {
    assert(createWhereClause(StructType(Array.empty[StructField]), Seq.empty[Filter]) === "")
  }

  test("creates a where clause with non-existing columns in schema") {
    val filters = Seq[Filter](
      LessThanOrEqual("str_col", "123"),
      Not(EqualTo("unknown_col", 1337.0)),
      GreaterThan("int_col", 42),
      Not(In("in_col", inValues))
    )

    val expected =
      """
        |    str_col <= '123'
        |AND int_col > 42
        |AND (in_col NOT IN (1,2,3))
    """.stripMargin.lines.mkString(" ").trim

    assert(createWhereClause(testSchema, filters) === expected)
  }

  test("creates where clause from a list of filters") {
    val filters = Seq[Filter](
      EqualTo("str_col", "abc"),
      Not(EqualTo("int_col", 7)),
      LessThan("int_col", 15),
      GreaterThan("int_col", 5),
      LessThanOrEqual("float_col", 100.0),
      GreaterThanOrEqual("float_col", 10.0),
      EqualTo("double_col", 0.13),
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
        |AND int_col != 7
        |AND int_col < 15
        |AND int_col > 5
        |AND float_col <= 100.0
        |AND float_col >= 10.0
        |AND double_col = 0.13
        |AND (null_col IS NULL)
        |AND (null_col IS NOT NULL)
        |AND (str_col LIKE '%suffix')
        |AND (str_col LIKE '%inside%')
        |AND (str_col LIKE 'prefix%')
        |AND (in_col IN (1,2,3))
      """.stripMargin.lines.mkString(" ").trim

    assert(createWhereClause(testSchema, filters) === expected)
  }

  test("creates where clause from EqualTo with different data types") {
    val filters = Seq(
      EqualTo("bool_col", false),
      EqualTo("str_col", "XYZ"),
      // EqualTo("str_col", "unicode str"),
      // EqualTo("str_col", "quoted str"),
      EqualTo("int_col", 42),
      EqualTo("float_col", 13.0f),
      EqualTo("double_col", 100.0)
      // EqualTo("date_col", ...),
      // EqualTo("datetime_col", ...),
    )

    val expected =
      """
        |    bool_col = false
        |AND str_col = 'XYZ'
        |AND int_col = 42
        |AND float_col = 13.0
        |AND double_col = 100.0
      """.stripMargin.lines.mkString(" ").trim

    assert(createWhereClause(testSchema, filters) === expected)
  }

  test("creates where clause from a nested list of filters") {
    val filters = Seq(
      Or(EqualTo("str_col", "abc"), EqualTo("int_col", 123)),
      Or(Not(IsNull("int_col")), IsNotNull("str_col")),
      Or(EqualTo("str_col", "xyz"), And(EqualTo("float_col", 3.14), Not(EqualTo("int_col", 3))))
    )

    val expected =
      """
        |((str_col = 'abc') OR (int_col = 123))
        |AND (((NOT ((int_col IS NULL)))) OR ((str_col IS NOT NULL)))
        |AND ((str_col = 'xyz') OR (((float_col = 3.14) AND (int_col != 3))))
    """.stripMargin.lines.mkString(" ").trim

    assert(createWhereClause(testSchema, filters) === expected)
  }

}

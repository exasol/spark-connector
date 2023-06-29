package com.exasol.spark

import java.math.BigDecimal
import java.nio.charset.StandardCharsets.UTF_8
import java.sql.Date
import java.sql.ResultSet
import java.sql.Timestamp

import org.apache.spark.sql.Encoder

import com.exasol.matcher.ResultSetStructureMatcher.table
import com.exasol.matcher.TypeMatchMode._

import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat

class SparkDataImportIT extends BaseTableQueryIT {

  private[this] val INT_MIN = -2147483648
  private[this] val INT_MAX = 2147483647
  private[this] val LONG_MIN = -9223372036854775808L
  private[this] val LONG_MAX = 9223372036854775807L

  import sqlContext.implicits._

  test("saves boolean") {
    SparkImportChecker(Seq(true, false)).assert(
      table()
        .row(java.lang.Boolean.TRUE)
        .row(java.lang.Boolean.FALSE)
        .matches()
    )
  }

  test("saves integer") {
    SparkImportChecker(Seq(1, 13, INT_MIN, INT_MAX)).assert(
      table()
        .row(java.lang.Integer.valueOf(1))
        .row(java.lang.Integer.valueOf(13))
        .row(java.lang.Integer.valueOf(INT_MIN))
        .row(java.lang.Integer.valueOf(INT_MAX))
        .matches(NO_JAVA_TYPE_CHECK)
    )
  }

  test("saves long") {
    SparkImportChecker(Seq(1L, LONG_MIN, LONG_MAX)).assert(
      table()
        .row(java.lang.Long.valueOf(1))
        .row(java.lang.Long.valueOf(LONG_MIN))
        .row(java.lang.Long.valueOf(LONG_MAX))
        .matches(NO_JAVA_TYPE_CHECK)
    )
  }

  test("saves double") {
    SparkImportChecker(Seq(3.14, 2.71)).assert(
      table()
        .row(java.lang.Double.valueOf(3.14))
        .row(java.lang.Double.valueOf(2.71))
        .matches()
    )
  }

  test("saves float") {
    SparkImportChecker(Seq(1.01f, 0.45f)).assert(
      table()
        .withDefaultNumberTolerance(new BigDecimal(1e-3))
        .row(java.lang.Float.valueOf("1.01"))
        .row(java.lang.Float.valueOf("0.45"))
        .matches(NO_JAVA_TYPE_CHECK)
    )
  }

  test("saves short") {
    SparkImportChecker(Seq(2.toShort, 3.toShort)).assert(
      table()
        .row(java.lang.Short.valueOf("2"))
        .row(java.lang.Short.valueOf("3"))
        .matches(NO_JAVA_TYPE_CHECK)
    )
  }

  test("saves byte") {
    SparkImportChecker(Seq(13.toByte, 127.toByte)).assert(
      table()
        .row(java.lang.Byte.valueOf("13"))
        .row(java.lang.Byte.valueOf("127"))
        .matches(NO_JAVA_TYPE_CHECK)
    )
  }

  test("saves string") {
    SparkImportChecker(Seq("str", "abc", null)).assert(
      table()
        .row("str")
        .row("abc")
        .row(null)
        .matches()
    )
  }

  test("saves bytes") {
    SparkImportChecker(Seq("hello".getBytes(UTF_8), "world".getBytes(UTF_8), null)).assert(
      table()
        .row("hello")
        .row("world")
        .row(null)
        .matches()
    )
  }

  test("saves date") {
    SparkImportChecker(Seq(Date.valueOf("1986-02-25"), Date.valueOf("2021-02-18"), null)).assert(
      table()
        .row(java.sql.Date.valueOf("1986-02-25"))
        .row(java.sql.Date.valueOf("2021-02-18"))
        .row(null)
        .matches()
    )
  }

  test("saves timestamp") {
    val timestamp1 = Timestamp.from(java.time.Instant.EPOCH)
    val timestamp2 = new Timestamp(System.currentTimeMillis())
    SparkImportChecker(Seq(timestamp1, timestamp2, null)).assert(
      table()
        .row(timestamp1)
        .row(timestamp2)
        .row(null)
        .matches()
    )

  }

  test("saves decimal") {
    SparkImportChecker(Seq(new BigDecimal("123.333"), new BigDecimal("1.666"), null)).assert(
      table()
        .row(java.lang.Double.valueOf(123.333))
        .row(java.lang.Double.valueOf(1.666))
        .row(null)
        .matches(NO_JAVA_TYPE_CHECK)
    )
  }

  test("saves unsupported type") {
    val thrown = intercept[IllegalArgumentException] {
      SparkImportChecker(Seq(Map("a" -> 1L))).assert(
        table()
          .row("""{"a":1}""")
          .matches()
      )
    }
    assert(thrown.getMessage().startsWith("F-SEC-8"))
  }

  case class SparkImportChecker[T: Encoder](input: Seq[T]) {
    def assert(matcher: Matcher[ResultSet]): Unit = {
      spark
        .createDataset(input)
        .toDF("col_field")
        .write
        .mode("overwrite")
        .options(getDefaultOptions() ++ Map("table" -> tableName, "drop_table" -> "true"))
        .format("exasol")
        .save()

      exasolConnectionManager
        .withExecuteQuery(s"SELECT * FROM $tableName")(assertThat(_, matcher))
    }
  }

}

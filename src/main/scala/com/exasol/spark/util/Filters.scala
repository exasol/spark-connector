package com.exasol.spark.util

import org.apache.spark.sql.sources._

import com.typesafe.scalalogging.LazyLogging

/**
 * A helper class with functions to create Exasol where clauses from Spark
 * [[org.apache.spark.sql.sources.Filter]]-s
 */
object Filters extends LazyLogging {

  /**
   * Creates an Exasol SQL where clause from given list of
   * [[org.apache.spark.sql.sources.Filter]]-s. Then these set of predicates will be pushed to
   * Exasol for evaluation.
   *
   * @param filters A sequence of Spark source filters
   * @return A created Exasol where clause
   */
  @SuppressWarnings(Array("org.wartremover.warts.Option2Iterable"))
  def createWhereClause(filters: Seq[Filter]): String =
    filters.flatMap(filterExpr).mkString(" AND ")

  /**
   * Given a Spark source [[org.apache.spark.sql.sources.Filter]], create a Exasol SQL expression.
   * Returns [[scala.None]] if an expression could not be created from the filter.
   */
  def filterExpr(filter: Filter): Option[String] = filter match {
    case EqualTo(a, v)            => Option(s"$a = '$v'")
    case GreaterThan(a, v)        => Option(s"$a > $v")
    case GreaterThanOrEqual(a, v) => Option(s"$a >= $v")
    case LessThan(a, v)           => Option(s"$a < $v")
    case LessThanOrEqual(a, v)    => Option(s"$a <= $v")
    case IsNull(a)                => Option(s"($a IS NULL)")
    case IsNotNull(a)             => Option(s"($a IS NOT NULL)")
    case StringEndsWith(a, v)     => Option(s"($a LIKE '%$v')")
    case StringContains(a, v)     => Option(s"($a LIKE '%$v%')")
    case StringStartsWith(a, v)   => Option(s"($a LIKE '$v%')")
    case In(a, vs)                => Option(s"($a IN (${vs.map(v => s"'$v'").mkString(",")}))")
    case Not(f)                   => notExpr(f)
    case And(lf, rf)              => binaryExpr(lf, rf, "AND")
    case Or(lf, rf)               => binaryExpr(lf, rf, "OR")
  }

  def notExpr(filter: Filter): Option[String] =
    filterExpr(filter).flatMap(expr => Option(s"(NOT ($expr))"))

  @SuppressWarnings(Array("org.wartremover.warts.Return", "org.wartremover.warts.OptionPartial"))
  def binaryExpr(lf: Filter, rf: Filter, op: String): Option[String] = {
    val leftOpt = filterExpr(lf)
    if (!leftOpt.isDefined) {
      return None // scalastyle:ignore return
    }

    val rightOpt = filterExpr(rf)
    if (!rightOpt.isDefined) {
      None
    } else {
      Option(s"((${leftOpt.get}) $op (${rightOpt.get}))")
    }
  }

}

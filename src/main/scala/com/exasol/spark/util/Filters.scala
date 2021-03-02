package com.exasol.spark.util

import org.apache.spark.sql.sources._

import com.exasol.sql.expression.BooleanExpression
import com.exasol.sql.expression.BooleanTerm
import com.exasol.sql.expression.ExpressionTerm._
import com.exasol.sql.expression.ValueExpression

/**
 * A helper class with functions to create Exasol where clauses from Spark
 * [[org.apache.spark.sql.sources.Filter]]-s.
 */
object Filters {

  /**
   * Converts a sequence of filters into an Exasol boolean expressions.
   *
   * @param filters a sequence of Spark source filters
   * @return a sequence of Exasol boolean expressions
   */
  def booleanExpressionFromFilters(filters: Seq[Filter]): Seq[BooleanExpression] =
    filters.map(filterToBooleanExpression(_)).map(_.toList).flatten

  /**
   * Given a Spark source [[org.apache.spark.sql.sources.Filter]],
   * creates an Exasol boolean expression.
   *
   * @param filter a Spark source filter
   * @return an Exasol boolean expression, [[scala.None]] is returned if
   *         expression cannot be created from the filter
   */
  // scalastyle:off null
  // Suppression is accepted since we have terminal conditions in the
  // recursion.
  @SuppressWarnings(Array("org.wartremover.warts.Recursion"))
  def filterToBooleanExpression(filter: Filter): Option[BooleanExpression] =
    Option(filter match {
      case EqualTo(attribute, value) =>
        BooleanTerm.eq(column(attribute), getLiteral(value))
      case Not(EqualTo(attribute, value)) =>
        BooleanTerm.compare(column(attribute), "<>", getLiteral(value))
      case GreaterThan(attribute, value) =>
        BooleanTerm.gt(column(attribute), getLiteral(value))
      case GreaterThanOrEqual(attribute, value) =>
        BooleanTerm.ge(column(attribute), getLiteral(value))
      case LessThan(attribute, value) =>
        BooleanTerm.lt(column(attribute), getLiteral(value))
      case LessThanOrEqual(attribute, value) =>
        BooleanTerm.le(column(attribute), getLiteral(value))
      // case IsNull(attribute) => stringLiteral(s"""("$attribute" IS NULL)""")
      // case IsNotNull(attribute) => stringLiteral(s"""("$attribute" IS NOT NULL)""")
      case StringEndsWith(attribute, value) =>
        BooleanTerm.like(column(attribute), stringLiteral(s"%$value"))
      case StringContains(attribute, value) =>
        BooleanTerm.like(column(attribute), stringLiteral(s"%$value%"))
      case StringStartsWith(attribute, value) =>
        BooleanTerm.like(column(attribute), stringLiteral(s"$value%"))
      // case In(a, vs) => inExpr(a, vs, "IN", dataTypes)
      // case Not(In(a, vs)) => inExpr(a, vs, "NOT IN", dataTypes)
      case Not(notFilter) =>
        filterToBooleanExpression(notFilter).map(BooleanTerm.not(_)).getOrElse(null)
      case And(leftFilter, rightFilter) =>
        val leftExpr = filterToBooleanExpression(leftFilter)
        val rightExpr = filterToBooleanExpression(rightFilter)
        if (leftExpr.isDefined && rightExpr.isDefined) {
          BooleanTerm.and(leftExpr.getOrElse(null), rightExpr.getOrElse(null))
        } else {
          null
        }
      case Or(leftFilter, rightFilter) =>
        val leftExpr = filterToBooleanExpression(leftFilter)
        val rightExpr = filterToBooleanExpression(rightFilter)
        if (leftExpr.isDefined && rightExpr.isDefined) {
          BooleanTerm.or(leftExpr.getOrElse(null), rightExpr.getOrElse(null))
        } else {
          null
        }
      case _ => null
    })
  // scalastyle:on null

  private[this] def getLiteral(value: Any): ValueExpression =
    value match {
      case booleanValue: Boolean => booleanLiteral(booleanValue)
      case stringValue: String   => stringLiteral(stringValue)
      case byteValue: Byte       => integerLiteral(byteValue.toInt)
      case shortValue: Short     => integerLiteral(shortValue.toInt)
      case integerValue: Int     => integerLiteral(integerValue)
      case longValue: Long       => longLiteral(longValue)
      case floatValue: Float     => floatLiteral(floatValue)
      case doubleValue: Double   => doubleLiteral(doubleValue)
      // case bigDecimalValue: BigDecimal => BigDecimalLiteral(bigDecimalValue.underlying())
      // case bigDecimalValue: java.math.BigDecimal => BigDecimalLiteral(bigDecimalValue)
      case _ => stringLiteral(s"$value")
    }

}

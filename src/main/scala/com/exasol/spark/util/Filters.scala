package com.exasol.spark.util

import org.apache.spark.sql.sources._
import org.apache.spark.sql.types._

/**
 * A helper class with functions to create Exasol where clauses from Spark
 * [[org.apache.spark.sql.sources.Filter]]-s.
 */
object Filters {

  /**
   * Creates an Exasol SQL where clause from given list of
   * [[org.apache.spark.sql.sources.Filter]]-s. Then these set of predicates
   * will be pushed to Exasol for evaluation.
   *
   * @param schema A user provided or inferred schema of the query
   * @param filters A sequence of Spark source filters
   * @return A created Exasol where clause
   */
  @SuppressWarnings(Array("org.wartremover.warts.Option2Iterable"))
  def createWhereClause(schema: StructType, filters: Seq[Filter]): String = {
    val dataTypes = schema.map(field => field.name -> field.dataType).toMap
    filters.flatMap(filterExpr(_, dataTypes)).mkString(" AND ")
  }

  /**
   * Given a Spark source [[org.apache.spark.sql.sources.Filter]], create a
   * Exasol SQL expression.
   *
   * Returns [[scala.None]] if an expression could not be created from the
   * filter.
   */
  def filterExpr(filter: Filter, dataTypes: Map[String, DataType]): Option[String] =
    filter match {
      case EqualTo(a, v)            => comparisonExpr(a, v, "=", dataTypes)
      case Not(EqualTo(a, v))       => comparisonExpr(a, v, "!=", dataTypes)
      case GreaterThan(a, v)        => comparisonExpr(a, v, ">", dataTypes)
      case GreaterThanOrEqual(a, v) => comparisonExpr(a, v, ">=", dataTypes)
      case LessThan(a, v)           => comparisonExpr(a, v, "<", dataTypes)
      case LessThanOrEqual(a, v)    => comparisonExpr(a, v, "<=", dataTypes)
      case IsNull(a)                => Option(s"($a IS NULL)")
      case IsNotNull(a)             => Option(s"($a IS NOT NULL)")
      case StringEndsWith(a, v)     => Option(s"($a LIKE '%$v')")
      case StringContains(a, v)     => Option(s"($a LIKE '%$v%')")
      case StringStartsWith(a, v)   => Option(s"($a LIKE '$v%')")
      case In(a, vs)                => inExpr(a, vs, "IN", dataTypes)
      case Not(In(a, vs))           => inExpr(a, vs, "NOT IN", dataTypes)
      case Not(f)                   => notExpr(f, dataTypes)
      case And(lf, rf)              => binaryExpr(lf, rf, "AND", dataTypes)
      case Or(lf, rf)               => binaryExpr(lf, rf, "OR", dataTypes)
      case _                        => None
    }

  def isQuotedValue(dataType: DataType, value: Any): String = dataType match {
    case StringType    => s"'$value'"
    case DateType      => s"date '$value'"
    case TimestampType => s"timestamp '$value'"
    case _             => s"$value"
  }

  def comparisonExpr(
    name: String,
    value: Any,
    op: String,
    types: Map[String, DataType]
  ): Option[String] = types.get(name).flatMap {
    case dtype =>
      val newValue = isQuotedValue(dtype, value)
      Option(s"$name $op $newValue")
  }

  def inExpr(
    name: String,
    values: Array[Any],
    op: String,
    types: Map[String, DataType]
  ): Option[String] =
    types.get(name).flatMap {
      case dtype =>
        val newValues = values.map(v => isQuotedValue(dtype, v))
        Option(s"($name $op (${newValues.mkString(",")}))")
    }

  def notExpr(f: Filter, types: Map[String, DataType]): Option[String] =
    filterExpr(f, types).flatMap(expr => Option(s"(NOT ($expr))"))

  @SuppressWarnings(Array("org.wartremover.warts.Return", "org.wartremover.warts.OptionPartial"))
  def binaryExpr(
    lf: Filter,
    rf: Filter,
    op: String,
    types: Map[String, DataType]
  ): Option[String] = {
    val leftOpt = filterExpr(lf, types)
    if (!leftOpt.isDefined) {
      return None // scalastyle:ignore return
    }

    val rightOpt = filterExpr(rf, types)
    if (!rightOpt.isDefined) {
      None
    } else {
      Option(s"((${leftOpt.get}) $op (${rightOpt.get}))")
    }
  }

}

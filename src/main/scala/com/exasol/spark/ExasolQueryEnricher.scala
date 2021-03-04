package com.exasol.spark

import org.apache.spark.sql.sources.Filter

import com.exasol.spark.util.Filters
import com.exasol.sql.StatementFactory
import com.exasol.sql.dql.select.Select
import com.exasol.sql.dql.select.rendering.SelectRenderer
import com.exasol.sql.expression.BooleanTerm.and
import com.exasol.sql.expression.ExpressionTerm.stringLiteral
import com.exasol.sql.expression.function.exasol.ExasolAggregateFunction.COUNT
import com.exasol.sql.rendering.StringRendererConfig

/**
 * Improves the original user query with column pruning and predicate
 * pushdown.
 *
 * @param userQuery A user provided initial query
 */
final case class ExasolQueryEnricher(userQuery: String) {

  private[this] val PLACEHOLDER_TABLE_NAME = "<PLACEHOLDER>"

  /**
   * Enriches user query with column pruning and where clause using the
   * provided column names and filters.
   *
   * Additionally, if no column names are provided it creates a {@code
   * COUNT('*')} query.
   *
   * @param columns A list of column names
   * @param filters A list of Spark [[org.apache.spark.sql.sources.Filter]]-s
   * @return An enriched query with column selection and predicate pushdown
   */
  def enrichQuery(columns: Array[String], filters: Array[Filter]): String = {
    val select = StatementFactory.getInstance().select()
    val _ = select.from().table(PLACEHOLDER_TABLE_NAME)
    addColumns(columns, select)
    addFilters(filters, select)
    renderSelectQuery(PLACEHOLDER_TABLE_NAME, select)
  }

  private[this] def addColumns(columns: Array[String], select: Select): Unit = {
    if (columns.isEmpty) {
      val _ = select.function(COUNT, stringLiteral("*"))
    } else {
      columns.foreach(column => select.field(column))
    }
    ()
  }

  private[this] def addFilters(filters: Array[Filter], select: Select): Unit = {
    val booleanExpressions = Filters.booleanExpressionFromFilters(filters)
    if (!booleanExpressions.isEmpty) {
      val _ = select.where(and(booleanExpressions: _*))
    }
    ()
  }

  private[this] def renderSelectQuery(subSelectPlaceholder: String, select: Select): String = {
    val rendererConfig = StringRendererConfig.builder().quoteIdentifiers(true).build()
    val renderer = new SelectRenderer(rendererConfig)
    select.accept(renderer)
    renderer.render().replace(s""""$subSelectPlaceholder"""", s"($userQuery) A")
  }

}

package com.exasol.spark

/**
 * Tests column pruning for user queries.
 */
class ColumnPruningIT extends BaseTableQueryIT {

  test("returns datarame with selected columns") {
    val df = getDataFrame().select("city")
    assert(df.columns.size === 1)
    assert(df.columns.head === "city")
    assert(df.collect().map(x => x.getString(0)) === Seq("Berlin", "Paris", "Lisbon"))
  }

}

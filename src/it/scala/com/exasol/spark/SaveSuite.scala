package com.exasol.spark

import org.apache.spark.{SparkConf, SparkException}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.FunSuite

/** Integration tests for saving Spark dataframes into Exasol tables */
class SaveSuite extends FunSuite with BaseDockerSuite with DataFrameSuiteBase {

  test("`tableExists` should return correct boolean result") {
    createDummyTable()

    assert(exaManager.tableExists(s"$EXA_SCHEMA.$EXA_TABLE") === true)
    assert(exaManager.tableExists("DUMMY_SCHEMA.DUMMYTABLE") === false)
  }

  test("`tuncateTable` should perform table truncation") {
    createDummyTable()

    assert(exaManager.withCountQuery(s"SELECT COUNT(*) FROM $EXA_SCHEMA.$EXA_TABLE") > 0)

    exaManager.truncateTable(s"$EXA_SCHEMA.$EXA_TABLE")
    assert(exaManager.withCountQuery(s"SELECT COUNT(*) FROM $EXA_SCHEMA.$EXA_TABLE") === 0)

    // Idempotent
    exaManager.truncateTable(s"$EXA_SCHEMA.$EXA_TABLE")
    assert(exaManager.withCountQuery(s"SELECT COUNT(*) FROM $EXA_SCHEMA.$EXA_TABLE") === 0)
  }

}

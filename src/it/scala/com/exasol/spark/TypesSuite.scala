package com.exasol.spark

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.FunSuite

class TypesSuite extends FunSuite with BaseDockerSuite with DataFrameSuiteBase {

  test("Create Exasol with all Types and test types in Spark") {
    createAllTypesTable()


  }

}

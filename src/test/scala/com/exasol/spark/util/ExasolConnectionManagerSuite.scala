package com.exasol.spark.util

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ExasolConnectionManagerSuite extends AnyFunSuite with Matchers {

  def getECMConnectionString(opts: Map[String, String]): String = {
    val conf: ExasolConfiguration = ExasolConfiguration.apply(opts)
    ExasolConnectionManager(conf).getJdbcConnectionString()
  }

  val emptyOpts: Map[String, String] = Map("host" -> "10.0.0.1", "port" -> "8888")

  test("check extra exasol jdbc options are correctly configured for establishing connection") {
    assert(getECMConnectionString(emptyOpts) === "jdbc:exa:10.0.0.1:8888")

    val correctOpts1 = emptyOpts ++ Map(
      "jdbc_options" -> "debug=1"
    )
    assert(getECMConnectionString(correctOpts1) === "jdbc:exa:10.0.0.1:8888;debug=1")

    val correctOpts2 = emptyOpts ++ Map(
      "jdbc_options" -> "debug=1;encryption=0"
    )
    assert(getECMConnectionString(correctOpts2) === "jdbc:exa:10.0.0.1:8888;debug=1;encryption=0")
  }

  test("check exasol jdbc options has invalid property format") {
    val incorrectOpt = emptyOpts ++ Map(
      "jdbc_options" -> "debug==1;encryption=0"
    )

    val thrown = intercept[IllegalArgumentException] {
      getECMConnectionString(incorrectOpt)
    }
    val thrownMsg = thrown.getMessage
    assert(thrownMsg === "property invalid: debug==1 does not have key=value format")
  }

  test("check exasol jdbc options start with semicolon") {
    val incorrectOpt = emptyOpts ++ Map(
      "jdbc_options" -> ";debug=1;encryption=0"
    )

    val thrown = intercept[IllegalArgumentException] {
      getECMConnectionString(incorrectOpt)
    }
    val thrownMsg = thrown.getMessage
    assert(thrownMsg === "jdbc option should not start or end with semicolon")
  }

}

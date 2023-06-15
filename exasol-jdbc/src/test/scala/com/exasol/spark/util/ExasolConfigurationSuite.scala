package com.exasol.spark.util

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ExasolConfigurationSuite extends AnyFunSuite with Matchers {

  @SuppressWarnings(Array("scala:S1313")) // Hardcoded IP addresses are safe in tests
  val validIPv4Addresses: Seq[String] = Seq(
    "1.1.1.1",
    "255.255.255.255",
    "192.168.1.1",
    "10.10.1.1",
    "10.0.0.11",
    "132.254.111.10",
    "26.10.2.10",
    "127.0.0.1"
  )

  val inValidIpv4Addresses: Seq[String] = Seq(
    "10",
    "10.10",
    "10.10.10",
    "a.a.a.a",
    "www.exasol.com",
    "10.0.0.a",
    "10.10.10.256",
    "222.222.2.999",
    "999.10.10.20",
    "2222.22.22.22",
    "22.2222.22.2"
  )

  test("check host value is ipv4") {
    validIPv4Addresses.foreach { host =>
      assert(ExasolConfiguration.checkHost(host) === host)
    }
    inValidIpv4Addresses.foreach { host =>
      intercept[IllegalArgumentException] {
        ExasolConfiguration.checkHost(host)
      }
    }
  }

}

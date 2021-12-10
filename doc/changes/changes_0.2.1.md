# Spark Exasol Connector 0.2.1, released 2019-07-15

## Fixes

* Fix bug with Decimal type conversion
  [#44](https://github.com/exasol/spark-connector/issues/44)
  [#47](https://github.com/exasol/spark-connector/pull/47) by @jpizagno
* Fix bug with JDBC string null value setting
  [#46](https://github.com/exasol/spark-connector/issues/46)
  [#52](https://github.com/exasol/spark-connector/pull/52) by @morazow
* Add Scala 2.12+ cross compilation for Spark versions above 2.4+
  [#38](https://github.com/exasol/spark-connector/issues/38)
  [#50](https://github.com/exasol/spark-connector/pull/50) by @morazow
* Improve integration testing Docker startup time
  [#51](https://github.com/exasol/spark-connector/issues/51)
  [#53](https://github.com/exasol/spark-connector/pull/53) by @3cham
* Add check that ensures host parameter value is an IPv4 address
  [#42](https://github.com/exasol/spark-connector/issues/42)
  [#48](https://github.com/exasol/spark-connector/pull/48)
  [#43](https://github.com/exasol/spark-connector/issues/43)
  [#49](https://github.com/exasol/spark-connector/pull/49) by @morazow

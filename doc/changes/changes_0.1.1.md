# Spark Exasol Connector 0.1.1, released 2018-10-25

## Fixes

* Use `date` and `timestamp` hints when creating where clause from Spark filters
  [#13](https://github.com/exasol/spark-connector/issues/13)
  [#17](https://github.com/exasol/spark-connector/pull/17) by @3cham
* Enable user provided `schema` when reading from Exasol
  [#5](https://github.com/exasol/spark-connector/issues/5)
  [#19](https://github.com/exasol/spark-connector/pull/19) by @3cham
* Fix ordering bug when pruning schema using required columns
  [#21](https://github.com/exasol/spark-connector/pull/21) by @morazow

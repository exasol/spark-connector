## Unreleased

* Spark dataframe `count` action pushdown
  [#24](https://github.com/EXASOL/spark-exasol-connector/issues/24)
  [#29](https://github.com/EXASOL/spark-exasol-connector/pull/29) by @morazow
* Add `unhandledFilters` function to ExasolRelation
  [#18](https://github.com/EXASOL/spark-exasol-connector/issues/18)
  [#30](https://github.com/EXASOL/spark-exasol-connector/pull/30) by @morazow

## v0.1.2

> 2018 NOV 05

* Use configurations from SparkConf when provided
  [#4](https://github.com/EXASOL/spark-exasol-connector/issues/4)
  [#23](https://github.com/EXASOL/spark-exasol-connector/pull/23) by @3cham
* Change sub connections to use `handle` from main connection
  [#22](https://github.com/EXASOL/spark-exasol-connector/issues/22)
  [#25](https://github.com/EXASOL/spark-exasol-connector/pull/25) by @morazow
* Add tests for different data types conversions
  [#15](https://github.com/EXASOL/spark-exasol-connector/issues/15)
  [#26](https://github.com/EXASOL/spark-exasol-connector/pull/26) by @jpizagno

## v0.1.1

> 2018 OCT 25

* Use `date` and `timestamp` hints when creating where clause from Spark filters
  [#13](https://github.com/EXASOL/spark-exasol-connector/issues/13)
  [#17](https://github.com/EXASOL/spark-exasol-connector/pull/17) by @3cham
* Enable user provided `schema` when reading from Exasol
  [#5](https://github.com/EXASOL/spark-exasol-connector/issues/5)
  [#19](https://github.com/EXASOL/spark-exasol-connector/pull/19) by @3cham
* Fix ordering bug when pruning schema using required columns
  [#21](https://github.com/EXASOL/spark-exasol-connector/pull/21) by @morazow

## v0.1.0

> 2018 OCT 04

This is first release version which includes initial setup of project for
continued improvements.

* Initial working version (with known issues)
* Initial predicate pushdown implementation
  [#6](https://github.com/EXASOL/spark-exasol-connector/pull/6)
  [#7](https://github.com/EXASOL/spark-exasol-connector/pull/7)
* Build plugins such as linting and formatting
* Travis CI based continuous build process
* Basic manual Sonatype release script
  [#11](https://github.com/EXASOL/spark-exasol-connector/pull/11)

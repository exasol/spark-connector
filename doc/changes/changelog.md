# Releases

* [1.0.0](changes_1.1.0.md)
* [1.0.0](changes_1.0.0.md)
* [0.3.2](changes_0.3.2.md)
* [0.3.1](changes_0.3.1.md)
* [0.3.0](changes_0.3.0.md)

## v0.2.1

> 2019 JUL 15

* Fix bug with Decimal type conversion
  [#44](https://github.com/exasol/spark-exasol-connector/issues/44)
  [#47](https://github.com/exasol/spark-exasol-connector/pull/47) by @jpizagno
* Fix bug with JDBC string null value setting
  [#46](https://github.com/exasol/spark-exasol-connector/issues/46)
  [#52](https://github.com/exasol/spark-exasol-connector/pull/52) by @morazow
* Add Scala 2.12+ cross compilation for Spark versions above 2.4+
  [#38](https://github.com/exasol/spark-exasol-connector/issues/38)
  [#50](https://github.com/exasol/spark-exasol-connector/pull/50) by @morazow
* Improve integration testing Docker startup time
  [#51](https://github.com/exasol/spark-exasol-connector/issues/51)
  [#53](https://github.com/exasol/spark-exasol-connector/pull/53) by @3cham
* Add check that ensures host parameter value is an IPv4 address
  [#42](https://github.com/exasol/spark-exasol-connector/issues/42)
  [#48](https://github.com/exasol/spark-exasol-connector/pull/48)
  [#43](https://github.com/exasol/spark-exasol-connector/issues/43)
  [#49](https://github.com/exasol/spark-exasol-connector/pull/49) by @morazow

## v0.2.0

> 2019 JAN 21

* Add Spark 2.4+ version
  [#28](https://github.com/exasol/spark-exasol-connector/issues/28)
  [#37](https://github.com/exasol/spark-exasol-connector/pull/37) by @morazow

* Add Spark dataframe insert as Exasol table feature
  [#32](https://github.com/exasol/spark-exasol-connector/issues/32)
  [#37](https://github.com/exasol/spark-exasol-connector/pull/37) by @morazow

## v0.1.3

> 2018 DEC 10

* Add tests for Unicode strings in predicate pushdown
  [#12](https://github.com/exasol/spark-exasol-connector/issues/12)
  [#34](https://github.com/exasol/spark-exasol-connector/pull/34) by @jpizagno
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

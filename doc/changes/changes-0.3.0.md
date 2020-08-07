## Spark Exasol Connector v0.3.0, released 2020-08-??

### Bug Fixes

* [#60](https://github.com/exasol/spark-exasol-connector/issues/60): Removed
  `not null` constraint from string types (PR
  [#63](https://github.com/exasol/spark-exasol-connector/pull/63)).

### Improvements

* [#45](https://github.com/exasol/spark-exasol-connector/issues/45): Improved
  logging (PR [#56](https://github.com/exasol/spark-exasol-connector/pull/56)).
* [#62](https://github.com/exasol/spark-exasol-connector/issues/62): Updated
  Spark and Exasol versions (PR
  [#64](https://github.com/exasol/spark-exasol-connector/pull/64)).
* [#69](https://github.com/exasol/spark-exasol-connector/issues/69): Updated the
  document and changelog structure. (PR
  [#70](https://github.com/exasol/spark-exasol-connector/pull/70)).
* Added project logo (PR
  [#67](https://github.com/exasol/spark-exasol-connector/pull/67)).

### Documentation

* Fixed write example in readme (PR
  [#58](https://github.com/exasol/spark-exasol-connector/pull/58)).

### Dependency Updates

* Updated sbt version from `1.2.8` to `1.3.13`.
* Updated latest version of [sbt script](https://github.com/paulp/sbt-extras).
* Updated ``org.scalatest:scalatest`` from `3.1.1` to `3.2.0`.
* Updated ``org.mockito:mockito-core`` from `3.3.3` to `3.4.6`.
* Updated ``org.testcontainers:jdbc`` from `1.13.0` to `1.14.3`.
* Updated ``com.dimafeng:testcontainers-scala`` from `0.36.1` to `0.38.1`.

#### Plugin Updates

* Updated ``org.wartremover:sbt-wartremover`` from `2.4.5` to `2.4.10`.
* Updated ``org.wartremover:sbt-wartremover-contrib`` from `1.3.4` to `1.3.8`.
* Updated ``comeed3si9n:sbt-assembly`` from `0.14.10` to `0.15.0`.
* Updated ``com.timushev.sbt:sbt-updates`` from `0.5.0` to `0.5.1`.
* Updated ``com.xerial.sbt:sbt-sonatype`` from `3.9.2` to `3.9.4`.

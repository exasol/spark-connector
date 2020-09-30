# Spark Exasol Connector 0.3.2, released 2020-MM-DD

## Summary

In this release, we add support for additional JDBC options that can be provided
using `jdbc_options` parameter. In addition, we updated the Exasol JDBC version
to `7.0.0`.

## Features / Improvements

* [#76](https://github.com/exasol/spark-exasol-connector/issues/76): Enabled extra options for JDBC connection (PR [#77](https://github.com/exasol/spark-exasol-connector/pull/77)).
* [#78](https://github.com/exasol/spark-exasol-connector/issues/78): Updated Exasol JDBC version to `7.0.0` (PR [#79](https://github.com/exasol/spark-exasol-connector/pull/79)).

## Documentation

* [#80](https://github.com/exasol/spark-exasol-connector/issues/80): Removed auto release setup from Travis CI (PR [#83](https://github.com/exasol/spark-exasol-connector/pull/83)).
* [#81](https://github.com/exasol/spark-exasol-connector/issues/81): Updated user and development guide (PR [#82](https://github.com/exasol/spark-exasol-connector/pull/82)).

## Dependency Updates

* Updated Scala version from `2.12.10` to `2.12.12`.
* Updated ``com.exasol:exasol-jdbc`` from `6.2.5` to `7.0.0`.
* Updated ``org.mockito:mockito-core`` from `3.5.7` to `3.5.13`.
* Updated ``com.dimafeng:testcontainers-scala`` from `0.38.1` to `0.38.4`.

### Plugin Updates

* Added ``org.scoverage:sbt-coveralls`` plugin for additional coverage report.

# Spark Exasol Connector 1.0.0, released 2021-MM-DD

## Features / Improvements

## Refactoring

* #40: Added Exasol testcontainers, refactoring test environment (PR #87).

## Documentation

* #85: Updated documentation with configuration for the Databricks cluster (PR #86).

## Dependency Updates

### Runtime Dependency Updates

* Updated to `com.exasol:exasol-jdbc:7.0.0` to `7.0.7`
* Updated to `org.apache.spark:spark-core:2.4.5` to `3.0.1`
* Updated to `org.apache.spark:spark-sql:2.4.5` to `3.0.1`

### Test Dependency Updates

* Updated to `org.scalatest:scalatest:3.2.2` to `3.2.3`
* Updated to `org.mockito:mockito-core:3.5.13` to `3.7.7`
* Updated to `org.testcontainers:jdbc:1.14.3` to `1.15.2`
* Updated to `com.dimafeng:testcontainers-scala:0.38.4` to `0.39.1`
* Updated to `com.holdenkarau:spark-testing-base:2.4.5_0.14.0` to `3.0.1_1.0.0`

### Plugin Updates

* Updated to `sbt.version:1.3.13` to `1.7.7`
* Updated to `org.wartremover:sbt-wartremover:2.4.10` to `2.4.13`
* Updated to `org.wartremover:sbt-wartremover-contrib:1.3.8` to `1.3.11`
* Updated to `com.jsuereth:sbt-pgp:2.0.1` to `2.1.1`
* Updated to `org.xerial.sbt:sbt-sonatype:3.9.4` to `3.9.5`
* Removed `io.get-coursier:sbt-coursier`

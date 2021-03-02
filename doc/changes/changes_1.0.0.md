# Spark Exasol Connector 1.0.0, released 2021-MM-DD

## Features / Improvements

## Refactoring

* #40: Added Exasol testcontainers, refactored test environment (PR #87).

## Documentation

* #85: Updated documentation with configuration for the Databricks cluster (PR #86).

## Dependency Updates

### Runtime Dependency Updates

* Added `com.exasol:sql-statement-builder:4.3.0`
* Updated `com.exasol:exasol-jdbc:7.0.0` to `7.0.7`
* Updated `org.apache.spark:spark-core:2.4.5` to `3.0.1`
* Updated `org.apache.spark:spark-sql:2.4.5` to `3.0.1`

### Test Dependency Updates

* Added `com.exasol:exasol-testcontainers:3.5.1`
* Added `com.exasol:test-db-builder-java:3.0.0`
* Added `com.exasol:hamcrest-resultset-matcher:1.4.0`
* Removed `org.testcontainers:jdbc`
* Removed `com.dimafeng:testcontainers-scala`
* Updated `org.scalatest:scalatest:3.2.2` to `3.2.5`
* Updated `org.mockito:mockito-core:3.5.13` to `3.8.0`
* Updated `com.holdenkarau:spark-testing-base:2.4.5_0.14.0` to `3.0.1_1.0.0`

### Plugin Updates

* Updated `sbt.version:1.3.13` to `1.4.7`
* Updated `org.wartremover:sbt-wartremover:2.4.10` to `2.4.13`
* Updated `org.wartremover:sbt-wartremover-contrib:1.3.8` to `1.3.11`
* Updated `com.jsuereth:sbt-pgp:2.0.1` to `2.1.1`
* Updated `org.xerial.sbt:sbt-sonatype:3.9.4` to `3.9.5`
* Removed `io.get-coursier:sbt-coursier`

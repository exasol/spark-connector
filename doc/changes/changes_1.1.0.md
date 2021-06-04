# Spark Exasol Connector 1.1.0, released 2021-06-04

Code: Added Support for Java 8 Runtime

## Summary

This release adds support for Java 8 runtime environments. We updated `sql-statement-builder` that was released for Java 11 to `sql-statement-builder-java8` version.  

## Features

* #92: Added support for Java 8.

## Dependency Updates

### Runtime Dependency Updates

* Removed `com.exasol:sql-statement-builder:4.4.0`
* Added `com.exasol:sql-statement-builder-java8:4.4.1`

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:3.5.1` to `3.5.3`
* Updated `com.exasol:test-db-builder-java:3.1.0` to `3.1.1`
* Updated `org.scalatest:scalatest:3.2.5` to `3.2.9`
* Updated `org.mockito:mockito-core:3.8.0` to `3.11.0`

### Plugin Updates

* Updated `org.wartremover:sbt-wartremover:2.4.13` to `2.4.15`
* Updated `org.wartremover:sbt-wartremover-contrib:1.3.11` to `1.3.12`
* Updated `org.scoverage:sbt-scoverage:1.6.1` to `1.8.2`
* Updated `com.timushev.sbt:sbt-updates:0.5.1` to `0.5.3`
* Updated `org.xerial.sbt:sbt-sonatype:3.9.5` to `3.9.7`
* Updated `com.typesafe.sbt:sbt-git:1.0.0` to `1.0.1`

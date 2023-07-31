# Spark Connector 2.1.0, released 2023-??-??

Code name:

## Summary

## Features

* #190: Added predicate pushdown and column selection for `S3` variant

## Dependency Updates

### Spark Exasol Connector With JDBC

#### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:1.1.1` to `2.0.0`

#### Plugin Dependency Updates

* Updated `com.diffplug.spotless:spotless-maven-plugin:2.37.0` to `2.38.0`

### Spark Exasol Connector With S3

#### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:1.1.1` to `2.0.0`
* Updated `software.amazon.awssdk:s3:2.20.103` to `2.20.115`

#### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.506` to `1.12.518`
* Added `com.exasol:java-util-logging-testing:2.0.3`
* Updated `org.junit.jupiter:junit-jupiter-api:5.9.3` to `5.10.0`
* Updated `org.junit.jupiter:junit-jupiter:5.9.3` to `5.10.0`

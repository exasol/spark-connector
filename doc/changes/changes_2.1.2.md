# Spark Connector 2.1.2, released 2023-??-??

Code name:

## Summary

## Features

* 194: Wrong name of Exasol JDBC format in documentation
* 197: Committing transaction in the finally handler
* 198: Remove unused `Statement` variable

## Dependency Updates

### Spark Exasol Connector Parent POM

#### Plugin Dependency Updates

* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.3.0` to `3.4.0`

### Spark Exasol Connector With JDBC

#### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:2.0.0` to `2.0.1`

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:6.6.1` to `6.6.2`
* Updated `com.exasol:hamcrest-resultset-matcher:1.6.0` to `1.6.1`
* Updated `com.exasol:test-db-builder-java:3.4.2` to `3.5.1`

### Spark Exasol Connector With S3

#### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:2.0.0` to `2.0.1`
* Updated `org.apache.hadoop:hadoop-aws:3.3.4` to `3.3.6`

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:6.6.1` to `6.6.2`
* Updated `com.exasol:hamcrest-resultset-matcher:1.6.0` to `1.6.1`
* Updated `com.exasol:test-db-builder-java:3.4.2` to `3.5.1`
* Updated `org.testcontainers:junit-jupiter:1.18.3` to `1.19.0`
* Updated `org.testcontainers:localstack:1.18.3` to `1.19.0`

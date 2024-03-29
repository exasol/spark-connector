# Spark Connector 2.1.2, released 2023-09-27

Code name: Transaction commit fixes, security fixes in dependencies

## Summary

Fixes CVE-2023-42503 in org.apache.commons:commons-compress.
Fixes logic in transaction commit during the DB import.

## Features

* 194: Wrong name of Exasol JDBC format in documentation
* 197: Committing transaction in the finally handler
* 198: Remove unused `Statement` variable
* 206: Fix CVE-2023-42503 and update dependencies

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
* Updated `org.mockito:mockito-core:5.4.0` to `5.5.0`
* Updated `org.mockito:mockito-junit-jupiter:5.4.0` to `5.5.0`
* Updated `org.scalatestplus:scalatestplus-mockito_2.13:1.0.0-M2` to `1.0.0-SNAP5`
* Updated `org.scalatest:scalatest_2.13:3.2.9` to `3.3.0-SNAP4`

### Spark Exasol Connector With S3

#### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:2.0.0` to `2.0.1`
* Updated `org.apache.hadoop:hadoop-aws:3.3.4` to `3.3.6`

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:6.6.1` to `6.6.2`
* Updated `com.exasol:hamcrest-resultset-matcher:1.6.0` to `1.6.1`
* Updated `com.exasol:test-db-builder-java:3.4.2` to `3.5.1`
* Updated `org.mockito:mockito-junit-jupiter:5.4.0` to `5.5.0`
* Updated `org.testcontainers:junit-jupiter:1.18.3` to `1.19.0`
* Updated `org.testcontainers:localstack:1.18.3` to `1.19.0`

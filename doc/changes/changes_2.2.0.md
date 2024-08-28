# Spark Connector 2.2.0, released 2024-08-28

Code name: Fix Exasol-JDBC compatibility, CVE

## Summary
Fixed issue with Exasol-JDBC parameter change.
Fixed CVE-2024-25638 in dnsjava:dnsjava:jar:2.1.7:provided (hadoop-client dependency).

## Features

* #227: Spark Connector is not compatible with exasol-jdbc V24+
* #228: Fix vulnerabilitiy in dnsjava:dnsjava:jar:2.1.7:provided

## Dependency Updates

### Spark Exasol Connector With JDBC

#### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.20` to `24.1.2`
* Updated `com.exasol:spark-connector-common-java:2.0.1` to `2.0.5`

### Spark Exasol Connector With S3

#### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:2.0.1` to `2.0.5`

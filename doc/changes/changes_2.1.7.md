# Spark Connector 2.1.7, released 2024-05-14

Code name: Fix CVEs in runtime dependencies

## Summary
This release fixes the following vulnerabilities in dependencies:
CVE-2024-29131 & CVE-2024-29133 in org.apache.commons:commons-configuration2:jar:2.8.0:provided

## Features

* #224: CVE-2024-29131 & CVE-2024-29133 in org.apache.commons:commons-configuration2:jar:2.8.0:provided
* Fix issues in spark 3.3 dependencies caused by spark-connector-common-java upgrade

## Dependency Updates

### Spark Exasol Connector With JDBC

#### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:2.0.1` to `2.0.5`

### Spark Exasol Connector With S3

#### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:2.0.1` to `2.0.5`

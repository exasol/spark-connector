# The Spark Exasol Connector 1.5.0, released 2023-??-??

Code name:

## Summary

## Features

* #149: Added s3 import query generator and runner
* #150: Added S3 intermediate storage layer
* #159: Added cleanup process to remove intermediate data after job finish
* #160: Add support for writing to Exasol database using S3 as intermediate storage

## Security

* #151: Fixed vulnerability `CVE-2023-26048` coming with `jetty-util` transitive dependency

## Refactoring

* #158: Refactored common options class

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.19` to `7.1.20`
* Added `com.exasol:spark-connector-common-java:1.0.0`
* Updated `com.google.protobuf:protobuf-java:3.22.3` to `3.23.2`
* Added `org.apache.hadoop:hadoop-aws:3.3.5`
* Added `software.amazon.awssdk:s3:2.20.79`

### Test Dependency Updates

* Added `com.amazonaws:aws-java-sdk-s3:1.12.482`
* Updated `com.exasol:exasol-testcontainers:6.5.2` to `6.6.0`
* Added `nl.jqno.equalsverifier:equalsverifier:3.14.2`
* Added `org.junit.jupiter:junit-jupiter-api:5.9.3`
* Added `org.junit.jupiter:junit-jupiter:5.9.3`
* Updated `org.mockito:mockito-core:5.3.0` to `5.3.1`
* Added `org.mockito:mockito-junit-jupiter:5.3.1`
* Added `org.testcontainers:junit-jupiter:1.18.3`
* Added `org.testcontainers:localstack:1.18.3`

### Plugin Dependency Updates

* Updated `com.diffplug.spotless:spotless-maven-plugin:2.36.0` to `2.37.0`

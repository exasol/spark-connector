# The Spark Exasol Connector 1.5.0, released 2023-??-??

Code name:

## Summary

## Features

* #150: Added S3 intermediate storage layer

## Security

* #151: Fixed vulnerability `CVE-2023-26048` coming with `jetty-util` transitive dependency

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.19` to `7.1.20`
* Added `com.exasol:spark-connector-common-java:1.0.0`
* Updated `com.google.protobuf:protobuf-java:3.22.3` to `3.23.0`
* Added `org.apache.hadoop:hadoop-aws:3.3.5`
* Added `software.amazon.awssdk:s3:2.20.66`

### Test Dependency Updates

* Added `com.amazonaws:aws-java-sdk-s3:1.12.469`
* Added `nl.jqno.equalsverifier:equalsverifier:3.14.1`
* Added `org.junit.jupiter:junit-jupiter-api:5.9.3`
* Added `org.junit.jupiter:junit-jupiter:5.9.3`
* Updated `org.mockito:mockito-core:5.3.0` to `5.3.1`
* Added `org.mockito:mockito-junit-jupiter:5.3.1`
* Added `org.testcontainers:junit-jupiter:1.18.1`
* Added `org.testcontainers:localstack:1.18.1`

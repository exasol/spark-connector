# Spark Connector 2.2.1, released 2025-01-14

Code name: Fix vulnerabilities in dependencies

## Summary

This release fixes the following vulnerabilities in dependencies:

* CVE-2024-47554 in `commons-io:commons-io:jar:2.11.0:compile`
* CVE-2024-47561 in `org.apache.avro:avro:jar:1.11.3:compile`
* CVE-2024-47535 in `io.netty:netty-common:jar:4.1.109.Final:compile`
* CVE-2024-23454 in `org.apache.hadoop:hadoop-common:jar:3.3.6:provided`
* CVE-2024-12798 and CVE-2024-12801 in `ch.qos.logback:logback-core:jar:1.2.13:compile`
* CVE-2024-7254 in `com.google.protobuf:protobuf-java:jar:3.24.3:provided`
* CVE-2024-51504 in `org.apache.zookeeper:zookeeper:jar:3.9.2:compile`

## Security

* #230: Fixed vulnerabilities in dependencies

## Dependency Updates

### Spark Exasol Connector Parent POM

#### Plugin Dependency Updates

* Updated `org.apache.maven.plugins:maven-deploy-plugin:3.1.1` to `3.1.3`

### Spark Exasol Connector With JDBC

#### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:24.1.2` to `24.2.1`
* Updated `com.exasol:spark-connector-common-java:2.0.5` to `2.0.10`
* Updated `org.apache.avro:avro:1.11.3` to `1.11.4`

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:6.6.3` to `7.1.2`
* Updated `com.exasol:hamcrest-resultset-matcher:1.6.1` to `1.7.0`
* Updated `com.exasol:test-db-builder-java:3.5.1` to `3.6.0`
* Updated `org.apache.logging.log4j:log4j-1.2-api:2.20.0` to `2.24.3`
* Updated `org.apache.logging.log4j:log4j-api:2.20.0` to `2.24.3`
* Updated `org.apache.logging.log4j:log4j-core:2.20.0` to `2.24.3`
* Updated `org.mockito:mockito-core:5.5.0` to `5.15.2`
* Updated `org.mockito:mockito-junit-jupiter:5.5.0` to `5.15.2`

### Spark Exasol Connector With S3

#### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:2.0.5` to `2.0.10`
* Updated `org.apache.avro:avro:1.11.3` to `1.11.4`
* Updated `software.amazon.awssdk:s3:2.20.115` to `2.29.51`

#### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.639` to `1.12.780`
* Updated `com.exasol:exasol-testcontainers:6.6.3` to `7.1.2`
* Updated `com.exasol:hamcrest-resultset-matcher:1.6.1` to `1.7.0`
* Updated `com.exasol:test-db-builder-java:3.5.1` to `3.6.0`
* Updated `org.junit-pioneer:junit-pioneer:2.1.0` to `2.3.0`
* Updated `org.junit.jupiter:junit-jupiter-api:5.10.0` to `5.11.4`
* Updated `org.junit.jupiter:junit-jupiter:5.10.0` to `5.11.4`
* Updated `org.mockito:mockito-junit-jupiter:5.5.0` to `5.15.2`
* Updated `org.testcontainers:junit-jupiter:1.19.0` to `1.20.4`
* Updated `org.testcontainers:localstack:1.19.0` to `1.20.4`

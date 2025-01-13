# Spark Connector 2.2.1, released 2025-01-13

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

### Spark Exasol Connector With JDBC

#### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:2.0.5` to `2.0.10`
* Updated `org.apache.avro:avro:1.11.3` to `1.11.4`

### Spark Exasol Connector With S3

#### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:2.0.5` to `2.0.10`
* Updated `org.apache.avro:avro:1.11.3` to `1.11.4`
* Updated `org.apache.hadoop:hadoop-aws:3.3.6` to `3.4.1`

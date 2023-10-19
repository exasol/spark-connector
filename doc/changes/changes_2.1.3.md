# Spark Connector 2.1.3, released 2023-??-??

Code name: More flexibility for AWS Credentials specification in spark-connector-s3

## Summary
In addition to explicit AWS Credentials specification we now support environment variables and EC2 instance profiles.
Fixes CVE-2023-39410 in apache avro (transitive dependency).

## Features

* 192: Add support for AWS IAM Profile Credentials for s3 connector.

## Dependency Updates

### Spark Exasol Connector With JDBC

#### Compile Dependency Updates

* Added `org.apache.avro:avro:1.11.3`

### Spark Exasol Connector With S3

#### Compile Dependency Updates

* Added `org.apache.avro:avro:1.11.3`

#### Test Dependency Updates

* Added `org.junit-pioneer:junit-pioneer:2.1.0`

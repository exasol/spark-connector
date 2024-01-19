# Spark Connector 2.1.5, released 2024-01-19

Code name: Fix CVE-2024-21634 and CVE-2023-33546

## Summary

This release fixes CVE-2024-21634 in transitive test dependency `software.amazon.ion:ion-java`.

## Security

* #214: Fixed CVE-2024-21634 in `software.amazon.ion:ion-java`

## Dependency Updates

### Spark Exasol Connector Parent POM

#### Plugin Dependency Updates

* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.1.2` to `3.2.3`
* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.16.1` to `2.16.2`

### Spark Exasol Connector With JDBC

#### Plugin Dependency Updates

* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0`

### Spark Exasol Connector With S3

#### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.518` to `1.12.639`

#### Plugin Dependency Updates

* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0`

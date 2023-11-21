# Spark Connector 2.1.4, released 2023-11-21

Code name: PK back in config, CVE fixed in transitive deps.

## Summary

Project keeper added to Maven configuration.
Vulnerability fixed in transitive dependency.

## Features

* #211: Brought PK plugin back to the Maven config
* #212: Fix vulnerability in org.eclipse.parsson:parsson

## Dependency Updates

### Spark Exasol Connector Parent POM

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.3.0` to `1.3.1`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.4.0` to `3.4.1`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.16.0` to `2.16.1`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.10` to `0.8.11`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184` to `3.10.0.2594`

### Spark Exasol Connector With JDBC

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:6.6.2` to `6.6.3`

### Spark Exasol Connector With S3

#### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:6.6.2` to `6.6.3`

# The Spark Exasol Connector 1.2.2, released 2022-09-27

Code name: Fix vulnerabilities in dependencies

## Summary

This release fixes [sonatype-2022-5401](https://ossindex.sonatype.org/vulnerability/sonatype-2022-5401) in reload4j.

## Features

* #121: Fixed vulnerabilities in dependencies

## Dependency Updates

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:6.1.2` to `6.2.0`
* Updated `com.exasol:hamcrest-resultset-matcher:1.5.1` to `1.5.2`
* Updated `com.exasol:test-db-builder-java:3.3.3` to `3.3.4`
* Updated `org.apache.logging.log4j:log4j-1.2-api:2.18.0` to `2.19.0`
* Updated `org.apache.logging.log4j:log4j-core:2.18.0` to `2.19.0`
* Updated `org.mockito:mockito-core:4.6.1` to `4.8.0`

### Plugin Dependency Updates

* Updated `com.diffplug.spotless:spotless-maven-plugin:2.22.4` to `2.22.8`
* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.1` to `1.1.2`
* Updated `com.exasol:project-keeper-maven-plugin:2.5.0` to `2.8.0`
* Updated `net.alchim31.maven:scala-maven-plugin:4.6.1` to `4.6.3`
* Updated `org.apache.maven.plugins:maven-clean-plugin:3.2.0` to `2.5`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0` to `3.1.0`
* Updated `org.apache.maven.plugins:maven-install-plugin:2.5.2` to `2.4`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.2.2` to `3.3.0`
* Updated `org.apache.maven.plugins:maven-resources-plugin:3.2.0` to `2.6`
* Updated `org.apache.maven.plugins:maven-site-plugin:3.12.0` to `3.3`

# The Spark Exasol Connector 1.2.4, released 2023-08-10

Code name: Fixed remote Maven Central release bug

## Summary

In this release we fixed the bug due to release to the remote Maven Central repository. Because of this users were not able to reference the `spark-connector` in their dependencies. Additionally, we updated dependencies and fixed known vulnerabilities.

## Bug Fixes

* #123: Fixed release bug that errors when referencing artifact from maven central

## Refactoring

* #129: Updated dependencies and fixed vulnerabilities

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.11` to `7.1.17`
* Updated `com.google.protobuf:protobuf-java:3.21.8` to `3.22.1`

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:6.3.0` to `6.5.1`
* Updated `com.exasol:test-db-builder-java:3.4.0` to `3.4.2`
* Updated `org.apache.logging.log4j:log4j-1.2-api:2.19.0` to `2.20.0`
* Updated `org.apache.logging.log4j:log4j-api:2.19.0` to `2.20.0`
* Updated `org.apache.logging.log4j:log4j-core:2.19.0` to `2.20.0`
* Updated `org.mockito:mockito-core:4.8.0` to `5.2.0`

### Plugin Dependency Updates

* Updated `com.diffplug.spotless:spotless-maven-plugin:2.24.0` to `2.34.0`
* Updated `com.exasol:artifact-reference-checker-maven-plugin:0.4.1` to `0.4.2`
* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.2` to `1.2.2`
* Updated `com.exasol:project-keeper-maven-plugin:2.8.0` to `2.9.3`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.15` to `0.16`
* Updated `net.alchim31.maven:scala-maven-plugin:4.7.2` to `4.8.1`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.10.1` to `3.11.0`
* Updated `org.apache.maven.plugins:maven-deploy-plugin:3.0.0-M1` to `3.0.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5` to `3.0.0-M8`
* Updated `org.apache.maven.plugins:maven-javadoc-plugin:3.4.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.4.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5` to `3.0.0-M8`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.2.7` to `1.3.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.10.0` to `2.14.2`
* Removed `org.scalastyle:scalastyle-maven-plugin:1.0.0`

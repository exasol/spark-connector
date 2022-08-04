# The Spark Exasol Connector 1.2.1, released 2022-08-04

Code name: Dependency updates

## Bugfixes

* #118: Dependency update to fix vulnerabilities reported by ossindex and broken links
Updated com.fasterxml.jackson.core and excluded dependencies provided by
spark-core as current there is no update available.

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.4` to `7.1.11`
* Updated `org.scala-lang:scala-library:2.13.5` to `2.13.8`

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:5.1.1` to `6.1.2`
* Updated `com.exasol:test-db-builder-java:3.2.2` to `3.3.3`
* Updated `org.apache.logging.log4j:log4j-1.2-api:2.17.1` to `2.18.0`
* Added `org.apache.logging.log4j:log4j-core:2.18.0`
* Updated `org.mockito:mockito-core:4.2.0` to `4.6.1`

### Plugin Dependency Updates

* Updated `com.diffplug.spotless:spotless-maven-plugin:2.20.0` to `2.22.4`
* Updated `com.exasol:artifact-reference-checker-maven-plugin:0.4.0` to `0.4.1`
* Updated `com.exasol:error-code-crawler-maven-plugin:0.7.1` to `1.1.1`
* Updated `com.exasol:project-keeper-maven-plugin:1.3.4` to `2.5.0`
* Updated `net.alchim31.maven:scala-maven-plugin:4.5.6` to `4.6.1`
* Updated `org.apache.maven.plugins:maven-clean-plugin:3.1.0` to `3.2.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.9.0` to `3.10.1`
* Added `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5`
* Updated `org.apache.maven.plugins:maven-javadoc-plugin:3.3.1` to `3.4.0`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.2.4` to `3.3.0`
* Updated `org.apache.maven.plugins:maven-site-plugin:3.10.0` to `3.12.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:2.12.4` to `3.0.0-M5`
* Added `org.codehaus.mojo:flatten-maven-plugin:1.2.7`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.8.1` to `2.10.0`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.3.0` to `1.5.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.7` to `0.8.8`
* Added `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184`
* Updated `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.1.0` to `3.2.0`
* Updated `org.sonatype.plugins:nexus-staging-maven-plugin:1.6.8` to `1.6.13`

# Spark Exasol Connector 1.2.0, released 2021-12-??

Code name: Added support for certificate fingerprint

## Summary

In this release we added an option for providing Exasol certificate fingerprint value. Similarly, we added Spark versions to the released artifacts, improved errors and migrated to Maven based build from SBT build. We also updated Log4J dependency to fix recent `CVE-2021-44228` vulnerability.

## Features

* #103: Added an option so that users can provide certificate fingerprint

## Bugs

* #106: Fixed Log4J CVE-2021-44228 vulnerability
* #112: Updated Log4J dependency to 2.17.0 version

## Refactorings

* #54: Added Spark versions to the released artifacts
* #75: Added dependencies vulnerabilities checker
* #94: Added notice about Spark Streaming
* #96: Added unified error codes
* #101: Migrated to Maven based build from SBT
* #108: Removed custom loader class from tests

## Dependency Updates

### Compile Dependency Updates

* Added `com.exasol:error-reporting-java8:0.4.1`
* Added `com.exasol:exasol-jdbc:7.1.4`
* Added `com.exasol:sql-statement-builder-java8:4.5.0`
* Added `org.scala-lang:scala-library:2.13.5`

### Test Dependency Updates

* Added `com.exasol:exasol-testcontainers:5.1.1`
* Added `com.exasol:hamcrest-resultset-matcher:1.5.1`
* Added `com.exasol:test-db-builder-java:3.2.2`
* Added `org.apache.logging.log4j:log4j-1.2-api:2.17.1`
* Added `org.mockito:mockito-core:4.2.0`
* Added `org.scalatestplus:scalatestplus-mockito_2.13:1.0.0-M2`
* Added `org.scalatest:scalatest_2.13:3.2.9`

### Plugin Dependency Updates

* Added `com.diffplug.spotless:spotless-maven-plugin:2.20.0`
* Added `com.exasol:artifact-reference-checker-maven-plugin:0.4.0`
* Added `com.exasol:error-code-crawler-maven-plugin:0.7.1`
* Added `com.exasol:project-keeper-maven-plugin:1.3.4`
* Added `io.github.evis:scalafix-maven-plugin_2.13:0.1.4_0.9.31`
* Added `io.github.zlika:reproducible-build-maven-plugin:0.15`
* Added `net.alchim31.maven:scala-maven-plugin:4.5.6`
* Added `org.apache.maven.plugins:maven-assembly-plugin:3.3.0`
* Added `org.apache.maven.plugins:maven-clean-plugin:3.1.0`
* Added `org.apache.maven.plugins:maven-compiler-plugin:3.9.0`
* Added `org.apache.maven.plugins:maven-deploy-plugin:3.0.0-M1`
* Added `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0`
* Added `org.apache.maven.plugins:maven-gpg-plugin:3.0.1`
* Added `org.apache.maven.plugins:maven-install-plugin:2.5.2`
* Added `org.apache.maven.plugins:maven-jar-plugin:3.2.2`
* Added `org.apache.maven.plugins:maven-javadoc-plugin:3.3.1`
* Added `org.apache.maven.plugins:maven-resources-plugin:3.2.0`
* Added `org.apache.maven.plugins:maven-site-plugin:3.10.0`
* Added `org.apache.maven.plugins:maven-source-plugin:3.2.1`
* Added `org.apache.maven.plugins:maven-surefire-plugin:2.12.4`
* Added `org.codehaus.mojo:versions-maven-plugin:2.8.1`
* Added `org.itsallcode:openfasttrace-maven-plugin:1.3.0`
* Added `org.jacoco:jacoco-maven-plugin:0.8.7`
* Added `org.scalastyle:scalastyle-maven-plugin:1.0.0`
* Added `org.scalatest:scalatest-maven-plugin:2.0.2`
* Added `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.1.0`
* Added `org.sonatype.plugins:nexus-staging-maven-plugin:1.6.8`

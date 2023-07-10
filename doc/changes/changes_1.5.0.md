# The Spark Exasol Connector 1.5.0, released 2023-??-??

Code name:

## Summary

## Features

* #149: Added s3 import query generator and runner
* #150: Added S3 intermediate storage layer
* #159: Added cleanup process to remove intermediate data after job finish
* #160: Add support for writing to Exasol database using S3 as intermediate storage
* #168: Refactored to add module setup

## Security

* #151: Fixed vulnerability `CVE-2023-26048` coming with `jetty-util` transitive dependency

## Bugs

* #176: Fixed artifact upload of `sha256sum` files

## Refactoring

* #155: Unified user options
* #158: Refactored common options class
* #164: Validated that write directory is empty
* #171: Refactored artifact packaging and releasing for module setup
* #174: Refactored Github `.github/workflow/` action files
* #183: Updated user guide and prepared for release

## Dependency Updates

### Spark Exasol Connector Parent POM

#### Plugin Dependency Updates

* Added `com.exasol:error-code-crawler-maven-plugin:1.3.0`
* Added `io.github.zlika:reproducible-build-maven-plugin:0.16`
* Added `org.apache.maven.plugins:maven-clean-plugin:2.5`
* Added `org.apache.maven.plugins:maven-compiler-plugin:3.11.0`
* Added `org.apache.maven.plugins:maven-deploy-plugin:3.1.1`
* Added `org.apache.maven.plugins:maven-enforcer-plugin:3.3.0`
* Added `org.apache.maven.plugins:maven-install-plugin:2.4`
* Added `org.apache.maven.plugins:maven-site-plugin:3.3`
* Added `org.apache.maven.plugins:maven-surefire-plugin:3.1.2`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:2.0.1`
* Added `org.codehaus.mojo:flatten-maven-plugin:1.5.0`
* Added `org.codehaus.mojo:versions-maven-plugin:2.16.0`
* Added `org.itsallcode:openfasttrace-maven-plugin:1.6.2`
* Added `org.jacoco:jacoco-maven-plugin:0.8.10`
* Added `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184`
* Added `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.2.0`

### Spark Exasol Connector With JDBC

#### Compile Dependency Updates

* Added `com.exasol:error-reporting-java8:1.0.1`
* Added `com.exasol:exasol-jdbc:7.1.20`
* Added `com.exasol:spark-connector-common-java:1.1.1`
* Added `com.exasol:sql-statement-builder-java8:4.5.4`

#### Test Dependency Updates

* Added `com.exasol:exasol-testcontainers:6.6.1`
* Added `com.exasol:hamcrest-resultset-matcher:1.6.0`
* Added `com.exasol:test-db-builder-java:3.4.2`
* Added `org.apache.logging.log4j:log4j-1.2-api:2.20.0`
* Added `org.apache.logging.log4j:log4j-api:2.20.0`
* Added `org.apache.logging.log4j:log4j-core:2.20.0`
* Added `org.mockito:mockito-core:5.4.0`
* Added `org.mockito:mockito-junit-jupiter:5.4.0`
* Added `org.scalatestplus:scalatestplus-mockito_2.13:1.0.0-M2`
* Added `org.scalatest:scalatest_2.13:3.2.9`

#### Plugin Dependency Updates

* Added `com.diffplug.spotless:spotless-maven-plugin:2.37.0`
* Added `com.exasol:error-code-crawler-maven-plugin:1.2.3`
* Added `io.github.evis:scalafix-maven-plugin_2.13:0.1.4_0.9.31`
* Added `io.github.zlika:reproducible-build-maven-plugin:0.16`
* Added `net.alchim31.maven:scala-maven-plugin:4.8.1`
* Added `org.apache.maven.plugins:maven-clean-plugin:2.5`
* Added `org.apache.maven.plugins:maven-compiler-plugin:3.11.0`
* Added `org.apache.maven.plugins:maven-deploy-plugin:3.1.1`
* Added `org.apache.maven.plugins:maven-enforcer-plugin:3.3.0`
* Added `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0`
* Added `org.apache.maven.plugins:maven-install-plugin:2.4`
* Added `org.apache.maven.plugins:maven-jar-plugin:3.3.0`
* Added `org.apache.maven.plugins:maven-javadoc-plugin:3.5.0`
* Added `org.apache.maven.plugins:maven-resources-plugin:2.6`
* Added `org.apache.maven.plugins:maven-shade-plugin:3.4.1`
* Added `org.apache.maven.plugins:maven-site-plugin:3.3`
* Added `org.apache.maven.plugins:maven-surefire-plugin:3.0.0`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:1.5.1`
* Added `org.codehaus.mojo:flatten-maven-plugin:1.4.1`
* Added `org.codehaus.mojo:versions-maven-plugin:2.15.0`
* Added `org.itsallcode:openfasttrace-maven-plugin:1.6.2`
* Added `org.jacoco:jacoco-maven-plugin:0.8.9`
* Added `org.scalatest:scalatest-maven-plugin:2.2.0`
* Added `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184`
* Added `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.2.0`

### Spark Exasol Connector With S3

#### Compile Dependency Updates

* Added `com.exasol:spark-connector-common-java:1.1.1`
* Added `org.apache.hadoop:hadoop-aws:3.3.6`
* Added `org.scala-lang:scala-library:2.13.11`
* Added `software.amazon.awssdk:s3:2.20.100`

#### Test Dependency Updates

* Added `com.amazonaws:aws-java-sdk-s3:1.12.503`
* Added `com.exasol:exasol-testcontainers:6.6.1`
* Added `com.exasol:hamcrest-resultset-matcher:1.6.0`
* Added `com.exasol:test-db-builder-java:3.4.2`
* Added `org.junit.jupiter:junit-jupiter-api:5.9.3`
* Added `org.junit.jupiter:junit-jupiter:5.9.3`
* Added `org.mockito:mockito-junit-jupiter:5.4.0`
* Added `org.testcontainers:junit-jupiter:1.18.3`
* Added `org.testcontainers:localstack:1.18.3`

#### Plugin Dependency Updates

* Added `com.exasol:error-code-crawler-maven-plugin:1.2.3`
* Added `io.github.zlika:reproducible-build-maven-plugin:0.16`
* Added `org.apache.maven.plugins:maven-clean-plugin:2.5`
* Added `org.apache.maven.plugins:maven-compiler-plugin:3.11.0`
* Added `org.apache.maven.plugins:maven-deploy-plugin:3.1.1`
* Added `org.apache.maven.plugins:maven-enforcer-plugin:3.3.0`
* Added `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0`
* Added `org.apache.maven.plugins:maven-install-plugin:2.4`
* Added `org.apache.maven.plugins:maven-jar-plugin:2.4`
* Added `org.apache.maven.plugins:maven-resources-plugin:2.6`
* Added `org.apache.maven.plugins:maven-shade-plugin:3.4.1`
* Added `org.apache.maven.plugins:maven-site-plugin:3.3`
* Added `org.apache.maven.plugins:maven-surefire-plugin:3.0.0`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:1.5.1`
* Added `org.codehaus.mojo:flatten-maven-plugin:1.4.1`
* Added `org.codehaus.mojo:versions-maven-plugin:2.15.0`
* Added `org.itsallcode:openfasttrace-maven-plugin:1.6.2`
* Added `org.jacoco:jacoco-maven-plugin:0.8.9`
* Added `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184`
* Added `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.2.0`

# The Spark Exasol Connector 2.0.0, released 2023-07-18

Code name: Support S3 intermediate storage

## Summary

In this release we added support to use AWS S3 bucket as an intermediate storage layer when accessing Exasol database from Spark cluster.

With this release, we separated the connector into two variants, `S3` and `JDBC`. We recommend to use this new S3 variant instead of JDBC variant. It improves the stability of the connector.

Please refer to the user guide for updated usage instructions.

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

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.2.3` to `1.3.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0` to `3.1.2`
* Added `org.apache.maven.plugins:maven-gpg-plugin:3.1.0`
* Added `org.apache.maven.plugins:maven-source-plugin:3.2.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0` to `3.1.2`
* Updated `org.basepom.maven:duplicate-finder-maven-plugin:1.5.1` to `2.0.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.4.1` to `1.5.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.15.0` to `2.16.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.9` to `0.8.10`
* Added `org.sonatype.plugins:nexus-staging-maven-plugin:1.6.13`

### Spark Exasol Connector With S3

#### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.2.3` to `1.3.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0` to `3.1.2`
* Added `org.apache.maven.plugins:maven-gpg-plugin:3.1.0`
* Added `org.apache.maven.plugins:maven-javadoc-plugin:3.5.0`
* Added `org.apache.maven.plugins:maven-source-plugin:3.2.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0` to `3.1.2`
* Updated `org.basepom.maven:duplicate-finder-maven-plugin:1.5.1` to `2.0.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.4.1` to `1.5.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.15.0` to `2.16.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.9` to `0.8.10`
* Added `org.sonatype.plugins:nexus-staging-maven-plugin:1.6.13`

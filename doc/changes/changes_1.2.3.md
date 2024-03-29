# The Spark Exasol Connector 1.2.3, released 2022-10-25

Code name: Spark 3.3

## Summary

* Added support for Spark 3.3.
* Removed support for Spark 3.1
* Removed support for Spark 2.4.
* Removed support for Scala 2.12
* Dropped incomplete Scala 2.12 profile from build.

For Spark 3.3 the following vulnerabilities in dependencies were fixed by updating the dependencies:

* CVE-2022-42003: Uncontrolled Resource Consumption in `jackson-databind`
* CVE-2022-3171: Uncontrolled Resource Consumption ('Resource Exhaustion') in `org.apache.commons:commons-text`
* sonatype-2022-5820: Improper Restriction of XML External Entity Reference ('XXE') in `hadoop-common`

## Known Remaining Spark 3.3.4 Vulnerabilities and Sonatype Warnings

The following vulnerabilities are known in Spark 3.3.4, but no update is available at the time of this `spark-connector` update, so instead we evaluate the risks here.

* The command line tool `hdfs ec` has the known vulnerability sonatype-2022-5732, but the connector is not affected, since it does not use this tool. For more details see [HDFS-16766 on the Haddop issue tracker](https://issues.apache.org/jira/browse/HDFS-16766).
* Related to the vulnerability above the code creates `DocumentBuilderFactory` instances in various other locations, but the collection ticket [HADOOP-18469](https://issues.apache.org/jira/browse/HADOOP-18469) states that no additional issues are known as a result yet.

## Spark 3.2

At the moment no Spark 3.2 version is available where vulnerabilities in dependencies can be fixed or are not exploitable. So we cannot offer a connector for these versions until the upstream project provide updates.

While you can still download older versions of the `spark-connector` that support Spark 3.2, **we recommend updating**, because there are no secure 3.2 versions available at the time of this connector release. 

## Features

* #125: Added support for Spark 3.3

## Bugfixes

* #125: Fixed vulnerabilities in dependencies

## Dependency Updates

### Compile Dependency Updates

* Added `com.google.protobuf:protobuf-java:3.21.8`
* Added `org.apache.commons:commons-text:1.10.0`
* Updated `org.scala-lang:scala-library:2.13.8` to `2.13.10`

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:6.2.0` to `6.3.0`
* Updated `com.exasol:test-db-builder-java:3.3.4` to `3.4.0`
* Added `org.apache.logging.log4j:log4j-api:2.19.0`

### Plugin Dependency Updates

* Updated `com.diffplug.spotless:spotless-maven-plugin:2.22.8` to `2.24.0`
* Updated `net.alchim31.maven:scala-maven-plugin:4.6.3` to `4.7.2`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.3.0` to `3.4.0`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.5.0` to `1.6.1`
* Updated `org.scalatest:scalatest-maven-plugin:2.0.2` to `2.2.0`

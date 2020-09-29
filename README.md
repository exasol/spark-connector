# Spark Exasol Connector

<img alt="spark-exasol-connector logo" src="doc/images/spark-exasol-connector_128x128.png" style="float:left; padding:0px 10px 10px 10px;"/>

[![Build Status][travis-badge]][travis-link]
[![Codecov][codecov-badge]][codecov-link]
[![Coveralls][coveralls-badge]][coveralls-link]
[![Maven Central][maven-img-badge]][maven-link]

## Overview

Spark Exasol Connector that supports an integration between [Exasol][exasol] and
[Apache Spark][spark]. It allows creating Spark DataFrame from Exasol queries
and saving Spark DataFrame as an Exasol table.

## Features

* Creates Spark DataFrame from Exasol query results
* Saves Spark DataFrame to an Exasol table
* Allows to configure the Spark tasks for parallel connection
* Supports Spark DataFrame column pruning and filter push down

## Information for Users

* [User Guide](doc/user_guide/user_guide.md)
* [Changelog](doc/changes/changelog.md)

## Information for Contributors

* [Developer Guide](doc/development/developer_guide.md)

## Dependencies

In this section, we define all the dependencies together with their licenses
that are required for building, testing and running the connector.

The Java 8 is required for compiling and building the project. In the future
versions of Spark, we are planning to change to the newer JVM versions.

### Runtime Dependencies

| Dependency                                  | Purpose                                                         | License              |
|---------------------------------------------|-----------------------------------------------------------------|----------------------|
| [Exasol JDBC][exasol-jdbc-link]             | Accessing Exasol using JDBC and sub-connections                 | MIT License          |
| [Spark Core][spark]                         | Apache Spark core libraries for optimized computation           | Apache License 2.0   |
| [Spark SQL][spark-sql-link]                 | Apache Spark higher-level SQL and Dataframe interface libraries | Apache License 2.0   |

### Test Dependencies

| Dependency                                  | Purpose                                                         | License              |
|---------------------------------------------|-----------------------------------------------------------------|----------------------|
| [Scalatest][scalatest-link]                 | Testing tool for Scala and Java developers                      | Apache License 2.0   |
| [Scalatest Plus][scalatestplus-link]        | Integration support between Scalatest and Mockito               | Apache License 2.0   |
| [Mockito Core][mockitocore-link]            | Mocking framework for unit tests                                | MIT License          |
| [Testcontainers JDBC][tcont-jdbc-link]      | Testcontainers JDBC to help create JDBC based Docker containers | MIT License          |
| [Testcontainers Scala][tcont-scala-link]    | Scala wrapper for testcontainers-java                           | MIT License          |
| [Spark Testing Base][spark-testing-base]    | Library that helps to create tests for Spark applications       | Apache License 2.0   |

### Compiler Plugin Dependencies

These plugins help with project development.

| Plugin Name                                 | Purpose                                                         | License              |
|---------------------------------------------|-----------------------------------------------------------------|----------------------|
| [SBT Coursier][sbt-coursier-link]           | Pure Scala artifact fetching                                    | Apache License 2.0   |
| [SBT Wartremover][sbt-wartremover-link]     | Flexible Scala code linting tool                                | Apache License 2.0   |
| [SBT Wartremover Contrib][sbt-wcontrib-link]| Community managed additional warts for wartremover              | Apache License 2.0   |
| [SBT Assembly][sbt-assembly-link]           | Create fat jars with all project dependencies                   | MIT License          |
| [SBT API Mappings][sbt-apimapping-link]     | Plugin that fetches API mappings for common Scala libraries     | Apache License 2.0   |
| [SBT Scoverage][sbt-scoverage-link]         | Integrates the `scoverage` code coverage library                | Apache License 2.0   |
| [SBT Updates][sbt-updates-link]             | Checks Maven and Ivy repositories for dependency updates        | BSD 3-Clause License |
| [SBT Scalafmt][sbt-scalafmt-link]           | Plugin for https://scalameta.org/scalafmt/ formatting           | Apache License 2.0   |
| [SBT Scalastyle][sbt-style-link]            | Plugin for http://www.scalastyle.org/ Scala style checker       | Apache License 2.0   |
| [SBT Dependency Graph][sbt-depgraph-link]   | Plugin for visualizing dependency graph of your project         | Apache License 2.0   |
| [SBT Sonatype][sbt-sonatype-link]           | Sbt plugin for publishing Scala projects to the Maven central   | Apache License 2.0   |
| [SBT PGP][sbt-pgp-link]                     | PGP plugin for `sbt`                                            | BSD 3-Clause License |
| [SBT Git][sbt-git-link]                     | Plugin for Git integration, used to version the release jars    | BSD 2-Clause License |

[travis-badge]: https://travis-ci.com/exasol/spark-exasol-connector.svg?branch=master
[travis-link]: https://travis-ci.com/exasol/spark-exasol-connector
[codecov-badge]: https://codecov.io/gh/exasol/spark-exasol-connector/branch/master/graph/badge.svg
[codecov-link]: https://codecov.io/gh/exasol/spark-exasol-connector
[coveralls-badge]: https://coveralls.io/repos/github/exasol/spark-exasol-connector/badge.svg?branch=master
[coveralls-link]: https://coveralls.io/github/exasol/spark-exasol-connector?branch=master
[maven-img-badge]: https://img.shields.io/maven-central/v/com.exasol/spark-connector_2.12.svg
[maven-reg-badge]: https://maven-badges.herokuapp.com/maven-central/com.exasol/spark-connector_2.12/badge.svg
[maven-link]: https://maven-badges.herokuapp.com/maven-central/com.exasol/spark-connector_2.12
[exasol]: https://www.exasol.com/en/
[spark]: https://spark.apache.org/
[exasol-jdbc-link]: https://www.exasol.com/portal/display/DOWNLOAD/Exasol+Download+Section
[spark-sql-link]: https://spark.apache.org/sql/
[scalatest-link]: http://www.scalatest.org/
[scalatestplus-link]: https://github.com/scalatest/scalatestplus-mockito
[mockitocore-link]: https://site.mockito.org/
[tcont-jdbc-link]: https://www.testcontainers.org/modules/databases/jdbc/
[tcont-scala-link]: https://github.com/testcontainers/testcontainers-scala
[sbt-coursier-link]: https://github.com/coursier/coursier
[sbt-wartremover-link]: http://github.com/puffnfresh/wartremover
[sbt-wcontrib-link]: http://github.com/wartremover/wartremover-contrib
[sbt-assembly-link]: https://github.com/sbt/sbt-assembly
[sbt-apimapping-link]: https://github.com/ThoughtWorksInc/sbt-api-mappings
[sbt-scoverage-link]: http://github.com/scoverage/sbt-scoverage
[sbt-updates-link]: http://github.com/rtimush/sbt-updates
[sbt-scalafmt-link]: https://github.com/lucidsoftware/neo-sbt-scalafmt
[sbt-style-link]: https://github.com/scalastyle/scalastyle-sbt-plugin
[sbt-depgraph-link]: https://github.com/jrudolph/sbt-dependency-graph
[sbt-sonatype-link]: https://github.com/xerial/sbt-sonatype
[sbt-pgp-link]: https://github.com/xerial/sbt-sonatype
[sbt-git-link]: https://github.com/sbt/sbt-git

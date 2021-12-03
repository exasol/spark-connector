# Spark Exasol Connector

<img alt="spark-exasol-connector logo" src="doc/images/spark-exasol-connector_128x128.png" style="float:left; padding:0px 10px 10px 10px;"/>

[![Build Status](https://github.com/exasol/spark-exasol-connector/actions/workflows/ci-build.yml/badge.svg)](https://github.com/exasol/spark-exasol-connector/actions/workflows/ci-build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.exasol/spark-exasol-connector)](https://search.maven.org/artifact/com.exasol/spark-exasol-connector_2.13)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aspark-exasol-connector&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.exasol%3Aspark-exasol-connector)

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aspark-exasol-connector&metric=security_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aspark-exasol-connector)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aspark-exasol-connector&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aspark-exasol-connector)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aspark-exasol-connector&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aspark-exasol-connector)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aspark-exasol-connector&metric=sqale_index)](https://sonarcloud.io/dashboard?id=com.exasol%3Aspark-exasol-connector)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aspark-exasol-connector&metric=code_smells)](https://sonarcloud.io/dashboard?id=com.exasol%3Aspark-exasol-connector)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aspark-exasol-connector&metric=coverage)](https://sonarcloud.io/dashboard?id=com.exasol%3Aspark-exasol-connector)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aspark-exasol-connector&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=com.exasol%3Aspark-exasol-connector)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aspark-exasol-connector&metric=ncloc)](https://sonarcloud.io/dashboard?id=com.exasol%3Aspark-exasol-connector)

## Overview

Spark Exasol Connector (SEC) supports an integration between [Exasol][exasol]
and [Apache Spark][spark]. It allows creating Spark DataFrame from Exasol
queries and saving Spark DataFrame as an Exasol table.

## Features

* Creates Spark DataFrame from Exasol query results
* Saves Spark DataFrame to an Exasol table
* Allows configuring the Spark tasks for parallel connection
* Supports Spark DataFrame column pruning and filter push down

## Spark Streaming

At the moment, Spark Exasol Connector does not support integration with Spark
Streaming. Please check out the [Spark regular JDBC
integration](https://spark.apache.org/docs/latest/sql-data-sources-jdbc.html).

## Information for Users

* [User Guide](doc/user_guide/user_guide.md)
* [Changelog](doc/changes/changelog.md)

## Information for Contributors

* [Developer Guide](doc/development/developer_guide.md)
* [Dependencies](dependencies.md)

[exasol]: https://www.exasol.com/en/
[spark]: https://spark.apache.org/

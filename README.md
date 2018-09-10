# Spark Exasol Connector

[![Build Status](https://travis-ci.org/EXASOL/spark-exasol-connector.svg?branch=master)](https://travis-ci.org/EXASOL/spark-exasol-connector)
[![codecov](https://codecov.io/gh/EXASOL/spark-exasol-connector/branch/master/graph/badge.svg)](https://codecov.io/gh/EXASOL/spark-exasol-connector)

###### Please note that this is an open source project which is *not officially supported* by Exasol. We will try to help you as much as possible, but can't guarantee anything since this is not an official Exasol product.

This is a connector library that supports an integration between
[Exasol][exasol] and [Apache Spark][spark]. Using this connector, users can
read/write data from/to Exasol using Spark.

* [Quick Start](#quick-start)
* [Usage](#usage)
* [Building and Testing](#building-and-testing)
* [Configuration](#configuration)

## Quick Start

Here is short quick start on how to use the connector.

Reading data from Exasol,

```scala
// This is Exasol SQL Syntax
val exasolQueryString = "SELECT * FROM MY_SCHEMA.MY_TABLE"

val df = sparkSession
     .read
     .format("exasol")
     .option("host", "localhost")
     .option("port", "8888")
     .option("username", "sys")
     .option("password", "exasol")
     .option("query", exasolQueryString)
     .load()

df.show(10, false)
```

For more examples you can check [docs/examples](docs/examples.md).

## Usage

*TODO*: Add short description on how to use jar files via maven and sbt once the
publishing setup is decided.

## Building and Testing

Clone the repository,

```bash
git clone https://github.com/EXASOL/spark-exasol-connector

cd spark-exasol-connector/
```

Compile,

```bash
./sbtx compile
```

Run unit tests,

```bash
./sbtx test
```

To run integration tests, a separate docker network should be created first,

```bash
docker network create -d bridge --subnet 192.168.0.0/24 --gateway 192.168.0.1 dockernet
```

then run,

```bash
./sbtx it:test
```

The integration tests requires [docker][docker],
[exasol/docker-db][exa-docker-db], [testcontainers][testcontainers] and
[spark-testing-base][spark-testing-base].

In order to create a bundled jar,

```bash
./sbtx assembly
```

This creates a jar file under `target/` folder. The jar file can be used with
`spark-submit`, `spark-shell` or `pyspark` commands. For example,

```shell
spark-shell --jars /path/to/spark-exasol-connector-assembly-*.jar
```

## Configuration

*TODO*: Add short description on how to use or provide exasol parameters and
what they mean.

[exasol]: https://www.exasol.com/en/
[spark]: https://spark.apache.org/
[docker]: https://www.docker.com/
[exa-docker-db]: https://hub.docker.com/r/exasol/docker-db/
[testcontainers]: https://www.testcontainers.org/
[spark-testing-base]: https://github.com/holdenk/spark-testing-base

# User Guide

Spark Exasol Connector allows you to create Spark DataFrames from Exasol
queries. Similarly, you can use the connector to save the Spark DataFrames as
Exasol tables.

## Table of Contents

- [Getting Started](#getting-started)
- [Spark Exasol Connector as Dependency](#spark-exasol-connector-as-dependency)
- [Configuration Parameters](#configuration-options)
- [Creating a Spark DataFrame From Exasol Query](#creating-a-spark-dataframe-from-exasol-query)
- [Saving Spark DataFrame to an Exasol Table](#saving-spark-dataframe-to-an-exasol-table)
- [Troubleshooting](#troubleshooting)

## Getting Started

To use the connector, we assume you have an Exasol cluster running with a
version `6.0` or above. Similarly, you have a Spark cluster running with a
version `2.3.0` or later.

Please make sure that there is an access connection between the two clusters via
internal network addresses. The Spark Exasol Connector makes parallel
connections to the Exasol data nodes and assumes that data nodes have sequential
IPv4 addresses. For example, the first data node has an address `10.0.0.11`, the
second one is assigned `10.0.0.12`, and so on.

In addition, please make sure that the Exasol nodes are reachable from the Spark
cluster on the JDBC port (`8563`) and port range `20000-21000`. The port range
is used for the parallel connections from the Spark tasks to Exasol data nodes.

## Spark Exasol Connector as Dependency

The Spark Exasol Connector is released to the Maven Central Repository. You can
find all the releases at [com.exasol/spark-connector][maven-link] page.

[maven-link]: https://mvnrepository.com/artifact/com.exasol/spark-connector

There are several options to include the connector as a dependency to your
projects. Here we assume you know the basics of providing dependencies to your
projects.

### Spark Exasol Connector as Maven Dependency

You can provide the connector as a dependency to your Spark Java applications.

For that add the Exasol Artifactory and the dependency itself to your project:

```xml
<repositories>
    <repository>
        <id>maven.exasol.com</id>
        <url>https://maven.exasol.com/artifactory/exasol-releases</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.exasol</groupId>
        <artifactId>spark-connector_2.12</artifactId>
        <version><VERSION></version>
    </dependency>
</dependencies>
```

The connector uses the [Exasol JDBC driver][exasol-jdbc] as one of its
dependencies. We add the repository identifier for the Exasol Maven Artifactory
so that the Exasol JDBC is transitively fetched.

[exasol-jdbc]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm

Please do not forget to update the `<VERSION>` placeholder with one of the
latest Spark Exasol Connector releases.

### Spark Exasol Connector as SBT Dependency

To provide the connector to your Spark Scala projects, add the following lines
to your `build.sbt` file:

```scala
resolvers ++= Seq("Exasol Releases" at "https://maven.exasol.com/artifactory/exasol-releases")

libraryDependencies += "com.exasol" % "spark-connector" %% "<VERSION>"
```

Similar to the Java dependency, we add the resolver to the Exasol Artifactory so
that the Exasol JDBC driver can be found.

### Spark Exasol Connector as Databricks Cluster Dependency

Similar to using maven, you should provide maven artifact coordinates to the
[Databricks Workspace Library][databricks-install].

[databricks-install]: https://docs.databricks.com/libraries/workspace-libraries.html#maven-libraries

<img alt="databricks-deployment" src="../images/databricks-deployment.png"/>

Go to your cluster, then to `Libraries`, and click `Install New`:

- Select Maven as a Library Source.
- In the Coordinate field, enter artifact coordinates
  `com.exasol:spark-connector_2.12:<VERSION>`. Please notice that we use the
  Scala version 2.12, change it to 2.13 if your Databricks Runtime version
  requires it.
- In the Repository field, enter the Exasol Artifactory
  `https://maven.exasol.com/artifactory/exasol-releases`.
- Click Install.

Please change the `<VERSION>` to one of the latest Spark Exasol Connector
releases.

### Spark Exasol Connector With Spark Shell

You can also integrate the Spark Exasol Connector to the Spark Shell. Provide
the artifact coordinates to the `--repositories` and `--packages` parameters:

```sh
spark-shell \
  --repositories https://maven.exasol.com/artifactory/exasol-releases \
  --packages com.exasol:spark-connector_2.12:<VERSION>
```

The `spark-shell` provides Read-Eval-Print-Loop (REPL) to interactively learn
and experiment with the API.

### Spark Exasol Connector With Spark Submit

Additionally, you can provide the connector when submitting a packaged
application into the Spark cluster.

Use the `spark-submit` command:

```sh
spark-submit \
  --master spark://spark-master-url:7077 \
  --repositories https://maven.exasol.com/artifactory/exasol-releases \
  --packages com.exasol:spark-connector_2.11:<VERSION> \
  --class com.organization.SparkApplication \
  path/to/spark_application.jar
```

The `--repositories` and `--packages` can be omitted if your Spark application
JAR already includes the connector as a dependency (e.g, jar-with-dependencies).

Like `spark-shell` and `spark-submit`, you can also use `pyspark` and `sparkR`
commands.

### Spark Exasol Connector as JAR Dependency

Please check out the
[releases](https://github.com/exasol/spark-connector/releases) page for already
assembled jar file. Each release contains a jar file with `-assembly` suffix to
that respective version.

You can also build an assembled jar from the source. This way, you use the
latest commits that may not be released yet.

Clone the repository:

```sh
git clone https://github.com/exasol/spark-connector

cd spark-connector/
```

To create an assembled jar file, run the command:

```sh
mvn package -DskipTests=true
```

The assembled jar file with the `-assembly` suffix should be located in the `target/`
folder.

If you want different version of Spark, you can use profiles `-Pspark3.1` or
`-Pspark2.4` for Spark `3.1` or `2.4` versions respectively.

For example,

```sh
mvn package -Pspark3.1 -DskipTests=true
```

Then you can use this jar file with `spark-submit`, `spark-shell` or `pyspark`
commands.

```sh
spark-shell --jars path/to/assemled-jar
```

## Configuration Options

In this section, we describe the configuration parameters that are used to
facilitate the integration between Spark and Exasol clusters.

List of required and optional parameters:

| Spark Configuration           | Configuration    | Default       | Description
| :---                          | :---             | :---          | :---
|                               | ``query``        | *<none>*      | An Exasol SQL query string to send to Exasol cluster
|                               | ``table``        | *<none>*      | A table name (with schema, e.g. schema.table) to save dataframe into
| ``spark.exasol.host``         | ``host``         | ``10.0.0.11`` | A host ip address to the **first** Exasol node
| ``spark.exasol.port``         | ``port``         | ``8563``      | A JDBC port number to connect to Exasol database
| ``spark.exasol.username``     | ``username``     | ``sys``       | A username for connecting to the Exasol database
| ``spark.exasol.password``     | ``password``     | ``exasol``    | A password for connecting to the Exasol database
| ``spark.exasol.fingerprint``  | ``fingerprint``  | ``""``        | A Exasol connection certificate fingerprint value
| ``spark.exasol.max_nodes``    | ``max_nodes``    | ``200``       | The number of data nodes in the Exasol cluster
| ``spark.exasol.batch_size``   | ``batch_size``   | ``1000``      | The number of records batched before running an execute statement when saving dataframe
| ``spark.exasol.create_table`` | ``create_table`` | ``false``     | A permission to create a table if it does not exist in the Exasol database when saving dataframe
| ``spark.exasol.drop_table``   | ``drop_table``   | ``false``     | A permission to drop the table if it exists in the Exasol database when saving dataframe
| ``spark.exasol.jdbc_options`` | ``jdbc_options`` | ``""``        | A string to specify the list of Exasol JDBC options using a ``key1=value1;key2=value2`` format

### Max Nodes

Setting the `max_nodes` value to a large number does not increase the connector
parallelism. The number of parallel connections is always limited to the
number of Exasol data nodes.

However, you can use this configuration to decrease the parallelism. This can be
helpful when debugging an issue. For example, you can set it to one and check if
the behavior changes.

### JDBC Options

The Spark Exasol Connector uses Exasol JDBC Driver to connect to the Exasol
cluster from the Spark cluster. You can use this configuration parameter to enrich
the JDBC connection.

For example, to enable debugging with a log directory:

```
.option("jdbc_options", "debug=1;logdir=/tmp/")
```

Please make sure that the `jdbc_options` value does not start or end with a
semicolon (`;`).

For more JDBC options please check the [Exasol JDBC documentation][exasol-jdbc].

### Providing Configuration Settings in DataFrame Load or Save

These are required coonfiguration parameters so that the connector can
authenticate itself with the Exasol database.

Provide the configuration options when creating a dataframe from an Exasol
query:

```scala
val exasolDF = sparkSession
  .read
  .format("exasol")
  .option("host", "10.0.0.11")
  .option("port", "8563")
  .option("username", "<USERNAME>")
  .option("password", "<PASSWORD>")
  .option("query", "<EXASOLQUERY_STRING>")
  .load()
```

Similarly, you can set these configurations when saving a dataframe to an Exasol
table.

### Providing Configuration Settings in SparkConf

You can set the configurations in the
[SparkConf](https://spark.apache.org/docs/latest/api/java/org/apache/spark/SparkConf.html)
object using the `spark.exasol.` prefix.

```scala
// Create a configuration object
val sparkConf = new SparkConf()
  .setMaster("local[*]")
  .set("spark.exasol.host", "10.0.0.11")
  .set("spark.exasol.port", "8563")
  .set("spark.exasol.username", "sys")
  .set("spark.exasol.password", "exasol")

// Provide it to the SparkSession
val sparkSession = SparkSession
  .builder()
  .config(sparkConf)
  .getOrCreate()
```

This way you can use the SparkSession with pre-configured settings when reading
or saving a dataframe from the Exasol database.

Please note that configuration values set on SparkConf have higher precedence.

### Providing Configuration Settings With `spark-submit`

Like SparkConf, you can configure the Exasol key-value settings from outside using,
for example, `--conf key=value` syntax at startup.

Provide configurations with `spark-submit`:

```sh
spark-submit \
    --master spark://spark-master-url:7077
    ...
    --conf spark.exasol.username=sys \
    --conf spark.exasol.password=exaTru3P@ss \
    path/to/spark_application.jar
```

This allows you to avoid hardcoding the credentials in your Spark applications.

Providing configurations parameters with `spark-submit` has a higher precedence
than the SparkConf configurations.

## Creating a Spark DataFrame From Exasol Query

You can query the Exasol database and load the results of the query into a Spark
dataframe.

For that specify the data source format as `"exasol"` and provide the required
configurations.

As an example we query two retail tables from the Exasol database:

```scala
val exasolQueryString = """
  SELECT s.SALES_DATE, s.MARKET_ID, sp.ARTICLE_ID, sp.AMOUNT
  FROM RETAIL.SALES s
  JOIN RETAIL.SALES_POSITIONS sp
  ON s.SALES_ID != sp.SALES_ID
  WHERE s.MARKET_ID IN (534, 678, 1019, 2277)
"""
```

Please combine your Exasol queries into a single query string and load the
result into the Spark DataFrame. This helps to reduce the additional network
overhead. At the moment, it is not possible to create many dataframes with
several separate queries.

Create a dataframe from the query result:

```scala
val df = sparkSession
  .read
  .format("exasol")
  .option("host", "10.0.0.11")
  .option("port", "8563")
  .option("username", "<USERNAME>")
  .option("password", "<PASSWORD>")
  .option("query", exasolQueryString)
  .load()
```

Now you can inspect the schema of the dataframe:

```scala
df.printSchema()
```

If you are only interested in some columns, you can select them:

```scala
df.select("MARKET_ID", "AMOUNT")
```

Run other Spark related data analytics, run transformations and aggregations on the
dataframe:

```scala
val transformedDF = df
  .filter($"MARKET_ID".isin(534, 678, 1019, 2277))

val groupedDF = transformedDF
  .groupBy("SALES_DATE")
  .agg(sum("AMOUNT"), countDistinct("ARTICLE_ID").alias("ARTICLE_CNT"))
  .sort($"SALES_DATE".desc)
```

## Saving Spark DataFrame to an Exasol Table

You can also save the Spark dataframe into an Exasol table:

```scala
groupedDF
  .write
  .mode("overwrite")
  .format("exasol")
  .option("host", "10.0.0.11")
  .option("port", "8563")
  .option("username", "<USERNAME>")
  .option("password", "<PASSWORD>")
  .option("create_table", "true")
  .option("table", "<schema>.<table>")
  .save()
```

Please notice that we create the table if it is not already available in the
Exasol database.

### Spark Save Modes

When saving a dataframe, you can provide two optional parameters: `drop_table`
and `create_table`.

If you set the `drop_table` configuration to `true`, then just before saving the
Spark dataframe, the connector drops the Exasol table if it exists.

If you set the `create_table` configuration to `true`, the connector will
eagerly try to create an Exasol table from a Spark dataframe schema before saving
the contents of the dataframe.

Depending on your use case, you can provide both of these parameters at the same
time.

Additionally, a Spark save operation takes optional `SaveMode` configurations.

| Spark Save Mode     | Description
| :---                | :---
| `"error"` (default) | If the table exists an exception is thrown. You could drop the table via the connector `drop_table` option.
| `"append"`          | If the table exists, the contents of the dataframe are appended to the existing table.
| `"overwrite"`       | If the table exists, it is truncated first and then the contents of the dataframe are saved to the table.
| `"ignore"`          | If the table exists, the save operation is skipped, and nothing is changed in the existing table.

Please keep in mind that Spark Save Modes do not use any locking mechanisms,
thus they are not atomic.

## Troubleshooting

In this section, we explain common issues and pitfalls when using Spark Exasol
Connector and provide instructions on how to solve them.

### Exasol JDBC Sub Connections

The Spark Exasol connector uses [Exasol JDBC Sub
Connections][jdbc-subconnections] underneath. The sub-connections are static by
design. You can use them after all the connections have been established.

[jdbc-subconnections]: https://community.exasol.com/t5/database-features/parallel-connections-with-jdbc/ta-p/1779

However, this causes problems in certain situations since Spark tasks are very
dynamic. Depending on the available Spark cluster resource, tasks can be
scheduled dynamically, not all at once.

In these cases, not all of the sub-connections will be consumed, and the
connector will throw an exception.

A similar issue occurs when the number of parallel connections (the number of
Exasol data nodes) is more than the Spark tasks. This can happen when the Spark
cluster does not have enough resources to schedule parallel tasks.

For instance, an Exasol cluster has three data nodes, and a Spark cluster has
only two (virtual) CPUs. In this case, the Spark cluster can only schedule two
tasks at a time. In such situations, you can decrease the JDBC sub-connections
by setting `max_nodes` parameters to a lower number.

To resolve these issues we are waiting for the [Spark Barrier Execution
Mode](https://issues.apache.org/jira/browse/SPARK-24374).

### Spark DataFrame `.show()` Action

The spark dataframe `.show()` action is one of the operations that fails because
of the problems described in the previous section.

We recommend using the `collect()` operation with combination SQL
`LIMIT` clause instead.

For example, add a limit clause to the Exasol query:

```
val exasolQueryString =
  """
    SELECT ...
    FROM   ...
    WHERE  ...
    LIMIT  10;
  """
```

Then run a collect operation to print the dataframe contents:

```
df.printSchema()
df.collect().foreach(println)
```

Please do not forget to remove the `LIMIT` clause after the exploratory phase.

### Connection was Lost and Could not be Reestablished

This error occurs because of the issues we described above.

```txt
[error] Caused by: com.exasol.jdbc.ConnectFailed: Connection was lost and could not be reestablished.  (SessionID: 1615669509094853970)
[error]         at com.exasol.jdbc.AbstractEXAConnection.reconnect(AbstractEXAConnection.java:3505)
[error]         at com.exasol.jdbc.ServerCommunication.handle(ServerCommunication.java:98)
[error]         at com.exasol.jdbc.AbstractEXAConnection.communication(AbstractEXAConnection.java:2537)
[error]         at com.exasol.jdbc.AbstractEXAConnection.communication_resultset(AbstractEXAConnection.java:2257)
[error]         at com.exasol.jdbc.AbstractEXAStatement.execute(AbstractEXAStatement.java:456)
[error]         at com.exasol.jdbc.EXAStatement.execute(EXAStatement.java:278)
[error]         at com.exasol.jdbc.AbstractEXAStatement.executeQuery(AbstractEXAStatement.java:601)
[error]         at com.exasol.spark.rdd.ExasolRDD.compute(ExasolRDD.scala:125)
[error]         at org.apache.spark.rdd.RDD.computeOrReadCheckpoint(RDD.scala:324)
[error]         at org.apache.spark.rdd.RDD.iterator(RDD.scala:288)
```

It can be mitigated by submitting a Spark application with enough resources so
that it can start parallel tasks that are more or equal to the number of
parallel Exasol connections.

Additionally, you can limit the Exasol parallel connections using the
`max_nodes` parameter. However, we do not advise to limit this value in the
production environment.

### Connection Refused

This usually occurs when the Spark connector cannot reach Exasol data nodes.
Please make sure that the Exasol data nodes are reachable on port `8563` and on
port ranges `20000-21000`.

Also, please make sure that the `host` parameter value is set to the first
Exasol data node address, for example, `10.0.0.11`.

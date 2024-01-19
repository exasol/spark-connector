# User Guide

Spark Exasol Connector allows you to create Spark DataFrames from Exasol
queries. Similarly, you can use the connector to save the Spark DataFrames as
Exasol tables.

## Table of Contents

- [Getting Started](#getting-started)
- [Versioning](#versioning)
- [Format](#format)
- [Using as Dependency](#using-as-dependency)
- [AWS Authentication](#aws-authentication)
- [Configuration Parameters](#configuration-options)
- [Creating a Spark DataFrame From Exasol Query](#creating-a-spark-dataframe-from-exasol-query)
- [Saving Spark DataFrame to an Exasol Table](#saving-spark-dataframe-to-an-exasol-table)
- [Troubleshooting](#troubleshooting)

## Getting Started

The Spark Exasol connector has two variants, one for using with Exasol `JDBC` sub-connections and one for using with AWS `S3` as intermediate storage.

We recommend to use the connector with `S3` storage layer, because this version improves several shortcomings of `JDBC` variant. The JDBC sub-connections version are not reliably working when Spark uses dynamic resource scheduling. In other words, when there are limited resource on Spark clusters, using S3 version of connector is better.

Depending on the variant you are using, some usage requirements may change. For example, for JDBC the `host` parameter should be the first datanode address.

### JDBC

When using the connector with JDBC, please make sure that there is an access connection between the two clusters via internal network addresses. The Spark Exasol Connector makes parallel connections to the Exasol data nodes and assumes that data nodes have sequential IPv4 addresses. For example, the first data node has an address `10.0.0.11`, the second one is assigned `10.0.0.12`, and so on.

Additionally, please make sure that the Exasol nodes are reachable from the Spark cluster on the JDBC port (`8563`) and port range `20000-21000`. The port range is used for the parallel connections from the Spark tasks to Exasol data nodes.

### S3

When using with S3 intermediate storage please make sure that there is access to an S3 bucket. In details, AWS Authentication is described in the [corresponding section of this document](#aws-authentication).

## Versioning

The Spark Exasol connector is released for specific Spark versions.

Each version has parts for Scala version, connector version and target Spark runtime version.

For example, `spark-connector-s3_2.13:2.0.0-spark-3.4.1` artifact shows that it is for S3 variant, released with Scala 2.13 version, connector release version is `2.0.0` and it is released for Spark `3.4.1` runtime.

## Format

For creating a Spark dataframe, you should provide the `format`.

For example, when reading from a source `spark.read.format(<FORMAT>)...`.

Depending on the connector variant, use the following formats:

- JDBC &mdash; `"exasol"`
- S3 &mdash; `"exasol-s3"`

## Using as Dependency

We publish the Spark Exasol Connector to the Maven Central Repository. With that, you could include it as a dependency in your Spark applications.

Here we show `Maven` dependency as an example, but you can find other ways of using the artifact on the [Maven Central Release](https://mvnrepository.com/artifact/com.exasol/spark-connector) page.

### Using as Maven Dependency

You can provide the connector as a dependency to your Spark Java applications.

#### S3

```xml
<dependencies>
    <dependency>
        <groupId>com.exasol</groupId>
        <artifactId>spark-connector-s3_2.13</artifactId>
        <version><VERSION></version>
    </dependency>
</dependencies>
```

#### JDBC

```xml
<dependencies>
    <dependency>
        <groupId>com.exasol</groupId>
        <artifactId>spark-connector-jdbc_2.13</artifactId>
        <version><VERSION></version>
    </dependency>
</dependencies>
```

Please do not forget to update the `<VERSION>` placeholder with the latest Spark Exasol Connector releases.

### Using as Databricks Cluster Dependency

You can upload the assembled jar file or provide maven artifact coordinates to the [Databricks Workspace Library](https://docs.databricks.com/libraries/workspace-libraries.html#maven-libraries).

- Go to Azure &rarr; Databricks &rarr; Workspace &rarr; Clusters
- Go to Libraries &rarr; and click Install New
- Select Maven as a `Library Source`
- In the `Coordinates` field, enter artifact coordinates `com.exasol:spark-connector-s3_2.13:<VERSION>`. Please note that we use the Scala version 2.13. Please refer to releases if you require Scala version 2.12. This should match the Spark cluster runtime Scala version.
- Click `Install`

Similar to above steps you could also upload the assembled jar file.

- Select Upload as `Library Source`
- Jar as a `Library Type`
- Drop the **assembled** jar file with `-assembly` suffix
- Click to `Install`

### Using With Spark Shell

You can also integrate the Spark Exasol Connector to the Spark Shell. Provide the artifact coordinates using the `--packages` parameter:

```sh
spark-shell --packages com.exasol:spark-connector-s3_2.13:<VERSION>
```

The `spark-shell` provides a Read-Eval-Print-Loop (REPL) to interactively learn and experiment with the API.

### Using With Spark Submit

Additionally, you can provide the connector when submitting a packaged application into the Spark cluster.

Use the `spark-submit` command:

```sh
spark-submit \
  --master spark://spark-master-url:7077 \
  --packages com.exasol:spark-connector-jdbc_2.13:<VERSION> \
  --class com.example.SparkApplication \
  path/to/spark_application.jar
```

The `--packages` parameter can be omitted if your Spark application JAR already includes the connector as a dependency (e.g, `jar-with-dependencies`).

Like `spark-shell` and `spark-submit`, you can also use `pyspark` and `sparkR` commands.

### Using as JAR Dependency

Please check out the [releases](https://github.com/exasol/spark-connector/releases) page for already assembled jar file. Each release contains a jar file with `-assembly` suffix to that respective version. This jar file contains all required dependencies as a "shaded jar".

Then you can use this jar file with `spark-submit`, `spark-shell` or `pyspark` commands.

For example, S3 variant with version `2.0.0-spark-3.4.1`:

```sh
spark-shell --jars spark-connector-s3_2.13-2.0.0-spark-3.4.1-assembly.jar
```

## AWS Authentication

If S3 intermediate storage is used, proper AWS Authentication parameters has to be provided:

* Spark has to be able to read and write into S3 (to export and import dataframe's data);
* Database has to be able to read and write into S3 (to perform `IMPORT` and `EXPORT` statements).

There are several ways to provide AWS credentials and concrete method depends on configuration of your cloud infrastructure. Here we cover main scenarios and configuration options you can tweak.

### Credential Providers

The first option is `awsCredentialsProvider` with which you can specify list of ways credentials are retrieved from your spark environment. This parameter is not required and if not specified, the default list of credentials providers is being used. At the moment of writing, this list includes the following credentials providers:

* `org.apache.hadoop.fs.s3a.SimpleAWSCredentialsProvider`: credentials are explicitly set with options `awsAccessKeyId` and `awsSecretAccessKey`.
* `com.amazonaws.auth.EnvironmentVariableCredentialsProvider`: credentials are retrieved from environment variables `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` (of Spark process).
* `com.amazonaws.auth.InstanceProfileCredentialsProvider`: credentials are retrieved from EC2 instance IAM role.

There are many other credential providers in Amazon Hadoop library and 3rd party libraries. If you need to change default behaviour, you can set `awsCredentialsProvider` option to list of comma-separated class names.

In details you can read about Credentials Providers in [this document](https://hadoop.apache.org/docs/stable/hadoop-aws/tools/hadoop-aws/index.html#Authenticating_with_S3).

### Explicitly provided credentials
If you want to specify Access Key ID and Secret Access Key explicitly you can set options `awsAccessKeyId` and `awsSecretAccessKey`.

Alternatively, you can set environment variables `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` in your Spark cluster configuration.

In both cases, credentials will be used for S3 operations from Spark's side and forwarded to the database in `IMPORT` and `EXPORT` commands (as `USER 'key' IDENTIFIED BY 'secret_key'` parameters).

### Using EC2 Instance Profile
In AWS you can attach permissions to the role associated with EC2 instance your Spark cluster is working. In that case, S3 credentials are extracted from instance profile automatically by `InstanceProfileCredentialsProvider`, so you don't need to pass any options.

In this scenario, no credentials are being put in `IMPORT` and `EXPORT` DB commands, so you need to make sure that DB has proper access to S3 bucket you're using for intermediate storage. 

If database is running in EC2, it is possible to use EC2 Instance Profiles, but it has to be enabled explicitly, as described in [this document](https://exasol.my.site.com/s/article/Changelog-content-15155?language=en_US).

## Configuration Options

In this section, we describe the common configuration parameters that are used for both JDBC and S3 variants to facilitate the integration between Spark and Exasol clusters.

List of common required and optional parameters:

| Configuration  | Default     | Description                                                                                      |
| :------------- | :---------- | :----------------------------------------------------------------------------------------------- |
| `query`        | _<none>_    | An Exasol SQL query string to send to Exasol cluster                                             |
| `table`        | _<none>_    | A table name (with schema, e.g. schema.table) to save dataframe into                             |
| `host`         | `10.0.0.11` | A host ip address to the Exasol node                                                             |
| `port`         | `8563`      | A JDBC port number to connect to Exasol database                                                 |
| `username`     | `sys`       | A username for connecting to the Exasol database                                                 |
| `password`     | `exasol`    | A password for connecting to the Exasol database                                                 |
| `fingerprint`  | `""`        | A Exasol connection certificate fingerprint value                                                |
| `jdbc_options` | `""`        | A string to specify the list of Exasol JDBC options using format `key1=value1;key2=value2`       |

The `query` parameter is required when you are reading data from Exasol database. Likewise, the `table` parameter is required when you are writing to an Exasol table.

### JDBC Options

The Spark Exasol Connector uses Exasol JDBC Driver to connect to the Exasol cluster from the Spark cluster. You can use this configuration parameter to enrich the JDBC connection.

For example, to enable debugging with a log directory:

```
.option("jdbc_options", "debug=1;logdir=/tmp/")
```

Please make sure that the `jdbc_options` value does not start or end with a semicolon (`;`).

For more JDBC options please check the [Exasol JDBC documentation](https://docs.exasol.com/db/latest/connect_exasol/drivers/jdbc.htm).

### JDBC Related Configuration Options

When using the `JDBC` variant you can additionally set these parameters:

| Configuration  | Default     | Description                                                                                      |
| :------------- | :---------- | :----------------------------------------------------------------------------------------------- |
| `max_nodes`    | `200`       | The number of data nodes in the Exasol cluster                                                   |
| `batch_size`   | `1000`      | The number of records batched before running an execute statement when saving dataframe          |
| `create_table` | `false`     | A permission to create a table if it does not exist in the Exasol database when saving dataframe |
| `drop_table`   | `false`     | A permission to drop the table if it exists in the Exasol database when saving dataframe         |

When saving a dataframe, you can provide two optional parameters: `drop_table` and `create_table`.

If you set the `drop_table` configuration to `true`, then just before saving the Spark dataframe, the connector drops the Exasol table if it exists.

If you set the `create_table` configuration to `true`, the connector will eagerly try to create an Exasol table from a Spark dataframe schema before saving the contents of the dataframe. Depending on your use case, you can provide both of these parameters at the same time.

#### Max Nodes

Setting the `max_nodes` value to a large number does not increase the connector parallelism. The number of parallel connections is always limited to the number of Exasol data nodes.

However, you can use this configuration to decrease the parallelism. This can be helpful when debugging an issue. For example, you can set it to `1` and check if the behavior changes.

### S3 Related Configuration Options

When using the `S3` variant of the connector you should provide the following additional required or optional parameters.

| Parameter             | Default            | Required | Description                                                         |
|-----------------------|:------------------:|:--------:|-------------------------------------------------------------------- |
| `s3Bucket`            |                    |    ✓     | A bucket name for intermediate storage                              |
| `awsAccessKeyId`      |                    |          | AWS Access Key for accessing bucket                                 |
| `awsSecretAccessKey`  |                    |          | AWS Secret Key for accessing bucket                                 |
| `awsCredentialsProvider` | [default providers](https://hadoop.apache.org/docs/stable/hadoop-aws/tools/hadoop-aws/index.html#Authenticating_with_S3) | | List of classes used to extract credentials information from the runtime environment. |
| `numPartitions`       | `8`                |          | Number of partitions that will match number of files in `S3` bucket |
| `awsRegion`           | `us-east-1`        |          | AWS Region for provided bucket                                      |
| `awsEndpointOverride` | (default endpoint) |          | AWS S3 Endpoint for bucket, set this value for custom endpoints     |
| `s3PathStyleAccess`   | `false`            |          | Path style access for bucket, set this value for custom S3 buckets  |
| `useSsl`              | `true`             |          | Enable HTTPS client access for S3 bucket                            |

### Providing Configuration Settings in DataFrame Load or Save

These are required configuration parameters so that the connector can authenticate itself with the Exasol database.

Provide the configuration options when creating a dataframe from an Exasol query:

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

Similarly, you can set these configurations when saving a dataframe to an Exasol table.

### Providing Configuration Settings in SparkConf

You can set the configurations in the [SparkConf](https://spark.apache.org/docs/latest/api/java/org/apache/spark/SparkConf.html) object using the `spark.exasol.` prefix.

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

This way you can use the SparkSession with previously configured settings when reading or saving a dataframe from the Exasol database.

Please note that configuration values set on SparkConf have higher precedence.

### Providing Configuration Settings With `spark-submit`

Like SparkConf, you can configure the Exasol key-value settings from outside using, for example, `--conf key=value` syntax at startup.

Provide configurations with `spark-submit`:

```sh
spark-submit \
    --master spark://spark-master-url:7077
    ...
    --conf spark.exasol.username=sys \
    --conf spark.exasol.password=exaTru3P@ss \
    path/to/spark_application.jar
```

This allows you to avoid hard-coding the credentials in your Spark applications.

Providing configurations parameters with `spark-submit` has a higher precedence than the SparkConf configurations.

## Creating a Spark DataFrame From Exasol Query

You can query the Exasol database and load the results of the query into a Spark dataframe.

For that specify the data source format and provide the required configurations.

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

Please combine your Exasol queries into a single query and load the result into the Spark DataFrame. This helps to reduce the additional network overhead.

Create a dataframe from the query result using JDBC variant:

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

Using S3 variant:

```scala
val df = spark
  .read
  .format("exasol-s3")
  .option("host", "10.10.0.2")
  .option("port", "8563")
  .option("username", "<USERNAME>")
  .option("password", "<PASSWORD>")
  .option("query", exasolQueryString)
  .option("awsAccessKeyId", "<ACCESSKEY>")
  .option("awsSecretAccessKey", "<SECRETKEY>")
  .option("s3Bucket", "spark-s3-bucket")
  .option("awsRegion", "eu-central-1")
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

Run other Spark related data analytic queries, run transformations and aggregations on the dataframe:

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
  .option("table", "<schema>.<table>")
  .save()
```

Please notice that we create the table if it is not already available in the Exasol database.

### Spark Save Modes

Additionally, a Spark save operation takes optional `SaveMode` configurations.

| Spark Save Mode     | Description                                                                                                 |
| :------------------ | :---------------------------------------------------------------------------------------------------------- |
| `"error"` (default) | If the table exists an exception is thrown. You could drop the table via the connector `drop_table` option. |
| `"append"`          | If the table exists, the contents of the dataframe are appended to the existing table.                      |
| `"overwrite"`       | If the table exists, it is truncated first and then the contents of the dataframe are saved to the table.   |
| `"ignore"`          | If the table exists, the save operation is skipped, and nothing is changed in the existing table.           |

Please keep in mind that Spark Save Modes do not use any locking mechanisms, thus they are not atomic.

## Troubleshooting

In this section, we explain common issues and pitfalls when using Spark Exasol Connector and provide instructions on how to solve them.

### Exasol JDBC Sub Connections

The Spark Exasol connector uses [Exasol JDBC Sub Connections](https://community.exasol.com/t5/database-features/parallel-connections-with-jdbc/ta-p/1779) underneath. The sub-connections are static by design. You can use them after all the connections have been established.

However, this causes problems in certain situations since Spark tasks are very dynamic. Depending on the available Spark cluster resource, tasks can be scheduled dynamically, not all at once. In these cases, not all of the sub-connections will be consumed, and the connector will throw an exception. A similar issue occurs when the number of parallel connections (the number of Exasol data nodes) is more than the Spark tasks. This can happen when the Spark cluster does not have enough resources to schedule parallel tasks.

For instance, an Exasol cluster has three data nodes, and a Spark cluster has only two (virtual) CPUs. In this case, the Spark cluster can only schedule two tasks at a time. In such situations, you can decrease the JDBC sub-connections by setting `max_nodes` parameters to a lower number.

### Spark DataFrame `.show()` Action

The spark dataframe `.show()` action is one of the operations that fails because of the problems described in the previous section.

We recommend using the `collect()` operation in combination with the SQL `LIMIT` clause instead.

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

It can be mitigated by submitting a Spark application with enough resources so that it can start parallel tasks that are more or equal to the number of parallel Exasol connections.

Additionally, you can limit the Exasol parallel connections using the `max_nodes` parameter. However, we do not advise to limit this value in the production environment.

### Connection Refused

This usually occurs when the Spark connector cannot reach Exasol data nodes. Please make sure that the Exasol data nodes are reachable on port `8563` and on port ranges `20000-21000`.

Also, please make sure that the `host` parameter value is set to the first Exasol data node address, for example, `10.0.0.11`.

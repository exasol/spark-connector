# Developer Guide

## Running Tests

The project must be built with Java 17. Running the build with Java 21 will cause tests to fail with the following exception in Spark:

```
  Cause: java.lang.NoSuchMethodException: java.nio.DirectByteBuffer.<init>(long,int)
  at java.base/java.lang.Class.getConstructor0(Class.java:3761)
  at java.base/java.lang.Class.getDeclaredConstructor(Class.java:2930)
  at org.apache.spark.unsafe.Platform.<clinit>(Platform.java:71)
  at org.apache.spark.unsafe.array.ByteArrayMethods.<clinit>(ByteArrayMethods.java:52)
  at org.apache.spark.memory.MemoryManager.defaultPageSizeBytes$lzycompute(MemoryManager.scala:261)
  at org.apache.spark.memory.MemoryManager.defaultPageSizeBytes(MemoryManager.scala:251)
  at org.apache.spark.memory.MemoryManager.$anonfun$pageSizeBytes$1(MemoryManager.scala:270)
  at scala.runtime.java8.JFunction0$mcJ$sp.apply(JFunction0$mcJ$sp.scala:17)
  at scala.Option.getOrElse(Option.scala:201)
  at org.apache.spark.memory.MemoryManager.<init>(MemoryManager.scala:270)
```

If your default Java version is newer, you can run the build by setting the `JAVA_HOME` environment variable to a JDK 17:

```sh
JAVA_HOME=$JAVA17_HOME mvn test
```

## S3 Write Path Validation

When using S3 storage as intermediate layer, we generate a S3 bucket path for intermediate data. The generated path is checked that it is empty before writing data.

The path layout:

```
userProvidedS3Bucket/
└── <UUID>-<SparkApplicationId>/
    └── <SparkQueryId>/
```

The generated intermediate write path `<UUID>-<SparkApplicationId>/<SparkQueryId>/` is validated that it is empty before write. And it is cleaned up after the write query finishes.

## S3 Staging Commit Process

The Spark job that writes data to Exasol uses an AWS S3 bucket as intermediate storage. In this process, the `ExasolS3Table` API implementation uses Spark [`CSVTable`](https://github.com/apache/spark/blob/master/sql/core/src/main/scala/org/apache/spark/sql/execution/datasources/v2/csv/CSVTable.scala) writer to create files in S3.

The write process continues as following:

1. We ask Spark's `CSVTable` to commit data into S3 bucket
1. We commit to import this data into Exasol database using Exasol's `CSV` loader
1. And finally we ask our `ExasolS3Table` API implementation to commit the write process

If any failure occurs, each step will trigger the `abort` method and S3 bucket locations will be cleaned up. If job finishes successfully, the Spark job end listener will trigger the cleanup process.

## S3 Maximum Number of Files

For the write Spark jobs, we allow maximum of `1000` CSV files to be written as intermediate data into S3 bucket. The main reason for this is that S3 SDK `listObjects` command returns up to 1000 objects from a bucket path per each request.

Even though we could improve it to list more objects from S3 bucket with multiple requests, we wanted to keep this threshold for now.

## Integration Tests

The integration tests are run using [Docker](https://www.docker.com) and [exasol-testcontainers](https://github.com/exasol/exasol-testcontainers/)

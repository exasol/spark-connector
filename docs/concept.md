# Spark Exasol Connector

Initial concepts on how to integrate Exasol and Spark will be documented here.

## General API

The api should be similar to all other data integration (sources) frameworks.

```scala
df = sparkSession
     .read
     .format("com.exasol.spark") // or .format("exasol")
     .options("table", "myschema.mytable") // a table should be specified
     .load()

// continue using df as regular Spark dataframe
df = df
     .filter..
     .where..
     ...
```

## Options

Other than `table`, maybe a query can be provided?

```scala
df = sparkSession
     .read
     .format("exasol")
     .options("query", "SELECT ...") // an exasol query syntax
     .load()

df = df
     .join(...
     ...
```

Another crucial parameters are url, username and password, so they should be
definitely required in options.

```scala
df = sparkSession
     .read
     .format("exasol")
     .options("url", "connectionStr")
     .options("username", "myusername")
     .options("password", "mYpassworD")
     .options("query", "SELECT ...") // an exasol query syntax
     .load()
```

## Predicate Pushdown and Column Pruning

The idea is even if user only reads an Exasol table, the Spark Query Optimizer
(Catalyst) knows that we only need some columns and there are filters in overal
query. Using these information, we should only send to Exasol a query string
with required columns and filters.

For example,

```scala
exaDF = sparkSession
     .read
     .format("exasol")
     .options("username", "myusername")
     .options("password", "mYpassworD")
     .options("table", "country_schema.country_population")
     .load()

val resultDF = exaDF
               .join(otherDF, Seq("country_name"))
               .filter($"population" > 100 OR $"population" < 20)
               .filter($"country_name".startWith("A"))
               .select("country_name")

resultDF.show()
```

with above Spark code, even though user specified to read a Exasol table, the
connector should send the correct query to Exasol with selects and filters.

For example a query sent to Exasol can be something:

```sql
SELECT country_name
FROM   country_schema.country_population
WHERE  population > 100 OR population < 20) AND country_name RLIKE '^A.*';
```

Then Spark can join only needed Exasol data with other dataframe.

Using **partitioned** fields in queries would help both Exasol and Spark.

## Architecture

Top down description of architecture.

### ExasolDataSource

An initial entry and proxy between Spark and Exasol.

Extends:

- RelationProvider for read api from Exasol

```scala
override def createRelation(
    sqlContext: SQLContext,
    parameters: Map[String, String]
): BaseRelation = {
  // ...
  // returns ExasolRelation
}
```

- CreatableRelationProvider for save api to Exasol

```scala
override def createRelation(
    sqlContext: SQLContext,
    mode: SaveMode,
    parameters: Map[String, String],
    data: DataFrame
): BaseRelation = {
  // ...
  // returns ExasolRelation
}
```

### ExasolRelation

Extends `BaseRelation` and returned from ExasolDataSource methods.

```scala
override def buildScan(
    requiredColumns: Array[String],
    filters: Array[Filter]
): RDD[Row] = {
  // ...
  // returns ExasolRDD
}
```

### ExasolRDD

- Number of partitions

```scala
override def getPartitions: Array[Partition] = {
  // ...
  // should return list of ExasolRDDPartition-s where each partition holds
  // information (token, port or host) about JDBC sub-connection
}
```

- Compute method

```scala
override def compute(
    partition: Partition,
    context: TaskContext
): Iterator[T] = new NextIterator[T] {
  context.addTaskCompletionListener(context => closeIfNeeded())
  // ...
  // We are now in a isolated process (Spark Task) somewhere in cluster.
  // Given the sub-connection information in partition, do an actual JDBC call
  // and return an iterator over the results.
  // Transformation of Exasol types to Spark types can happen here.
```

### ExasolRDDPartition

A simple case class thant extends Spark `Partition` and holds information (host,
port or token) needed when performing JDBC sub-connection.

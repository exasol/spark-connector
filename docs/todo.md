# TODOS

## Add a user provided `schema`

It should be possible that user provides an `schema` with options
`.option("schema", schemaSparkStruct)` when reading. Then we will not have to
infer the schema of query by calling a jdbc call to Exasol.

## Setup Travil CI

This should also include a build matrix. Maybe with different Spark and Scala
versions.

## Merge configs from SparkConf

We should it possible to define the configurations such as Exasol `host` or
`username` etc in SparkConf.

## Enable column selection

## Enable predicate pushdown

## Add publishing

We need to decide where to publish the jars. Maven central or Exasol
Artifactory. Discuss with Andre.

## Update README with concise documentation

## Add tests for several components

Add test for type conversions. There might be some issues with Exasol types not
correctly converted to Spark types.

## Test in distributed clusters

Test with clustered setup with Exasol and Spark. Might be easier in AWS / EMR.
This might show possible serialization errors.

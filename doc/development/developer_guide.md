# Developer Guide

## S3 Write Path Validation

When using S3 storage as intermediate layer, we generate a S3 bucket path for intermediate data. The generated path is checked that it is empty before writing data.

The path layout:

```
userProvidedS3Bucket/
└── uuid-sparkApplicationId
    └── queryId
```

The generated intermediate write path `<UUID>-<SparkApplicationId>/<SparkQueryId>/` if validated that it is empty before write. And it is cleaned up after the write query finishes.

## Integration Tests

The integration tests are run using [Docker](https://www.docker.com) and [exasol-testcontainers](https://github.com/exasol/exasol-testcontainers/)

## Release

Currently [release-droid](https://github.com/exasol/release-droid) has some troubles releasing the spark connector.

* **Validation**: When using the local folder with `release-droid -l .` then validation fails with error message `NumberFormatException for input string: "v0"`.<br />
Please validate spark-connector without option `-l .`.<br />
See also [release-droid/issue/245](https://github.com/exasol/release-droid/issues/245).
* **Language Java**: Although spark-connector contains scala code as well, there also is a pom file, though.<br />
In order to publish spark-connector to maven central using language "Java" is correct.
* For **releasing** spark-connector you should use
```
java -jar path/to/release-droid-*.jar" -n spark-connector -lg Java -g release
```

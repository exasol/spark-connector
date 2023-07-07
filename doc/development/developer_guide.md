# Developer Guide

## S3 Write Path Validation

When using S3 storage as intermediate layer, we generate a S3 bucket path for intermediate data. The generated path is checked that it is empty before writing data.

The path layout:

```
userProvidedS3Bucket/
└── <UUID>-<SparkApplicationId>/
    └── <SparkQueryId>/
```

The generated intermediate write path `<UUID>-<SparkApplicationId>/<SparkQueryId>/` is validated that it is empty before write. And it is cleaned up after the write query finishes.

## Integration Tests

The integration tests are run using [Docker](https://www.docker.com) and [exasol-testcontainers](https://github.com/exasol/exasol-testcontainers/)

# Spark Connector 2.1.1, released 2023-08-29

Code name: JDBC connection cache drop, `magic` s3 hadoop committer

## Summary

S3 connector uses faster hadoop committer, fix possible race condition in RDD operations

## Features

* #163: Switched to `magic` hadoop committer for intermediate s3 files
* #200: Dropped JDBC connection cache in `ExasolConnectionManager`

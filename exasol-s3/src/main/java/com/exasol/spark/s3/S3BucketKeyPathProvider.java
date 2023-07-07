package com.exasol.spark.s3;

/**
 * An interface for creating Spark job write {@code S3} bucket folders for writing intermediate data.
 */
public interface S3BucketKeyPathProvider {

    /**
     * Returns an {@code S3} bucket key path for writing intermediate data.
     *
     * @param queryId Spark query identifier that started the write job
     * return {@code S3} bucket key path for intermediate data
     */
    public String getS3BucketKeyForWriteLocation(final String queryId);

}


package com.exasol.spark.s3;

import java.util.UUID;

/**
 * An implementation of {@link S3BucketKeyPathProvider} that uses {@code UUID} prefixes for intermediate write path.
 *
 * It creates {@code S3} write path as following {@code <randomUUID>-<sparkApplicationId>/<sparkQueryId>}.
 */
public class UUIDS3BucketKeyPathProvider implements S3BucketKeyPathProvider {
    private final String applicationId;

    /**
     * Creates a new instance of {@link UUIDS3BucketKeyPathProvider}.
     *
     * @param applicationId Spark application identifier
     */
    public UUIDS3BucketKeyPathProvider(final String applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public String getS3BucketKeyForWriteLocation(final String queryId) {
        final StringBuilder builder = new StringBuilder();
        builder.append(UUID.randomUUID()).append("-").append(this.applicationId).append("/").append(queryId);
        return builder.toString();
    }

}

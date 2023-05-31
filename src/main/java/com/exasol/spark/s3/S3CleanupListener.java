package com.exasol.spark.s3;

import java.util.logging.Logger;

import org.apache.spark.scheduler.SparkListener;
import org.apache.spark.scheduler.SparkListenerJobEnd;

/**
 * A {@link SparkListener} class that cleans up {@code S3} intermediate location at the end of job run.
 */
public final class S3CleanupListener extends SparkListener {
    private static final Logger LOGGER = Logger.getLogger(S3CleanupListener.class.getName());
    private final ExasolOptions options;
    private final String bucketKey;

    /**
     * Creates an instance of {@link S3CleanupListener}.
     *
     * @param options   user provided options
     * @param bucketKey bucketKey inside the user provided bucket
     */
    public S3CleanupListener(final ExasolOptions options, final String bucketKey) {
        this.options = options;
        this.bucketKey = bucketKey;
    }

    @Override
    public void onJobEnd(final SparkListenerJobEnd jobEnd) {
        LOGGER.info(() -> "Cleaning up the bucket '" + this.options.getS3Bucket() + "' with key '" + this.bucketKey
                + "' in job '" + jobEnd.jobId() + "'.");
        deleteObjects();
        super.onJobEnd(jobEnd);
    }

    private void deleteObjects() {
        try (final S3FileSystem s3FileSystem = S3FileSystem.fromOptions(this.options)) {
            s3FileSystem.deleteKeys(this.options.getS3Bucket(), this.bucketKey);
        }
    }

}

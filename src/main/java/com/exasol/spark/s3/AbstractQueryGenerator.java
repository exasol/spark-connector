package com.exasol.spark.s3;

import static com.exasol.spark.s3.Constants.*;

/**
 * An common {@code CSV} query generator class.
 *
 * A generator for Exasol {@code IMPORT} or {@code EXPORT} queries that access {@code CSV} files in intermediate storage
 * systems.
 *
 * @see <a href="https://docs.exasol.com/db/latest/sql/import.htm">Exasol Import</a>
 * @see <a href="https://docs.exasol.com/db/latest/sql/export.htm">Exasol Export</a>
 */
public abstract class AbstractQueryGenerator {
    /** An {@link ExasolOptions} options. */
    protected final ExasolOptions options;

    /**
     * Creates a new instance of {@link AbstractQueryGenerator}.
     *
     * @param options user provided options
     */
    public AbstractQueryGenerator(final ExasolOptions options) {
        this.options = options;
    }

    /**
     * Creates an {@code IDENTIFIED BY} part of a query.
     *
     * @return identifiedBy part of a query
     */
    public String getIdentifier() {
        final String awsAccessKeyId = this.options.get(AWS_ACCESS_KEY_ID);
        final String awsSecretAccessKey = this.options.get(AWS_SECRET_ACCESS_KEY);
        return "AT '" + escapeStringLiteral(getBucketURL()) + "'\nUSER '" + escapeStringLiteral(awsAccessKeyId)
                + "' IDENTIFIED BY '" + escapeStringLiteral(awsSecretAccessKey) + "'\n";
    }

    private String escapeStringLiteral(final String input) {
        return input.replace("'", "''");
    }

    private String getBucketURL() {
        return "https://" + this.options.getS3Bucket() + ".s3." + getS3Endpoint();
    }

    private String getS3Endpoint() {
        if (this.options.containsKey(S3_ENDPOINT_OVERRIDE)) {
            return replaceInCITests(this.options.get(S3_ENDPOINT_OVERRIDE));
        } else {
            return "amazonaws.com";
        }
    }

    private String replaceInCITests(final String endpoint) {
        if (this.options.hasEnabled(CI_ENABLED)) {
            return endpoint.replace("localhost", "amazonaws.com");
        } else {
            return endpoint;
        }
    }

}

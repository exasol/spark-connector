package com.exasol.spark.s3;

/**
 * An common {@code CSV} query generator class.
 *
 * A generator for Exasol {@code IMPORT} or {@code EXPORT} queries that access {@code CSV} files in intermediate storage
 * systems.
 *
 * @see <a href="https://docs.exasol.com/db/latest/sql/import.htm">Exasol Import</a>
 * @see <a href="https://docs.exasol.com/db/latest/sql/export.htm">Exasol Export</a>
 */
public abstract class AbstractImportExportQueryGenerator {
    private static final String DEFAULT_S3_ENDPOINT = "amazonaws.com";

    /** Spark options for scenarios involving an Exasol database */
    protected final ExasolOptions options;

    /**
     * Creates a new instance of {@link AbstractImportExportQueryGenerator}.
     *
     * @param options user provided options
     */
    public AbstractImportExportQueryGenerator(final ExasolOptions options) {
        this.options = options;
    }

    /**
     * Creates an {@code IDENTIFIED BY} part of a query.
     *
     * @return identifiedBy part of a query
     */
    public String getIdentifier() {
        final String awsAccessKeyId = this.options.get(Option.AWS_ACCESS_KEY_ID.key());
        final String awsSecretAccessKey = this.options.get(Option.AWS_SECRET_ACCESS_KEY.key());
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
        String override =  this.options.get(Option.S3_ENDPOINT_OVERRIDE.key());
        if (override == null) {
            return DEFAULT_S3_ENDPOINT;
        }
        if (this.options.hasEnabled(Option.REPLACE_LOCALHOST_BY_DEFAULT_S3_ENDPOINT.key())) {
            return override.replace("localhost", DEFAULT_S3_ENDPOINT);
        }
        return override;
    }

}

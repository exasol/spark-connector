package com.exasol.spark.s3;

/**
 * Each {@link Option} is a supported entry for {@link ExasolOptions}.
 *
 * Using {@link Option#key()} you can check if the option is set and retrieve its value.
 */
public enum Option {

    TABLE("TABLE", "Parameter name for Exasol table."), //
    QUERY("QUERY", "Parameter name for Exasol query."), //
    JDBC_URL("JDBC_URL", "Parameter name for Exasol database connection JDBC URL."), //
    USERNAME("USERNAME", "Parameter name for Exasol database username."), //
    PASSWORD("PASSWORD", "Parameter name for Exasol database password."), //
    NUMBER_OF_PARTITIONS("numPartitions", "Parameter name for setting number of Spark job partitions."), //
    CI_ENABLED("exasol-ci", "Boolean parameter name to indicate local and CI environment."), //
    //
    AWS_ACCESS_KEY_ID("awsAccessKeyId", "AWS access key parameter name."), //
    AWS_SECRET_ACCESS_KEY("awsSecretAccessKey", "AWS secret key parameter name."), //
    AWS_SESSION_TOKEN("awsSessionToken", "AWS session token parameter name."), //
    AWS_REGION("awsRegion", "AWS region parameter name."), //
    DEFAULT_AWS_REGION("us-east-1", "Default AWS region value."), //
    AWS_USE_SSL("useSsl", "Boolean parameter to enable SSL."), //
    S3_BUCKET("s3Bucket", "AWS bucket name parameter name."), //
    S3_ENDPOINT_OVERRIDE("awsEndpointOverride", "AWS endpoint override parameter name."), //
    S3_PATH_STYLE_ACCESS("s3PathStyleAccess", "Boolean parameter name to enable S3 path style access."); //

    private final String key;
    // intentionally unused. Designed for documentation.
    @SuppressWarnings("unused")
    private final String comment;

    private Option(final String key, final String comment) {
        this.key = key;
        this.comment = comment;
    }

    /**
     * @return key of the current option
     */
    public String key() {
        return this.key;
    }
}

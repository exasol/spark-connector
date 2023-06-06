package com.exasol.spark.s3;

/**
 * Each {@link Option} is a supported entry for {@link ExasolOptions}.
 *
 * Using {@link Option#key()} you can check if the option is set and retrieve its value.
 */
public enum Option {

    /** Name for Exasol table. */
    TABLE("TABLE", "Parameter name for Exasol table."), //

    /** Name for Exasol query. */
    QUERY("QUERY", "Parameter name for Exasol query."), //

    /** Exasol database connection JDBC URL. */
    JDBC_URL("JDBC_URL", "Parameter name for Exasol database connection JDBC URL."), //

    /** Exasol database username. */
    USERNAME("USERNAME", "Parameter name for Exasol database username."), //

    /** Exasol database password. */
    PASSWORD("PASSWORD", "Parameter name for Exasol database password."), //

    /** Number of Spark job partitions. */
    NUMBER_OF_PARTITIONS("numPartitions", "Parameter name for setting number of Spark job partitions."), //

    /** Default number of Spark job partitions. */
    DEFAULT_NUMBER_OF_PARTITIONS("8", "Default number for Spark job partitions."), //

    /** Maximum allowed number of Spark job partitions. */
    MAX_ALLOWED_NUMBER_OF_PARTITIONS("1000", "Maximum allowed number for Spark job partitions."), //

    /** Boolean parameter name to indicate local and CI environment. */
    REPLACE_LOCALHOST_BY_DEFAULT_S3_ENDPOINT("replaceLocalhostByDefaultS3Endpoint",
            "Boolean parameter name to indicate local and CI environment."), //

    /**
     * Parameter name for intermediate data location for writing.
     *
     * It is internal parameter, not required to be set by users.
     */
    INTERMEDIATE_DATA_PATH("PATH", "Parameter name for location for writing, to store intermediate data."), //

    /**
     * Parameter name for S3 bucket key inside intermediate data path.
     *
     * It is internal parameter, not required to be set by users.
     */
    WRITE_S3_BUCKET_KEY("writeS3BucketKey",
            "Parameter name for bucket key that will be created in intermediate data path."), //

    /** AWS access key parameter name. */
    AWS_ACCESS_KEY_ID("awsAccessKeyId", "AWS access key parameter name."), //

    /** AWS secret key parameter name. */
    AWS_SECRET_ACCESS_KEY("awsSecretAccessKey", "AWS secret key parameter name."), //

    /** AWS session token parameter name. */
    AWS_SESSION_TOKEN("awsSessionToken", "AWS session token parameter name."), //

    /** AWS credentials provider name. */
    AWS_CREDENTIALS_PROVIDER("awsCredentialsProvider", "AWS credentials provider parameter name."), //

    /** AWS region parameter name. */
    AWS_REGION("awsRegion", "AWS region parameter name."), //

    /** Default AWS region value. */
    DEFAULT_AWS_REGION("us-east-1", "Default AWS region value."), //

    /** Boolean parameter to enable SSL. */
    AWS_USE_SSL("useSsl", "Boolean parameter to enable SSL."), //

    /** AWS bucket name parameter name. */
    S3_BUCKET("s3Bucket", "Parameter name for user provided AWS bucket name."), //

    /** AWS endpoint override parameter name. */
    S3_ENDPOINT_OVERRIDE("awsEndpointOverride", "AWS endpoint override parameter name."), //

    /** Boolean parameter name to enable S3 path style access. */
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

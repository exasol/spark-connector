package com.exasol.spark.s3;

/**
 * A class that contains common constant variables.
 */
public final class Constants {

    /** Parameter name for Exasol table. */
    public static final String TABLE = "TABLE";
    /** Parameter name for Exasol query. */
    public static final String QUERY = "QUERY";
    /** Parameter name for Exasol database connection JDBC URL. */
    public static final String JDBC_URL = "JDBC_URL";
    /** Parameter name for Exasol database username. */
    public static final String USERNAME = "USERNAME";
    /** Parameter name for Exasol database password. */
    public static final String PASSWORD = "PASSWORD";
    /** Parameter name for setting number of Spark job partitions. */
    public static final String NUMBER_OF_PARTITIONS = "numPartitions";
    /** Default number of partitions for Spark job. */
    public static final int DEFAULT_NUMBER_OF_PARTITIONS = 8;
    /** Maximum allowed number of partitions for Spark job. */
    public static final int MAX_ALLOWED_NUMBER_OF_PARTITIONS = 1000;
    /** Boolean parameter name to indicate local and CI environment. */
    public static final String REPLACE_LOCALHOST_BY_DEFAULT_S3_ENDPOINT = "replaceLocalhostByDefaultS3Endpoint";

    //

    /** AWS access key parameter name. */
    public static final String AWS_ACCESS_KEY_ID = "awsAccessKeyId";
    /** AWS secret key parameter name. */
    public static final String AWS_SECRET_ACCESS_KEY = "awsSecretAccessKey";
    /** AWS session token parameter name. */
    public static final String AWS_SESSION_TOKEN = "awsSessionToken";
    /** AWS credentials provider name. */
    public static final String AWS_CREDENTIALS_PROVIDER = "awsCredentialsProvider";
    /** AWS region parameter name. */
    public static final String AWS_REGION = "awsRegion";
    /** Default AWS region value. */
    public static final String DEFAULT_AWS_REGION = "us-east-1";
    /** Boolean parameter to enable SSL. */
    public static final String AWS_USE_SSL = "useSsl";
    /** AWS bucket name parameter name. */
    public static final String S3_BUCKET = "s3Bucket";
    /** AWS endpoint override parameter name. */
    public static final String S3_ENDPOINT_OVERRIDE = "awsEndpointOverride";
    /** Boolean parameter name to enable S3 path style access. */
    public static final String S3_PATH_STYLE_ACCESS = "s3PathStyleAccess";

    private Constants() {
        // prevent instantiation
    }

}

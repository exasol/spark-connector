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

    private Constants() {
        // prevent instantiation
    }

}

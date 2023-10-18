package com.exasol.spark.s3;

import com.exasol.spark.common.ExasolOptions;
import com.exasol.spark.common.Option;

import com.amazonaws.util.StringUtils;

import java.util.AbstractMap;
import java.util.Map;

import static com.amazonaws.SDKGlobalConfiguration.ACCESS_KEY_ENV_VAR;
import static com.amazonaws.SDKGlobalConfiguration.SECRET_KEY_ENV_VAR;

/**
 * An common {@code CSV} query generator class.
 *
 * A generator for Exasol {@code IMPORT} or {@code EXPORT} queries that access {@code CSV} files in intermediate storage
 * systems.
 *
 * @see <a href="https://docs.exasol.com/db/latest/sql/import.htm">Exasol Import</a>
 * @see <a href="https://docs.exasol.com/db/latest/sql/export.htm">Exasol Export</a>
 */
public class BaseQueryGenerator {
    private static final String DEFAULT_S3_ENDPOINT = "amazonaws.com";

    /** Spark options for scenarios involving an Exasol database */
    protected final ExasolOptions options;

    /**
     * Creates a new instance of {@link BaseQueryGenerator}.
     *
     * @param options user provided options
     */
    public BaseQueryGenerator(final ExasolOptions options) {
        this.options = options;
    }

    /**
     * Creates an {@code IDENTIFIED BY} part of a query.
     *
     * @return identifiedBy part of a query
     */
    public String getIdentifier() {
        Map.Entry<String, String> awsCreds = getAWSCredentials();

        StringBuilder result = new StringBuilder("AT '");
        result.append(escapeStringLiteral(getBucketURL()));
        result.append('\'');

        // no access key -> no user in the identifier, giving an option to use AWS EC2 Role Profiles
        // https://exasol.my.site.com/s/article/Changelog-content-15155?language=en_US
        if (!StringUtils.isNullOrEmpty(awsCreds.getKey())) {
            result.append(" USER '");
            result.append(escapeStringLiteral(awsCreds.getKey()));
            result.append('\'');
        }

        if (!StringUtils.isNullOrEmpty(awsCreds.getValue())) {
            result.append(" IDENTIFIED BY '");
            result.append(escapeStringLiteral(awsCreds.getValue()));
            result.append('\'');
        }
        result.append('\n');
        return result.toString();
    }

    private String escapeStringLiteral(final String input) {
        return input.replace("'", "''");
    }

    private String getBucketURL() {
        return "https://" + this.options.getS3Bucket() + ".s3." + getS3Endpoint();
    }

    private String getS3Endpoint() {
        if (!this.options.containsKey(Option.S3_ENDPOINT_OVERRIDE.key())) {
            return DEFAULT_S3_ENDPOINT;
        }
        final String override = this.options.get(Option.S3_ENDPOINT_OVERRIDE.key());
        if (this.options.hasEnabled(Option.REPLACE_LOCALHOST_BY_DEFAULT_S3_ENDPOINT.key())) {
            return override.replace("localhost", DEFAULT_S3_ENDPOINT);
        }
        return override;
    }

    protected Map.Entry<String, String> getAWSCredentials() {
        String awsAccessKeyId, awsSecretAccessKey = null;

        if (this.options.containsKey(Option.AWS_ACCESS_KEY_ID.key())) {
            awsAccessKeyId = this.options.get(Option.AWS_ACCESS_KEY_ID.key());
            if (this.options.containsKey(Option.AWS_SECRET_ACCESS_KEY.key()))
                awsSecretAccessKey = this.options.get(Option.AWS_SECRET_ACCESS_KEY.key());
        } else {
            // Retrieve access key and secret access key from environment variables
            awsAccessKeyId = System.getenv(ACCESS_KEY_ENV_VAR);
            awsSecretAccessKey = System.getenv(SECRET_KEY_ENV_VAR);
        }
        awsAccessKeyId = StringUtils.trim(awsAccessKeyId);
        awsSecretAccessKey = StringUtils.trim(awsSecretAccessKey);
        return new AbstractMap.SimpleImmutableEntry<>(awsAccessKeyId, awsSecretAccessKey);
    }
}

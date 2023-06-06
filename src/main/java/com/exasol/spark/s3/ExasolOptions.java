package com.exasol.spark.s3;

import java.util.*;

import com.exasol.errorreporting.ExaError;

/**
 * Configuration parameters for Exasol Spark connectors.
 */
public final class ExasolOptions {
    private final String jdbcUrl;
    private final String username;
    private final String password;
    private final String table;
    private final String query;
    private final String s3Bucket;
    private final Map<String, String> optionsMap;

    private ExasolOptions(final Builder builder) {
        this.jdbcUrl = builder.jdbcUrl;
        this.username = builder.username;
        this.password = builder.password;
        this.table = builder.table;
        this.query = builder.query;
        this.s3Bucket = builder.s3Bucket;
        this.optionsMap = builder.optionsMap;
    }

    /**
     * Gets the JDBC connection URL.
     *
     * @return JDBC connection URL
     */
    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    /**
     * Gets the connection username.
     *
     * @return connection username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the connection password.
     *
     * @return connection password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Checks if an table name parameter is available.
     *
     * @return {@code true} if table parameter is available
     */
    public boolean hasTable() {
        return this.table != null && !this.table.isEmpty();
    }

    /**
     * Gets the table parameter.
     *
     * @return table parameter value
     */
    public String getTable() {
        return this.table;
    }

    /**
     * Checks if a query parameter is available.
     *
     * @return {@code true} if query parameter is available
     */
    public boolean hasQuery() {
        return this.query != null && !this.query.isEmpty();
    }

    /**
     * Gets the query parameter.
     *
     * @return query parameter value
     */
    public String getQuery() {
        return this.query;
    }

    /**
     * Gets the table or query parameter.
     *
     * Both of them would not be set at the same time.
     *
     * @return table or query parameter value
     */
    public String getTableOrQuery() {
        return hasTable() ? this.table : this.query;
    }

    /**
     * Checks if an S3 bucket parameter is available.
     *
     * @return {@code true} if S3 bucket is available
     */
    public boolean hasS3Bucket() {
        return this.s3Bucket != null && !this.s3Bucket.isEmpty();
    }

    /**
     * Gets the S3 bucket name.
     *
     * @return an S3 bucket name
     */
    public String getS3Bucket() {
        return this.s3Bucket;
    }

    /**
     * Gets the number of partitions for Spark dataframe.
     *
     * @return number of partitions
     */
    public int getNumberOfPartitions() {
        if (!containsKey(Option.NUMBER_OF_PARTITIONS.key())) {
            return Integer.parseInt(Option.DEFAULT_NUMBER_OF_PARTITIONS.key());
        } else {
            return Integer.parseInt(get(Option.NUMBER_OF_PARTITIONS.key()));
        }
    }

    /**
     * Checks if a parameter key is available.
     *
     * @param key parameter name to check
     * @return {@code true} if parameter key is available
     */
    public boolean containsKey(final String key) {
        return this.optionsMap.containsKey(toLowerCase(key));
    }

    /**
     * Gets the value for a key.
     *
     * @param key key of a map
     * @return value of the key
     * @throws IllegalArgumentException in case no value exists for the key
     */
    public String get(final String key) {
        if (!containsKey(key)) {
            throw new IllegalArgumentException(
                    ExaError.messageBuilder("E-SEC-21").message("Key {{key name}} not found in the options map.", key)
                            .mitigation("Please make sure it is set and correct.").toString());
        }
        return this.optionsMap.get(toLowerCase(key));
    }

    /**
     * Checks if parameter key is set to {@code true}.
     *
     * @param key key of a map
     * @return {@code true} if parameter key is available and set to {@code true} value
     */
    public boolean hasEnabled(final String key) {
        if (!containsKey(key)) {
            return false;
        }
        final String value = get(key);
        return value.equalsIgnoreCase("true");
    }

    private String toLowerCase(final Object key) {
        return key.toString().toLowerCase(Locale.ROOT);
    }

    /**
     * Creates a new builder for {@link ExasolOptions}.
     *
     * @return builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExasolOptions)) {
            return false;
        }
        final ExasolOptions options = (ExasolOptions) other;
        return Objects.equals(this.jdbcUrl, options.jdbcUrl) //
                && Objects.equals(this.username, options.username) //
                && Objects.equals(this.password, options.password) //
                && Objects.equals(this.table, options.table) //
                && Objects.equals(this.query, options.query) //
                && Objects.equals(this.s3Bucket, options.s3Bucket) //
                && Objects.equals(this.optionsMap, options.optionsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.jdbcUrl, this.username, this.password, this.table, this.query, this.s3Bucket,
                this.optionsMap);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("ExasolOptions{") //
                .append("jdbcUrl=\"").append(this.jdbcUrl) //
                .append("\", username=\"").append(this.username) //
                .append("\", password=\"*******\"");
        if (this.hasS3Bucket()) {
            stringBuilder.append(", s3Bucket=\"").append(this.s3Bucket).append("\"");
        }
        if (this.hasTable()) {
            stringBuilder.append(", table=\"").append(this.table).append("\"");
        }
        if (this.hasQuery()) {
            stringBuilder.append(", query=\"").append(this.query).append("\"");
        }
        if (!this.optionsMap.isEmpty()) {
            stringBuilder.append(", map=\"").append(this.optionsMap.toString()).append("\"");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    /**
     * Builder for {@link ExasolOptions}.
     */
    public static class Builder {
        private String jdbcUrl = "jdbc:exa:localhost:8563";
        private String username = "sys";
        @SuppressWarnings("java:S2068") // Default password used for CI
        private String password = "exasol";
        private String table = null;
        private String query = null;
        private String s3Bucket = null;
        private Map<String, String> optionsMap = new HashMap<>(0);

        /**
         * Sets the JDBC connection URL.
         *
         * @param jdbcUrl {@code JDBC} connection URL
         * @return builder instance for fluent programming
         */
        public Builder jdbcUrl(final String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;
            return this;
        }

        /**
         * Sets the connection username.
         *
         * @param username connection username
         * @return builder instance for fluent programming
         */
        public Builder username(final String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the connection password.
         *
         * @param password connection password
         * @return builder instance for fluent programming
         */
        public Builder password(final String password) {
            this.password = password;
            return this;
        }

        /**
         * Sets the user provided table name option.
         *
         * @param table for querying or writing
         * @return builder instance for fluent programming
         */
        public Builder table(final String table) {
            this.table = table;
            return this;
        }

        /**
         * Sets the user provided query string.
         *
         * @param query string for reading
         * @return builder instance for fluent programming
         */
        public Builder query(final String query) {
            this.query = query;
            return this;
        }

        /**
         * Sets the S3 bucket name.
         *
         * @param s3Bucket S3 bucket name
         * @return builder instance for fluent programming
         */
        public Builder s3Bucket(final String s3Bucket) {
            this.s3Bucket = s3Bucket;
            return this;
        }

        /**
         * Sets key-value map.
         *
         * @param map key-value map
         * @return builder instance for fluent programming
         */
        public Builder withOptionsMap(final Map<String, String> map) {
            this.optionsMap = getCaseInsensitiveMap(map);
            return this;
        }

        private Map<String, String> getCaseInsensitiveMap(final Map<String, String> map) {
            final Map<String, String> caseInsensitiveMap = new HashMap<>(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                final String lowerCaseKey = entry.getKey().toLowerCase(Locale.ROOT);
                if (caseInsensitiveMap.containsKey(lowerCaseKey)) {
                    throw new IllegalArgumentException(ExaError.messageBuilder("E-SEC-20")
                            .message("Found case sensitive duplicate key {{KEY}}.", entry.getKey())
                            .mitigation("Please remove case sensitive duplicate options, and set only one of them.")
                            .toString());
                }
                caseInsensitiveMap.put(lowerCaseKey, entry.getValue());
            }
            return caseInsensitiveMap;
        }

        /**
         * Builds a new instance of {@link ExasolOptions}.
         *
         * @return new instance of {@link ExasolOptions}
         */
        public ExasolOptions build() {
            validate();
            return new ExasolOptions(this);
        }

        private void validate() {
            if (this.table != null && this.query != null) {
                throw new IllegalArgumentException(ExaError.messageBuilder("E-SEC-19")
                        .message("It is not possible to set both 'query' and 'table' options.")
                        .mitigation("Please set only one of the them.").toString());
            }
        }

    }

}

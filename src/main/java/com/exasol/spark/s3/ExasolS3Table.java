package com.exasol.spark.s3;

import static com.exasol.spark.s3.Constants.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.connector.catalog.SupportsRead;
import org.apache.spark.sql.connector.catalog.SupportsWrite;
import org.apache.spark.sql.connector.catalog.TableCapability;
import org.apache.spark.sql.connector.read.ScanBuilder;
import org.apache.spark.sql.connector.write.LogicalWriteInfo;
import org.apache.spark.sql.connector.write.WriteBuilder;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

import com.exasol.errorreporting.ExaError;

/**
 * Represents an instance of {@link ExasolS3Table}.
 *
 * It uses AWS S3 as an intermediate storage for reading or writing to Exasol database.
 */
public class ExasolS3Table implements SupportsRead, SupportsWrite {
    private final StructType schema;
    private final Set<TableCapability> capabilities;

    /**
     * Creates a new instance of {@link ExasolS3Table}.
     *
     * @param schema user provided schema
     */
    public ExasolS3Table(final StructType schema) {
        this.schema = schema;
        this.capabilities = Collections.unmodifiableSet(
                Stream.of(TableCapability.BATCH_READ, TableCapability.BATCH_WRITE).collect(Collectors.toSet()));
    }

    @Override
    public String name() {
        final StringBuilder builder = new StringBuilder();
        builder //
                .append("ExasolS3Table[") //
                .append("schema='" + this.schema().toString()) //
                .append("',") //
                .append("capabilities='" + this.capabilities().toString()) //
                .append("']");
        return builder.toString();
    }

    @Override
    public StructType schema() {
        return this.schema;
    }

    @Override
    public Set<TableCapability> capabilities() {
        return this.capabilities;
    }

    @Override
    public ScanBuilder newScanBuilder(final CaseInsensitiveStringMap map) {
        final ExasolOptions options = getExasolOptions(map);
        validateNumberOfPartitions(options);
        updateSparkConfigurationForS3(options);
        return new ExasolS3ScanBuilder(options, this.schema, map);
    }

    @Override
    public WriteBuilder newWriteBuilder(final LogicalWriteInfo defaultInfo) {
        return null; // this will be implemented in #149
    }

    private ExasolOptions getExasolOptions(final CaseInsensitiveStringMap options) {
        final ExasolOptions.Builder builder = ExasolOptions.builder() //
                .jdbcUrl(options.get(JDBC_URL)) //
                .username(options.get(USERNAME)) //
                .password(options.get(PASSWORD)) //
                .s3Bucket(options.get(S3_BUCKET));
        if (options.containsKey(TABLE)) {
            builder.table(options.get(TABLE));
        } else if (options.containsKey(QUERY)) {
            builder.query(options.get(QUERY));
        }
        return builder.withOptionsMap(options.asCaseSensitiveMap()).build();
    }

    private void validateNumberOfPartitions(final ExasolOptions options) {
        final int numberOfPartitions = options.getNumberOfPartitions();
        if (numberOfPartitions > MAX_ALLOWED_NUMBER_OF_PARTITIONS) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-SEC-23") //
                    .message("The number of partitions exceeds the supported maximum of {{MAXPARTITIONS}}.",
                        MAX_ALLOWED_NUMBER_OF_PARTITIONS) //
                    .mitigation("Please set parameter {{param}} to a lower value.", NUMBER_OF_PARTITIONS) //
                    .toString());
        }
    }

    private void updateSparkConfigurationForS3(final ExasolOptions options) {
        final SparkSession sparkSession = SparkSession.active();
        synchronized (sparkSession.sparkContext().hadoopConfiguration()) {
            final Configuration conf = sparkSession.sparkContext().hadoopConfiguration();
            if (options.containsKey(AWS_CREDENTIALS_PROVIDER)) {
                conf.set("fs.s3a.aws.credentials.provider", options.get(AWS_CREDENTIALS_PROVIDER));
                conf.set("fs.s3a.access.key", options.get(AWS_ACCESS_KEY_ID));
                conf.set("fs.s3a.secret.key", options.get(AWS_SECRET_ACCESS_KEY));
            }
            if (options.containsKey(S3_ENDPOINT_OVERRIDE)) {
                conf.set("fs.s3a.endpoint", "http://" + options.get(S3_ENDPOINT_OVERRIDE));
            }
            if (options.hasEnabled(S3_PATH_STYLE_ACCESS)) {
                conf.set("fs.s3a.path.style.access", "true");
            }
        }
    }

}

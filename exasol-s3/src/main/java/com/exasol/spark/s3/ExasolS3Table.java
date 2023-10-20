package com.exasol.spark.s3;

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
import com.exasol.spark.common.ExasolOptions;
import com.exasol.spark.common.ExasolValidationException;
import com.exasol.spark.common.Option;

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
        return new ExasolS3ScanBuilder(this.buildOptions(map), this.schema, map);
    }

    @Override
    public WriteBuilder newWriteBuilder(final LogicalWriteInfo defaultInfo) {
        final ExasolOptions options = this.buildOptions(defaultInfo.options());
        validateHasTable(options);
        final SparkSession sparkSession = SparkSession.active();
        final String applicationId = sparkSession.sparkContext().applicationId();
        final S3BucketKeyPathProvider prov = new UUIDS3BucketKeyPathProvider(applicationId);
        return new ExasolWriteBuilderProvider(options, prov).createWriteBuilder(this.schema, defaultInfo);
    }

    protected ExasolOptions buildOptions(final CaseInsensitiveStringMap map) {
        final ExasolOptions result = ExasolOptions.from(map);
        validateNumberOfPartitions(result);
        updateSparkConfigurationForS3(result);
        return result;
    }

    private void validateNumberOfPartitions(final ExasolOptions options) {
        final int numberOfPartitions = options.getNumberOfPartitions();
        final int maxAllowedPartitions = Integer.parseInt(Option.MAX_ALLOWED_NUMBER_OF_PARTITIONS.key());
        if (numberOfPartitions > maxAllowedPartitions) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-SEC-23") //
                    .message("The number of partitions exceeds the supported maximum of {{MAXPARTITIONS}}.",
                            maxAllowedPartitions) //
                    .mitigation("Please set parameter {{param}} to a lower value.", Option.NUMBER_OF_PARTITIONS.key()) //
                    .toString());
        }
    }

    private void validateHasTable(final ExasolOptions options) {
        if (!options.hasTable()) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-SEC-19")
                    .message("Missing 'table' option when writing into Exasol database.")
                    .mitigation("Please set 'table' property with fully qualified "
                            + "(e.g. 'schema_name.table_name') Exasol table name.")
                    .toString());
        }
    }

    private void updateSparkConfigurationForS3(final ExasolOptions options) {
        final SparkSession sparkSession = SparkSession.active();
        synchronized (sparkSession.sparkContext().hadoopConfiguration()) {
            final Configuration conf = sparkSession.sparkContext().hadoopConfiguration();
            if (options.containsKey(Option.AWS_ACCESS_KEY_ID.key())) {
                conf.set("fs.s3a.access.key", options.get(Option.AWS_ACCESS_KEY_ID.key()));
                conf.set("fs.s3a.secret.key", options.get(Option.AWS_SECRET_ACCESS_KEY.key()));
            }
            if (options.containsKey(Option.AWS_CREDENTIALS_PROVIDER.key())) {
                conf.set("fs.s3a.aws.credentials.provider", options.get(Option.AWS_CREDENTIALS_PROVIDER.key()));
            }
            if (options.containsKey(Option.S3_ENDPOINT_OVERRIDE.key())) {
                conf.set("fs.s3a.endpoint", "http://" + options.get(Option.S3_ENDPOINT_OVERRIDE.key()));
            }
            if (options.hasEnabled(Option.S3_PATH_STYLE_ACCESS.key())) {
                conf.set("fs.s3a.path.style.access", "true");
            }
        }
    }

}

package com.exasol.spark.s3;

import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.connector.write.LogicalWriteInfo;
import org.apache.spark.sql.connector.write.WriteBuilder;
import org.apache.spark.sql.execution.datasources.csv.CSVFileFormat;
import org.apache.spark.sql.execution.datasources.v2.csv.CSVTable;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

import com.exasol.spark.common.ExasolOptions;
import com.exasol.spark.common.Option;

import scala.collection.JavaConverters;
import scala.collection.immutable.Seq;

/**
 * A class that provides {@link WriteBuilder} instance.
 */
public final class ExasolWriteBuilderProvider {
    private static final Logger LOGGER = Logger.getLogger(ExasolWriteBuilderProvider.class.getName());

    private final ExasolOptions options;

    /**
     * Creates a new instance of {@link ExasolWriteBuilderProvider}.
     *
     * @param options user provided options
     */
    public ExasolWriteBuilderProvider(final ExasolOptions options) {
        this.options = options;
    }

    /**
     * Creates a {@link WriteBuilder} for writing into Exasol database.
     *
     * @param schema      user provided {@link StructType} schema
     * @param defaultInfo {@link LogicalWriteInfo} information for writing
     * @return an instance of {@link WriteBuilder}
     */
    public WriteBuilder createWriteBuilder(final StructType schema, final LogicalWriteInfo defaultInfo) {
        final SparkSession sparkSession = SparkSession.active();
        final LogicalWriteInfo info = getUpdatedLogicalWriteInfo(defaultInfo, sparkSession);
        final ExasolOptions updatedOptions = getUpdatedOptions(info.options());
        final String intermediateDataPath = updatedOptions.get(Option.INTERMEDIATE_DATA_PATH.key());
        LOGGER.info(() -> "Writing intermediate data to the '" + intermediateDataPath + "' path for write job.");
        final CSVTable csvTable = new CSVTable("", sparkSession, info.options(), getS3WritePath(intermediateDataPath),
                scala.Option.apply(schema), new CSVFileFormat().getClass());
        return new DelegatingWriteBuilder(updatedOptions, csvTable.newWriteBuilder(info));
    }

    private ExasolOptions getUpdatedOptions(final Map<String, String> map) {
        final ExasolOptions.Builder builder = ExasolOptions.builder() //
                .jdbcUrl(this.options.getJdbcUrl()) //
                .username(this.options.getUsername()) //
                .password(this.options.getPassword()) //
                .s3Bucket(this.options.getS3Bucket());
        if (this.options.hasTable()) {
            builder.table(this.options.getTable());
        } else {
            builder.query(this.options.getQuery());
        }
        builder.withOptionsMap(map);
        return builder.build();
    }

    private LogicalWriteInfo getUpdatedLogicalWriteInfo(final LogicalWriteInfo defaultInfo,
            final SparkSession sparkSession) {
        final Map<String, String> map = new HashMap<>(defaultInfo.options().asCaseSensitiveMap());
        map.put("header", "true");
        map.put("delimiter", ",");
        map.put("mapreduce.fileoutputcommitter.marksuccessfuljobs", "false");
        final String s3Bucket = this.options.getS3Bucket();
        final String s3BucketKey = getS3BucketKeyForWriteLocation(defaultInfo, sparkSession);
        map.put(Option.INTERMEDIATE_DATA_PATH.key(), "s3a://" + Paths.get(s3Bucket, s3BucketKey).toString());
        map.put(Option.WRITE_S3_BUCKET_KEY.key(), s3BucketKey);

        return new LogicalWriteInfo() {
            @Override
            public String queryId() {
                return defaultInfo.queryId();
            }

            @Override
            public StructType schema() {
                return defaultInfo.schema();
            }

            @Override
            public CaseInsensitiveStringMap options() {
                return new CaseInsensitiveStringMap(map);
            }
        };
    }

    private String getS3BucketKeyForWriteLocation(final LogicalWriteInfo defaultInfo, final SparkSession sparkSession) {
        final StringBuilder builder = new StringBuilder();
        builder.append(UUID.randomUUID()).append("-").append(sparkSession.sparkContext().applicationId()).append("/")
                .append(defaultInfo.queryId());
        return builder.toString();
    }

    private Seq<String> getS3WritePath(final String path) {
        return JavaConverters.asScalaIteratorConverter(Arrays.asList(path).iterator()).asScala().toSeq();
    }

}

package com.exasol.spark.s3;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.connector.read.*;
import org.apache.spark.sql.execution.datasources.csv.CSVFileFormat;
import org.apache.spark.sql.execution.datasources.v2.csv.CSVTable;
import org.apache.spark.sql.sources.Filter;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

import com.exasol.errorreporting.ExaError;
import com.exasol.spark.common.ExasolOptions;

import scala.collection.JavaConverters;
import scala.collection.immutable.Seq;

/**
 * A class that implements {@link ScanBuilder} interface for accessing {@code S3} intermediate storage.
 */
public class ExasolS3ScanBuilder implements ScanBuilder, SupportsPushDownFilters, SupportsPushDownRequiredColumns {
    private static final Logger LOGGER = Logger.getLogger(ExasolS3ScanBuilder.class.getName());
    private final ExasolOptions options;
    private final CaseInsensitiveStringMap properties;

    private StructType schema;
    private Filter[] pushedFilters;

    /**
     * Creates a new instance of {@link ExasolS3ScanBuilder}.
     *
     * @param options    user provided options
     * @param schema     user-provided {@link StructType} schema
     * @param properties original key-value properties map that is passed to delegating classes
     */
    public ExasolS3ScanBuilder(final ExasolOptions options, final StructType schema,
            final CaseInsensitiveStringMap properties) {
        this.options = options;
        this.schema = schema;
        this.properties = properties;
        this.pushedFilters = new Filter[0];
    }

    @Override
    public Filter[] pushFilters(final Filter[] filters) {
        final List<Filter> unsupportedFilters = getUnsupportedFilters(filters);
        final List<Filter> supportedFilters = new ArrayList<>(Arrays.asList(filters));
        supportedFilters.removeAll(unsupportedFilters);
        this.pushedFilters = supportedFilters.toArray(new Filter[] {});
        return unsupportedFilters.toArray(new Filter[] {});
    }

    private List<Filter> getUnsupportedFilters(final Filter[] filters) {
        return Collections.emptyList();
    }

    @Override
    public Filter[] pushedFilters() {
        return this.pushedFilters;
    }

    @Override
    public void pruneColumns(final StructType schema) {
        this.schema = schema;
    }

    @Override
    public Scan build() {
        final SparkSession sparkSession = SparkSession.active();
        final String bucket = this.options.getS3Bucket();
        final String bucketKey = generateRandomBucketKey(sparkSession);
        // Import query data into `s3Bucket/s3BucketKey` location as `CSV` files
        LOGGER.info(() -> "Using S3 bucket '" + bucket + "' with folder '" + bucketKey + "' for scan job data.");
        addSparkCleanupJobListener(sparkSession, bucketKey);
        prepareIntermediateData(bucketKey);
        // Uses Spark `CSVTable` to read `CSV` files
        final Seq<String> csvFilesPaths = getCSVFiles(bucket, bucketKey);
        return new CSVTable("", sparkSession, this.properties, csvFilesPaths, scala.Option.apply(this.schema),
                new CSVFileFormat().getClass()).newScanBuilder(getUpdatedMapWithCSVOptions(this.properties)).build();
    }

    private String generateRandomBucketKey(final SparkSession sparkSession) {
        return UUID.randomUUID() + "-" + sparkSession.sparkContext().applicationId();
    }

    private void addSparkCleanupJobListener(final SparkSession spark, final String bucketKey) {
        spark.sparkContext().addSparkListener(new S3CleanupListener(this.options, bucketKey));
    }

    private Seq<String> getCSVFiles(final String bucket, final String bucketKey) {
        final String path = "s3a://" + Paths.get(bucket, bucketKey, "*.csv").toString();
        return JavaConverters.asScalaIteratorConverter(Arrays.asList(path).iterator()).asScala().toSeq();
    }

    private CaseInsensitiveStringMap getUpdatedMapWithCSVOptions(final CaseInsensitiveStringMap map) {
        final Map<String, String> updatedMap = new HashMap<>(map.asCaseSensitiveMap());
        updatedMap.put("header", "true");
        updatedMap.put("delimiter", ",");
        return new CaseInsensitiveStringMap(updatedMap);
    }

    /**
     * Returns SQL query that would be run on the Exasol database.
     *
     * This is enriched query that would add predicates or specific columns on top the user provided query or table. The
     * result of this enriched query will be saved into the intermediate storage.
     *
     * @return Enriched SQL query for the intermediate storage.
     */
    protected String getScanQuery() {
        return "SELECT * FROM " + getTableOrQuery() + " ";
    }

    private String getTableOrQuery() {
        if (this.options.hasTable()) {
            return this.options.getTable();
        } else {
            return "(" + this.options.getQuery() + ")";
        }
    }

    private void prepareIntermediateData(final String bucketKey) {
        final String exportQuery = new S3ExportQueryGenerator(this.options, bucketKey).generateQuery(getScanQuery());
        new S3DataExporter(this.options, bucketKey).exportData(exportQuery);
    }

    /**
     * A class that generates {@code SQL} query for exporting data from Exasol database into {@code S3} location.
     */
    private static class S3ExportQueryGenerator extends AbstractImportExportQueryGenerator {
        private final String bucketKey;
        private final int numberOfFiles;

        public S3ExportQueryGenerator(final ExasolOptions options, final String bucketKey) {
            super(options);
            this.bucketKey = bucketKey;
            this.numberOfFiles = options.getNumberOfPartitions();
        }

        public String generateQuery(final String baseQuery) {
            return new StringBuilder() //
                    .append("EXPORT (\n") //
                    .append(baseQuery) //
                    .append("\n) INTO CSV\n") //
                    .append(getIdentifier()) //
                    .append(getFiles()) //
                    .append(getFooter()) //
                    .toString();
        }

        private String getFiles() {
            final StringBuilder builder = new StringBuilder();
            final String prefix = "FILE '" + this.bucketKey + "/";
            for (int fileIndex = 1; fileIndex <= this.numberOfFiles; fileIndex++) {
                builder.append(prefix).append(String.format("part-%03d", fileIndex)).append(".csv'\n");
            }
            return builder.toString();
        }

        private String getFooter() {
            return "WITH COLUMN NAMES\nBOOLEAN = 'true/false'";
        }
    }

    /**
     * A class that exports data from Exasol database into {@code S3} location.
     */
    private static class S3DataExporter {
        private final ExasolOptions options;
        private final String bucket;
        private final String bucketKey;

        public S3DataExporter(final ExasolOptions options, final String bucketKey) {
            this.options = options;
            this.bucket = options.getS3Bucket();
            this.bucketKey = bucketKey;
        }

        public int exportData(final String exportQuery) {
            final ExasolConnectionFactory connectionFactory = new ExasolConnectionFactory(this.options);
            try (final Connection connection = connectionFactory.getConnection();
                    final Statement statement = connection.createStatement()) {
                final int numberOfExportedRows = statement.executeUpdate(exportQuery);
                LOGGER.info(() -> "Exported '" + numberOfExportedRows + "' rows into '" + this.bucket + "/"
                        + this.bucketKey + "'.");
                return numberOfExportedRows;
            } catch (final SQLException exception) {
                throw new ExasolValidationException(ExaError.messageBuilder("E-SEC-22")
                        .message("Failed to run export query {{exportQuery}} into S3 location {{s3Path}}.")
                        .parameter("exportQuery", removeIdentifiedByPart(exportQuery))
                        .parameter("s3Path", this.bucket + "/" + this.bucketKey)
                        .mitigation(
                                "Please ensure that query and table name are correct and satisfy SQL syntax requirements.")
                        .toString(), exception);
            }
        }
    }

    private static String removeIdentifiedByPart(final String input) {
        return Stream.of(input.split("\n")).filter(s -> !s.contains("IDENTIFIED BY")).collect(Collectors.joining("\n"));
    }

}

package com.exasol.spark.s3;

import static com.exasol.matcher.ResultSetStructureMatcher.table;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkException;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.MapGroupsFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.*;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.dbbuilder.dialects.Table;
import com.exasol.spark.SparkSessionProvider;

import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

// For this test suite, we start Spark session for each test unit to force the job end call.
@Tag("integration")
@Testcontainers
class S3CleanupIT extends S3IntegrationTestSetup {
    private int MAX_ALLOWED_SPARK_TASK_FAILURES = 3;

    private static Table table;

    private final SparkConf conf = new SparkConf() //
            .setMaster("local[*," + MAX_ALLOWED_SPARK_TASK_FAILURES + "]") //
            .setAppName("S3CleanupTests") //
            .set("spark.ui.enabled", "false") //
            .set("spark.driver.host", "localhost");

    private Map<String, String> getSparkOptions() {
        final String endpointOverride = DockerClientFactory.instance().dockerHostIpAddress() + ":"
                + S3.getMappedPort(4566);
        final Map<String, String> options = getOptionsMap();
        options.put("awsAccessKeyId", S3.getAccessKey());
        options.put("awsSecretAccessKey", S3.getSecretKey());
        options.put("awsCredentialsProvider", "org.apache.hadoop.fs.s3a.SimpleAWSCredentialsProvider");
        options.put("awsRegion", S3.getRegion());
        options.put("s3Bucket", DEFAULT_BUCKET_NAME);
        options.put("s3PathStyleAccess", "true");
        options.put("awsEndpointOverride", endpointOverride);
        options.put("useSsl", "false");
        options.put("numPartitions", "3");
        options.put("replaceLocalhostByDefaultS3Endpoint", "true");
        return options;
    }

    @BeforeAll
    static void startAll() {
        table = exasolSchema.createTableBuilder("table_cleanup") //
                .column("c1", "CHAR") //
                .build() //
                .bulkInsert(Stream.of("1", "2", "3").map(n -> Arrays.asList(n)));
        spark.stop();
    }

    @BeforeEach
    void beforeEach() {
        spark = SparkSessionProvider.getSparkSession(conf);
    }

    @AfterEach
    void afterEach() {
        TaskFailureStateCounter.clear();
    }

    private boolean isBucketEmpty(final String bucketName) {
        final List<S3Object> objects = s3Client.listObjects(ListObjectsRequest.builder().bucket(bucketName).build())
                .contents();
        return objects.isEmpty();
    }

    private void assertThatBucketIsEmpty() {
        spark.stop();
        assertThat(isBucketEmpty(DEFAULT_BUCKET_NAME), equalTo(true));
    }

    @Test
    void testSourceSuccessJobEndCleanup() {
        final Dataset<String> df = spark.read() //
                .format("exasol-s3") //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load() //
                .map((MapFunction<Row, String>) row -> row.getString(0), Encoders.STRING());
        assertThat(df.collectAsList(), contains("1", "2", "3"));
        assertThatBucketIsEmpty();
    }

    @Test
    void testSourceSingleMapTaskFailureJobEndCleanup() {
        final Dataset<Integer> df = spark.read() //
                .format("exasol-s3") //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load() //
                .map((MapFunction<Row, Integer>) row -> {
                    final int value = Integer.valueOf(row.getString(0));
                    synchronized (TaskFailureStateCounter.class) {
                        if (value == 1 && TaskFailureStateCounter.totalTaskFailures == 0) {
                            TaskFailureStateCounter.totalTaskFailures += 1;
                            throw new RuntimeException("Intentional failure, please ignore it.");
                        }
                    }
                    return value;
                }, Encoders.INT());

        assertThat(df.collectAsList(), contains(1, 2, 3));
        assertThatBucketIsEmpty();
    }

    @Test
    void testSourceMultiStageMapWithCacheFailureJobEndCleanup() {
        final Dataset<Row> cachedDF = spark.read() //
                .format("exasol-s3") //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load() //
                .filter((FilterFunction<Row>) row -> {
                    final int value = Integer.valueOf(row.getString(0));
                    synchronized (TaskFailureStateCounter.class) {
                        if ((value == 1 || value == 3) && TaskFailureStateCounter.totalTaskFailures < 2) {
                            TaskFailureStateCounter.totalTaskFailures += 1;
                            throw new RuntimeException(
                                    "Intentional failure, please ignore it. The filter task value '" + value + "'.");
                        }
                    }
                    return value == 3;
                }) //
                .cache();

        long size = cachedDF.count();
        assertThat(size, equalTo(1L));
        // Should stay the same size = cachedDF.count();
        size = cachedDF.count();
        assertThat(size, equalTo(1L));
        assertThatBucketIsEmpty();
    }

    @Test
    void testSourceMapReduceFailureJobEndCleanup() {
        final Dataset<String> df = spark.read() //
                .format("exasol-s3") //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load() //
                .map((MapFunction<Row, Long>) row -> {
                    final int value = Integer.valueOf(row.getString(0));
                    return value * 1L;
                }, Encoders.LONG()) //
                .groupByKey((MapFunction<Long, String>) v -> (v % 2) == 0 ? "even" : "odd", Encoders.STRING()) //
                .mapGroups((MapGroupsFunction<String, Long, String>) (key, values) -> {
                    synchronized (TaskFailureStateCounter.class) {
                        if (key.equals("even") && TaskFailureStateCounter.totalTaskFailures == 0) {
                            TaskFailureStateCounter.totalTaskFailures += 1;
                            throw new RuntimeException(
                                    "Intentional failure, please ignore it. The reduce task with 'even' key.");
                        }
                    }
                    List<Long> longs = StreamSupport
                            .stream(Spliterators.spliteratorUnknownSize(values, Spliterator.ORDERED), false)
                            .collect(Collectors.toList());
                    return key + ": " + longs.toString();
                }, Encoders.STRING());

        assertThat(df.collectAsList(), containsInAnyOrder("even: [2]", "odd: [1, 3]"));
        assertThatBucketIsEmpty();
    }

    @Test
    void testSourceJobAlwaysFailsJobEndCleanup() {
        final Dataset<Integer> df = spark.read() //
                .format("exasol-s3") //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load() //
                .map((MapFunction<Row, Integer>) row -> {
                    throw new RuntimeException("Intentional failure for all tasks. Please ignore it.");
                }, Encoders.INT());

        final SparkException exception = assertThrows(SparkException.class, () -> df.collectAsList());
        assertThat(exception.getMessage(), containsString("Intentional failure for all tasks."));
        assertThatBucketIsEmpty();
    }

    private Dataset<Row> getSampleSparkDataset() {
        final StructType schema = new StructType() //
                .add("c_str", DataTypes.StringType, false) //
                .add("c_int", DataTypes.IntegerType, false) //
                .add("c_double", DataTypes.DoubleType, false) //
                .add("c_bool", DataTypes.BooleanType, false);
        return spark.createDataFrame(Arrays.asList( //
                RowFactory.create("str", 10, 3.14, true), //
                RowFactory.create("abc", 20, 2.72, false) //
        ), schema);
    }

    @Test
    void testSinkSuccessJobEndCleanup() throws SQLException {
        final Table table = exasolSchema.createTableBuilder("table_cleanup_save") //
                .column("c_str", "VARCHAR(3)") //
                .column("c_int", "DECIMAL(9,0)") //
                .column("c_double", "DOUBLE") //
                .column("c_bool", "BOOLEAN") //
                .build();
        getSampleSparkDataset() //
                .write() //
                .mode("append") //
                .format("exasol-s3") //
                .options(getSparkOptions()) //
                .option("table", table.getFullyQualifiedName()) //
                .save();
        final String query = "SELECT * FROM " + table.getFullyQualifiedName() + " ORDER BY \"c_int\" ASC";
        try (final ResultSet result = connection.createStatement().executeQuery(query)) {
            assertThat(result, table().row("str", 10, 3.14, true).row("abc", 20, 2.72, false).matches());
        }
        assertThatBucketIsEmpty();
    }

    @Test
    void testSinkJobAlwaysFailsJobEndCleanup() {
        final DataFrameWriter<Row> df = getSampleSparkDataset() //
                .write() //
                .mode("append") //
                .format("exasol-s3") //
                .options(getSparkOptions()) //
                .option("table", "non_existent_table");
        final Exception exception = assertThrows(ExasolConnectionException.class, () -> df.save());
        assertThat(exception.getMessage(), containsString("NON_EXISTENT_TABLE not found"));
        assertThatBucketIsEmpty();
    }

    private static class TaskFailureStateCounter {
        private static int totalTaskFailures = 0;

        public static synchronized void clear() {
            totalTaskFailures = 0;
        }
    }

}

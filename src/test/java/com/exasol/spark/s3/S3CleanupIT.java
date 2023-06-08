package com.exasol.spark.s3;

import static com.exasol.matcher.ResultSetStructureMatcher.table;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.*;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkException;
import org.apache.spark.api.java.function.*;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.dbbuilder.dialects.Table;
import com.exasol.spark.SparkSessionProvider;

import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

// For this test suite, we start Spark session with local mode {@code local[*]} with multiple threads for each test unit
// to force the job end call.
@Tag("integration")
@Testcontainers
class S3CleanupIT extends S3IntegrationTestSetup {
    private final int MAX_ALLOWED_SPARK_TASK_FAILURES = 3;

    private static Table table;

    private final SparkConf conf = new SparkConf() //
            .setMaster("local[*," + this.MAX_ALLOWED_SPARK_TASK_FAILURES + "]") //
            .setAppName("S3CleanupTests") //
            .set("spark.ui.enabled", "false") //
            .set("spark.driver.host", "localhost");

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
        spark = SparkSessionProvider.getSparkSession(this.conf);
    }

    @AfterEach
    void afterEach() {
        TaskFailureStateCounter.getInstance().clear();
        spark = SparkSessionProvider.getSparkSession(this.conf);
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

    private Dataset<Row> getSparkDataset() {
        return spark.read() //
                .format("exasol-s3") //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load();
    }

    @Test
    void testSourceSuccessJobEndCleanup() {
        final MapFunction<Row, String> firstString = row -> row.getString(0);
        final Dataset<String> df = getSparkDataset().map(firstString, Encoders.STRING());
        assertThat(df.collectAsList(), contains("1", "2", "3"));
        assertThatBucketIsEmpty();
    }

    @Test
    void testSourceSingleMapTaskFailureJobEndCleanup() {
        final MapFunction<Row, Integer> failOnValue1 = row -> {
            final int value = Integer.valueOf(row.getString(0));
            final String message = "The filter task value '" + value + "'.";
            final TaskFailureStateCounter counter = TaskFailureStateCounter.getInstance();
            if (value == 1 && counter.getCount() == 0) {
                counter.increment();
                throw new RuntimeException("Intentional failure, please ignore it. " + message);
            }
            return value;
        };
        final Dataset<Integer> df = getSparkDataset().map(failOnValue1, Encoders.INT());
        assertThat(df.collectAsList(), contains(1, 2, 3));
        assertThatBucketIsEmpty();
    }

    @Test
    void testSourceMultiStageMapWithCacheFailureJobEndCleanup() {
        final FilterFunction<Row> failOn1And3 = row -> {
            final int value = Integer.valueOf(row.getString(0));
            final String message = "The filter task value '" + value + "'.";
            final TaskFailureStateCounter counter = TaskFailureStateCounter.getInstance();
            if ((value == 1 || value == 3) && counter.getCount() < 2) {
                counter.increment();
                throw new RuntimeException("Intentional failure, please ignore it. " + message);
            }
            return value == 3;
        };
        final Dataset<Row> cachedDF = getSparkDataset().filter(failOn1And3).cache();
        long size = cachedDF.count();
        assertThat(size, equalTo(1L));
        // Should stay the same size = cachedDF.count();
        size = cachedDF.count();
        assertThat(size, equalTo(1L));
        assertThatBucketIsEmpty();
    }

    @Test
    void testSourceMapReduceFailureJobEndCleanup() {
        final MapGroupsFunction<String, Long, String> failOnKeyEven = (key, values) -> {
            final String message = "The reduce task with 'even' key.";
            final TaskFailureStateCounter counter = TaskFailureStateCounter.getInstance();
            if (key.equals("even") && counter.getCount() == 0) {
                counter.increment();
                throw new RuntimeException("Intentional failure, please ignore it. " + message);
            }
            final List<Long> longs = StreamSupport
                    .stream(Spliterators.spliteratorUnknownSize(values, Spliterator.ORDERED), false)
                    .collect(Collectors.toList());
            return key + ": " + longs.toString();
        };
        final MapFunction<Row, Long> convertToLong = row -> Integer.valueOf(row.getString(0)) * 1L;
        final Dataset<String> df = getSparkDataset() //
                .map(convertToLong, Encoders.LONG()) //
                .groupByKey((MapFunction<Long, String>) v -> (v % 2) == 0 ? "even" : "odd", Encoders.STRING()) //
                .mapGroups(failOnKeyEven, Encoders.STRING());
        assertThat(df.collectAsList(), containsInAnyOrder("even: [2]", "odd: [1, 3]"));
        assertThatBucketIsEmpty();
    }

    @Test
    void testSourceJobAlwaysFailsJobEndCleanup() {
        final MapFunction<Row, Integer> failAlways = row -> {
            throw new RuntimeException("Intentional failure for all tasks. Please ignore it.");
        };
        final Dataset<Integer> df = getSparkDataset().map(failAlways, Encoders.INT());
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
        assertThat(exception.getMessage(), containsString("Failure running the import"));
        assertThatBucketIsEmpty();
    }

    /**
     * This class keeps count of failures among multiple Spark runner threads.
     */
    private static class TaskFailureStateCounter {
        private static volatile TaskFailureStateCounter instance;
        private int totalTaskFailures = 0;

        public static TaskFailureStateCounter getInstance() {
            if (instance == null) {
                synchronized (TaskFailureStateCounter.class) {
                    if (instance == null) {
                        instance = new TaskFailureStateCounter();
                    }
                }
            }
            return instance;
        }

        public synchronized void increment() {
            this.totalTaskFailures += 1;
        }

        public synchronized int getCount() {
            return this.totalTaskFailures;
        }

        public synchronized void clear() {
            this.totalTaskFailures = 0;
        }
    }
}

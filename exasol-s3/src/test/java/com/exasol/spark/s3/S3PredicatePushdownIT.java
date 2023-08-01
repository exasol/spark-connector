package com.exasol.spark.s3;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.apache.spark.sql.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.dbbuilder.dialects.Table;
import com.exasol.logging.CapturingLogHandler;

@Tag("integration")
@Testcontainers
class S3PredicatePushdownIT extends S3IntegrationTestSetup {
    private static Table table;
    private final CapturingLogHandler capturingLogHandler = new CapturingLogHandler();

    @BeforeAll
    static void setupAll() {
        table = exasolSchema.createTableBuilder("table_for_predicate_pushdown") //
                .column("column_string", "VARCHAR(10)") //
                .column("column_integer", "DECIMAL(9,0)") //
                .column("column_double", "DOUBLE") //
                .column("column_boolean", "BOOLEAN") //
                .build() //
                .insert("one", 314, 3.14, false) //
                .insert("two", 272, 2.72, false) //
                .insert("three", 1337, 13.37, true);
    }

    @BeforeEach
    void beforeEach() {
        Logger.getLogger("com.exasol").addHandler(this.capturingLogHandler);
        this.capturingLogHandler.reset();
    }

    @AfterEach
    void afterEach() {
        Logger.getLogger("com.exasol").removeHandler(this.capturingLogHandler);
    }

    private Dataset<Row> getSparkDataframe() {
        return spark.read() //
                .format("exasol-s3") //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load();
    }

    @Test
    void testEqualTo() {
        final Dataset<Row> df = getSparkDataframe() //
            .select("column_integer", "column_boolean") //
            .filter("column_string = 'one'");
        final List<Row> rows = df.collectAsList();
        assertAll(() -> assertThat(rows.size(), equalTo(1)), //
                () -> assertThat(rows, contains(RowFactory.create(314, false))),
                () -> assertThat(df.queryExecution().toString(), containsString("Filter ('column_string = one)")), //
                () -> assertThat(this.capturingLogHandler.getCapturedData(),
                        containsString("WHERE (\"column_string\" IS NOT NULL) AND (\"column_string\" = 'one')")));
    }

    @Test
    void testLessThan() {
        final Dataset<String> df = getSparkDataframe() //
            .select("column_string") //
            .filter("column_double < 3.00") //
            .as(Encoders.STRING());
        assertThat(df.collectAsList(), contains("two"));
    }

    @Test
    void testLessThanOrEqual() {
        final Dataset<String> df = getSparkDataframe() //
            .select("column_string") //
            .filter("column_integer <= 314") //
            .as(Encoders.STRING());
        assertThat(df.collectAsList(), contains("one", "two"));
    }

    @Test
    void testGreaterThan() {
        final Dataset<Double> df = getSparkDataframe() //
            .select("column_double") //
            .filter("column_integer > 300") //
            .as(Encoders.DOUBLE());
        assertThat(df.collectAsList(), contains(3.14, 13.37));
    }

    @Test
    void testPredicateGreaterThanOrEqual() {
        final Dataset<Double> df = getSparkDataframe() //
            .select("column_double") //
            .filter("column_integer >= 100") //
            .as(Encoders.DOUBLE());
        assertThat(df.collectAsList(), contains(3.14, 2.72, 13.37));
    }

    @Test
    void testStartsWith() {
        final Dataset<String> df = getSparkDataframe() //
            .select("column_string") //
            .filter("column_string LIKE 'o%'") //
            .as(Encoders.STRING());
        final List<String> rows = df.collectAsList();
        assertAll(() -> assertThat(rows.size(), equalTo(1)), //
                () -> assertThat(rows, contains("one")), //
                () -> assertThat(df.queryExecution().toString(), containsString("LIKE o%")), //
                () -> assertThat(this.capturingLogHandler.getCapturedData(), containsString(
                        "WHERE (\"column_string\" IS NOT NULL) AND (\"column_string\" LIKE 'o%' ESCAPE '\\')")));
    }

    @Test
    void testStringContains() {
        final Dataset<String> df = getSparkDataframe() //
            .select("column_string") //
            .filter("column_string LIKE '%n%'") //
            .as(Encoders.STRING());
        assertThat(df.collectAsList(), contains("one"));
    }

    @Test
    void testStringEndsWith() {
        final Dataset<Integer> df = getSparkDataframe() //
            .select("column_integer") //
            .filter("column_string LIKE '%ee'") //
            .as(Encoders.INT());
        assertThat(df.collectAsList(), contains(1337));
    }

    private static final Table escapedStringsTable = exasolSchema //
        .createTableBuilder("table_for_predicate_pushdown_escaped_strings") //
        .column("column_integer", "DECIMAL(9,0)") //
        .column("column_string", "VARCHAR(30)") //
        .build() //
        .insert("1", "unders\\corewildcard") //
        .insert("2", "%underscore_wild%card%") //
        .insert("3", "underscoreXwildcard") //
        .insert("4", "contains'singlequote") //
        .insert("5", "escaped\\_underscore");

    private static final Stream<Arguments> stringFilters() {
        return Stream.of(//
                Arguments.of(functions.col("column_string").startsWith("%under"), 2), //
                Arguments.of(functions.col("column_string").contains("e_wild%"), 2), //
                Arguments.of(functions.col("column_string").endsWith("card%"), 2), //
                Arguments.of(functions.col("column_string").contains("s\\cor"), 1), //
                Arguments.of(functions.col("column_string").contains("ains'sing"), 4), //
                Arguments.of(functions.col("column_string").contains("d\\_"), 5) //
        );
    }

    @ParameterizedTest
    @MethodSource("stringFilters")
    void testPredicateStringLiteralsEscaped(final Column column, final int id) {
        final Dataset<Integer> df = spark.read() //
                .format("exasol-s3") //
                .option("table", escapedStringsTable.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load() //
                .filter(column) //
                .select("column_integer") //
                .as(Encoders.INT());
        assertThat(df.collectAsList(), contains(id));
    }

    @Test
    void testNonPushedFiltersAreRunPostScan() {
        final Dataset<Row> df = getSparkDataframe() //
                .select("column_string", "column_integer", "column_boolean") //
                .filter(functions.col("column_string").eqNullSafe("one")) // not pushed, should be filtered after scan
                .filter(functions.col("column_double").gt(0.0));
        assertThat(df.collectAsList(), contains(RowFactory.create("one", 314, false)));
    }

    @Test
    void testMultipleFilters() {
        final Dataset<Row> df = getSparkDataframe() //
                .select("column_string", "column_boolean") //
                .filter(functions.col("column_boolean").equalTo(false)) //
                .filter(functions.col("column_double").gt(0.00));
        assertThat(df.collectAsList(), contains(RowFactory.create("one", false), RowFactory.create("two", false)));
    }

}

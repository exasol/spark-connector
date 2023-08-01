package com.exasol.spark.s3;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.logging.Logger;

import org.apache.spark.sql.*;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.dbbuilder.dialects.Table;
import com.exasol.logging.CapturingLogHandler;

@Tag("integration")
@Testcontainers
class S3ColumnSelectionIT extends S3IntegrationTestSetup {
    private static Table table;
    private final CapturingLogHandler capturingLogHandler = new CapturingLogHandler();

    @BeforeAll
    static void setupAll() {
        table = exasolSchema.createTableBuilder("table_for_column_selection") //
                .column("c1", "VARCHAR(10)") //
                .column("c2", "DECIMAL(18,0)") //
                .build() //
                .insert("one", 314) //
                .insert("two", 272) //
                .insert("three", 1337);
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

    @Test
    void testSelectStar() {
        final Dataset<Row> df = spark.read() //
                .format("exasol-s3") //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load();

        assertAll(
                () -> assertThat(df.collectAsList(),
                        contains(RowFactory.create("one", 314), RowFactory.create("two", 272),
                                RowFactory.create("three", 1337))),
                () -> assertThat(this.capturingLogHandler.getCapturedData(),
                        containsString("SELECT * FROM \"DEFAULT_SCHEMA\".\"table_for_column_selection\"")));
    }

    @Test
    void testSelectColumn() {
        final Dataset<String> df = spark.read() //
                .format("exasol-s3") //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load() //
                .select("c1") //
                .as(Encoders.STRING());

        assertAll(() -> assertThat(df.collectAsList(), contains("one", "two", "three")),
                () -> assertThat(df.queryExecution().toString(), containsString("Project [c1")),
                () -> assertThat(this.capturingLogHandler.getCapturedData(),
                        containsString("SELECT \"c1\" FROM \"DEFAULT_SCHEMA\".\"table_for_column_selection\"")));
    }

}

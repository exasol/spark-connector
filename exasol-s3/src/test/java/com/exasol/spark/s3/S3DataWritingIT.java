package com.exasol.spark.s3;

import static com.exasol.matcher.ResultSetStructureMatcher.table;
import static com.exasol.matcher.TypeMatchMode.NO_JAVA_TYPE_CHECK;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.date_format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.sql.*;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.dbbuilder.dialects.Table;

@Tag("integration")
@Testcontainers
class S3DataWritingIT extends S3IntegrationTestSetup {

    private final String format = "exasol-s3";

    private <T> Dataset<T> getDataset(final List<T> values, final Encoder<T> encoder) {
        return spark.createDataset(values, encoder);
    }

    private <T> void verify(final List<T> values, final Encoder<T> encoder, final Table table,
            final Matcher<ResultSet> matcher) throws SQLException {
        getDataset(values, encoder) //
                .write() //
                .mode("append") //
                .format(this.format) //
                .options(getSparkOptions()) //
                .option("table", table.getFullyQualifiedName()) //
                .save();
        verifyResultSet(table.getFullyQualifiedName(), matcher);
    }

    private void verifyResultSet(final String tableName, final Matcher<ResultSet> matcher) throws SQLException {
        final String query = "SELECT * FROM " + tableName + " ORDER BY \"c1\" ASC";
        try (final ResultSet result = connection.createStatement().executeQuery(query)) {
            assertThat(result, matcher);
        }
    }

    @Test
    void testThrowsWhenSavingIfNumberOfPartitionsExceedsMaximumAllowed() {
        final Table table = exasolSchema.createTable("table_write_integer_throws", "c1", "INTEGER");
        final DataFrameWriter<Integer> df = getDataset(Arrays.asList(1, 2), Encoders.INT()) //
                .write() //
                .mode("append") //
                .format(this.format) //
                .options(getSparkOptions()) //
                .option("numPartitions", "1351") //
                .option("table", table.getFullyQualifiedName());
        final ExasolValidationException exception = assertThrows(ExasolValidationException.class, () -> df.save());
        assertThat(exception.getMessage(), startsWith("E-SEC-23"));
    }

    @Test
    void testThrowsIfTableParameterIsNotSet() {
        final DataFrameWriter<Integer> df = getDataset(Arrays.asList(1, 2), Encoders.INT()) //
                .write() //
                .mode("append") //
                .format(this.format) //
                .options(getSparkOptions());
        final ExasolValidationException exception = assertThrows(ExasolValidationException.class, () -> df.save());
        assertThat(exception.getMessage(), startsWith("E-SEC-19"));
    }

    @Test
    void testWriteBoolean() throws SQLException {
        final Table table = exasolSchema.createTable("table_write_bool", "c1", "BOOLEAN");
        verify(Arrays.asList(true, false), Encoders.BOOLEAN(), table, table().row(false).row(true).matches());
    }

    @Test
    void testWriteInteger() throws SQLException {
        final Table table = exasolSchema.createTable("table_write_integer", "c1", "INTEGER");
        verify(Arrays.asList(-1, 0, 10, Integer.MIN_VALUE, Integer.MAX_VALUE), Encoders.INT(), table, //
                table().row(Integer.MIN_VALUE).row(-1).row(0).row(10).row(Integer.MAX_VALUE)
                        .matches(NO_JAVA_TYPE_CHECK));
    }

    @Test
    void testWriteLong() throws SQLException {
        final Table table = exasolSchema.createTable("table_write_long", "c1", "DECIMAL(36,0)");
        verify(Arrays.asList(-1L, 0L, 1L, Long.MIN_VALUE, Long.MAX_VALUE), Encoders.LONG(), table, //
                table().row(Long.MIN_VALUE).row(-1L).row(0L).row(1L).row(Long.MAX_VALUE).matches(NO_JAVA_TYPE_CHECK));
    }

    @Test
    void testWriteDouble() throws SQLException {
        final Table table = exasolSchema.createTable("table_write_double", "c1", "DOUBLE");
        verify(Arrays.asList(2.72, 3.14), Encoders.DOUBLE(), table, table().row(2.72).row(3.14).matches());
    }

    @Test
    void testWriteFloat() throws SQLException {
        final Table table = exasolSchema.createTable("table_write_float", "c1", "FLOAT");
        verify(Arrays.asList(0.72F, 1.11F), Encoders.FLOAT(), table, table().row(0.72).row(1.11).matches());
    }

    @Test
    void testWriteBigDecimal() throws SQLException {
        final Table table = exasolSchema.createTable("table_write_bigdecimal", "c1", "DECIMAL(10,3)");
        verify(Arrays.asList(new BigDecimal("12.172"), new BigDecimal("113.014")), Encoders.DECIMAL(), table,
                table().row(12.172).row(113.014).matches(NO_JAVA_TYPE_CHECK));
    }

    @Test
    void testWriteString() throws SQLException {
        final Table table = exasolSchema.createTable("table_write_string", "c1", "VARCHAR(5)");
        verify(Arrays.asList("xyz", "abc"), Encoders.STRING(), table, table().row("abc").row("xyz").matches());
    }

    @Test
    void testWriteDate() throws SQLException {
        final Date date1 = Date.valueOf("2022-05-10");
        final Date date2 = Date.valueOf("2022-05-20");
        final Table table = exasolSchema.createTable("table_write_date", "c1", "DATE");
        verify(Arrays.asList(date1, date2), Encoders.DATE(), table, table().row(date1).row(date2).matches());
    }

    @Test
    void testWriteTimestamp() throws SQLException {
        spark.conf().set("spark.sql.session.timeZone", "UTC");
        final Timestamp ts1 = Timestamp.from(java.time.Instant.EPOCH);
        final Timestamp ts2 = new Timestamp(System.currentTimeMillis());
        final Table table = exasolSchema.createTable("table_write_timestamp", "c1", "TIMESTAMP");
        getDataset(Arrays.asList(ts1, ts2), Encoders.TIMESTAMP()) //
                .withColumn("value", date_format(col("value"), "yyyy-MM-dd HH:mm:ss.SSS")) //
                .write() //
                .mode("append") //
                .format(this.format) //
                .options(getSparkOptions()) //
                .option("table", table.getFullyQualifiedName()) //
                .save();
        verifyResultSet(table.getFullyQualifiedName(), table().withUtcCalendar().row(ts1).row(ts2).matches());
    }

}

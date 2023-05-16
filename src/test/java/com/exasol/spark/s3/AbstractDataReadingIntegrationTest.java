package com.exasol.spark.s3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import java.util.*;
import java.util.stream.Stream;

import org.apache.spark.api.java.function.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.exasol.dbbuilder.dialects.Table;

abstract class AbstractDataReadingIntegrationTest extends S3IntegrationTestSetup {

    private static Table table;
    private final String format;

    public AbstractDataReadingIntegrationTest(final String format) {
        this.format = format;
    }

    abstract public Map<String, String> getSparkOptions();

    @BeforeAll
    static void dataReaderSetup() {
        table = exasolSchema.createTableBuilder("table_transformation") //
                .column("c1", "SMALLINT") //
                .build() //
                .bulkInsert(Stream.of(1, 2, 3, 4, 5, 6).map(n -> Arrays.asList(n)));
    }

    @Test
    void testDataFrameShow() {
        final Dataset<Row> df = spark.read() //
                .format(this.format) //
                .option("query", "SELECT * FROM " + table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load();
        df.show();
        assertThat(df.count(), equalTo(6L));
    }

    @Test
    void testProvidedSchema() {
        final StructType expectedSchema = StructType.fromDDL("col_str STRING");
        final StructType schema = spark.read() //
                .schema(expectedSchema) //
                .format(this.format) //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load() //
                .schema();
        assertThat(schema, equalTo(expectedSchema));
    }

    @Test
    void testMapTransformation() {
        final Dataset<Integer> df = spark.read() //
                .format(this.format) //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load() //
                .map((MapFunction<Row, Integer>) row -> {
                    return row.getInt(0) * 2;
                }, Encoders.INT());
        assertThat(df.collectAsList(), contains(2, 4, 6, 8, 10, 12));
    }

    @Test
    void testMapPartitionsTransformation() {
        final Dataset<String> df = spark.read() //
                .format(this.format) //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load() //
                .mapPartitions((MapPartitionsFunction<Row, String>) it -> {
                    List<String> result = new ArrayList<>();
                    while (it.hasNext()) {
                        result.add(String.valueOf(it.next().getInt(0)));
                    }
                    return result.iterator();
                }, Encoders.STRING());
        assertThat(df.collectAsList(), contains("1", "2", "3", "4", "5", "6"));
    }

    @Test
    void testFlatMapTransformation() {
        final Dataset<Integer> df = spark.read() //
                .format(this.format) //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load() //
                .flatMap((FlatMapFunction<Row, Integer>) row -> {
                    return Arrays.asList(row.getInt(0), row.getInt(0)).iterator();
                }, Encoders.INT());
        assertThat(df.collectAsList(), contains(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6));
    }

    @Test
    void testFilterTransformation() {
        final Dataset<Integer> df = spark.read() //
                .format(this.format) //
                .option("table", table.getFullyQualifiedName()) //
                .options(getSparkOptions()) //
                .load() //
                .filter((FilterFunction<Row>) row -> (row.getInt(0) % 2) == 0 ? true : false) //
                .map((MapFunction<Row, Integer>) row -> row.getInt(0), Encoders.INT());
        assertThat(df.collectAsList(), contains(2, 4, 6));
    }

}

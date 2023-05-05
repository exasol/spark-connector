package com.exasol.spark.s3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.dbbuilder.dialects.Table;

@Tag("integration")
@Testcontainers
class SchemaInferenceIT extends S3IntegrationTestSetup {

    @Test
    void testSparkWorks() {
        final StructType schema = new StructType() //
                .add("col_str", DataTypes.StringType, false) //
                .add("col_int", DataTypes.IntegerType, false);
        final Dataset<Row> df = spark.createDataFrame(Arrays.asList(RowFactory.create("value", 10)), schema);
        df.show();
        assertThat(df.count(), equalTo(1L));
    }

    @Test
    void testInferSchemaFromExasolTable() {
        final Table table = exasolSchema.createTable("multi_column_table", //
                Arrays.asList("c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8"), //
                Arrays.asList("VARCHAR(10)", "INTEGER", "DATE", "DOUBLE", "BOOLEAN", "TIMESTAMP", "DECIMAL(18,2)", "CHAR(255)"));
        final StructType expectedSchema = new StructType() //
                .add("c1", DataTypes.StringType, true) //
                .add("c2", DataTypes.LongType, true) //
                .add("c3", DataTypes.DateType, true) //
                .add("c4", DataTypes.DoubleType, true) //
                .add("c5", DataTypes.BooleanType, true) //
                .add("c6", DataTypes.TimestampType, true) //
                .add("c7", DataTypes.createDecimalType(18, 2), true) //
                .add("c8", DataTypes.StringType, true);
        final StructType schema = spark.read() //
                .format("exasol-s3") //
                .option("query", "SELECT * FROM " + table.getFullyQualifiedName()) //
                .options(getOptionsMap()) //
                .load() //
                .schema();
        assertThat(schema, equalTo(expectedSchema));
    }


}

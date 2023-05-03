package com.exasol.spark.s3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.spark.sql.connector.catalog.SupportsRead;
import org.apache.spark.sql.connector.catalog.SupportsWrite;
import org.apache.spark.sql.connector.catalog.TableCapability;
import org.apache.spark.sql.connector.read.ScanBuilder;
import org.apache.spark.sql.connector.write.LogicalWriteInfo;
import org.apache.spark.sql.connector.write.WriteBuilder;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

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
        this.capabilities = new HashSet<>(Arrays.asList(TableCapability.BATCH_READ, TableCapability.BATCH_WRITE));
    }

    @Override
    public String name() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ExasolS3Table[")
            .append("schema='" + this.schema().toString())
            .append("',")
            .append("capabilities='" + this.capabilities().toString())
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
        return null;
    }

    @Override
    public WriteBuilder newWriteBuilder(final LogicalWriteInfo defaultInfo) {
        return null;
    }

}

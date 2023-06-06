package com.exasol.spark.s3;

import org.apache.spark.sql.connector.write.BatchWrite;
import org.apache.spark.sql.connector.write.Write;
import org.apache.spark.sql.connector.write.WriteBuilder;

import com.exasol.spark.common.ExasolOptions;

/**
 * A delegating {@link WriteBuilder} class.
 */
public class DelegatingWriteBuilder implements WriteBuilder {
    private final ExasolOptions options;
    private final WriteBuilder delegate;

    /**
     * Creates a new instance of {@link DelegatingWriteBuilder}.
     *
     * @param options  user provided options
     * @param delegate delegate write builder
     */
    public DelegatingWriteBuilder(final ExasolOptions options, final WriteBuilder delegate) {
        this.options = options;
        this.delegate = delegate;
    }

    @Override
    public Write build() {
        return new Write() {
            @Override
            public BatchWrite toBatch() {
                return new ExasolBatchWrite(options, delegate.build());
            }

        };
    }

}

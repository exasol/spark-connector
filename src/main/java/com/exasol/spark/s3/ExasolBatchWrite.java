package com.exasol.spark.s3;

import static com.exasol.spark.s3.Constants.INTERMEDIATE_DATA_PATH;

import java.util.logging.Logger;

import org.apache.spark.sql.connector.write.*;

/**
 * An Exasol {@link BatchWrite} implementation.
 */
public class ExasolBatchWrite implements BatchWrite {
    private static final Logger LOGGER = Logger.getLogger(ExasolBatchWrite.class.getName());

    private final ExasolOptions options;
    private final BatchWrite delegate;

    /**
     * Creates a new instance of {@link ExasolBatchWrite}.
     *
     * @param options  user provided options
     * @param delegate delegate {@code CSV} batch write
     */
    public ExasolBatchWrite(final ExasolOptions options, final Write delegate) {
        this.options = options;
        this.delegate = delegate.toBatch();
    }

    @Override
    public DataWriterFactory createBatchWriterFactory(final PhysicalWriteInfo info) {
        return delegate.createBatchWriterFactory(info);
    }

    @Override
    public boolean useCommitCoordinator() {
        return delegate.useCommitCoordinator();
    }

    @Override
    public void commit(final WriterCommitMessage[] messages) {
        LOGGER.info("Committing the file writing stage of the job.");
        delegate.commit(messages);
        // Prepare intermediate data
    }

    @Override
    public void abort(final WriterCommitMessage[] messages) {
        LOGGER.info("Running abort stage of the job.");
        cleanup();
        delegate.abort(messages);
    }

    private void cleanup() {
        LOGGER.info(() -> "Running cleanup process for directory '" + this.options.get(INTERMEDIATE_DATA_PATH) + "'.");
    }

}

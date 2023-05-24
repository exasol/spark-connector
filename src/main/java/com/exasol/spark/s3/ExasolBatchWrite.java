package com.exasol.spark.s3;

import static com.exasol.spark.s3.Constants.INTERMEDIATE_DATA_PATH;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.sql.connector.write.*;

import com.exasol.errorreporting.ExaError;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

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
    public void abort(final WriterCommitMessage[] messages) {
        LOGGER.info("Running abort stage of the job.");
        cleanup();
        delegate.abort(messages);
    }

    private void cleanup() {
        LOGGER.info(() -> "Running cleanup process for directory '" + this.options.get(INTERMEDIATE_DATA_PATH) + "'.");
    }

    @Override
    public void commit(final WriterCommitMessage[] messages) {
        LOGGER.info("Committing the file writing stage of the job.");
        delegate.commit(messages);
        prepareIntermediateData();
    }

    private void prepareIntermediateData() {
        final long start = System.currentTimeMillis();
        final String table = this.options.getTable();
        final String query = new S3ImportQueryGenerator(options).generateQuery();
        try (final Connection connection = new ExasolConnectionFactory(this.options).getConnection();
                final Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false);
            final int rows = stmt.executeUpdate(query);
            connection.commit();
            final long time = System.currentTimeMillis() - start;
            LOGGER.info(() -> "Imported '" + rows + "' rows into the table '" + table + "' in '" + time + "' millis.");
        } catch (final SQLException exception) {
            throw new ExasolConnectionException(ExaError.messageBuilder("E-SEC-24")
                    .message("Failure running the import {{query}} query.", removeIdentifiedByPart(query))
                    .mitigation("Please check that connection address, username and password are correct.").toString(),
                    exception);
        } finally {
            cleanup();
        }
    }

    private static String removeIdentifiedByPart(final String input) {
        return Stream.of(input.split("\n")).filter(s -> !s.contains("IDENTIFIED BY")).collect(Collectors.joining("\n"));
    }

    /**
     * A class that generates {@code SQL} query for importing data from intermediate {@code S3} location into Exasol
     * database.
     */
    private static class S3ImportQueryGenerator extends AbstractImportExportQueryGenerator {

        public S3ImportQueryGenerator(final ExasolOptions options) {
            super(options);
        }

        public String generateQuery() {
            final String table = this.options.getTable();
            return new StringBuilder() //
                    .append("IMPORT INTO ") //
                    .append(table) //
                    .append(" FROM CSV\n") //
                    .append(getIdentifier()) //
                    .append(getFiles()) //
                    .append(getFooter()) //
                    .toString();
        }

        private String getFiles() {
            final String path = this.options.get(INTERMEDIATE_DATA_PATH);
            final URI pathURI = getPathURI(path);
            final String bucketName = pathURI.getHost();
            final String bucketKey = pathURI.getPath().substring(1);
            final S3ClientFactory s3ClientFactory = new S3ClientFactory(this.options);
            try (final S3Client s3Client = s3ClientFactory.getS3Client()) {
                final List<S3Object> objects = listObjects(s3Client, bucketName, bucketKey);
                final StringBuilder builder = new StringBuilder();
                for (final S3Object object : objects) {
                    builder.append("FILE '").append(object.key()).append("'\n");
                }
                return builder.toString();
            }
        }

        private URI getPathURI(final String path) {
            try {
                return new URI(path);
            } catch (final URISyntaxException exception) {
                throw new ExasolValidationException(ExaError.messageBuilder("E-SEC-25")
                        .message("Provided path {{path}} cannot be converted to URI systax.", path)
                        .mitigation("Please make sure the path is correct file system (hdfs, s3a, etc) path.")
                        .toString(), exception);
            }
        }

        private List<S3Object> listObjects(final S3Client s3Client, final String bucketName, final String bucketKey) {
            final ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder().bucket(bucketName)
                    .prefix(bucketKey).build();
            final List<S3Object> result = new ArrayList<>();
            for (final ListObjectsV2Response page: s3Client.listObjectsV2Paginator(listObjectsRequest)) {
                for (final S3Object s3Object: page.contents()) {
                    result.add(s3Object);
                }
            }
            return result;
        }

        private String getFooter() {
            return "SKIP = 1";
        }
    }

}

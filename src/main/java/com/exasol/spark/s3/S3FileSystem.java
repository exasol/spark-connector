package com.exasol.spark.s3;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.exasol.errorreporting.ExaError;
import com.exasol.spark.common.ExasolOptions;

import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

/**
 * An S3 file system operations implementations.
 */
public final class S3FileSystem implements Closeable {
    private static final Logger LOGGER = Logger.getLogger(S3FileSystem.class.getName());
    private final S3Client s3Client;

    /**
     * Creates a new instance of {@link S3FileSystem}.
     *
     * @param s3Client s3 client object
     */
    public S3FileSystem(final S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * Creates a new instance of {@link S3FileSystem} from {@link ExasolOptions} options.
     *
     * @param options user provided options
     * @return new instance of {@link S3FileSystem}
     */
    public static S3FileSystem fromOptions(final ExasolOptions options) {
        return new S3FileSystem(new S3ClientFactory(options).getS3Client());
    }

    /**
     * Checks if a given bucket exists.
     *
     * @param bucketName name of a bucket
     * @return {@code true} if bucket exists, {@code false} otherwise
     */
    public boolean doesBucketExist(final String bucketName) {
        try {
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
            return true;
        } catch (final NoSuchBucketException exception) {
            return false;
        }
    }

    /**
     * Deletes a given bucket.
     *
     * @param bucketName name of a bucket
     */
    public void deleteBucket(final String bucketName) {
        LOGGER.info(() -> "Deleting S3 bucket '" + bucketName + "'.");
        deleteObjects(bucketName, Optional.empty());
    }

    /**
     * For a bucket with given name: delete all contents with the specified key.
     *
     * @param bucketName name of a bucket
     * @param bucketKey  bucket key value
     */
    public void deleteKeys(final String bucketName, final String bucketKey) {
        LOGGER.info(() -> "Deleting objects in S3 bucket '" + bucketName + "' with bucket key '" + bucketKey + "'.");
        deleteObjects(bucketName, Optional.of(bucketKey));
    }

    private void deleteObjects(final String bucketName, final Optional<String> bucketKey) {
        try {
            final List<S3Object> objects = listObjects(bucketName, bucketKey);
            List<ObjectIdentifier> objectIdentifiers = objects.stream() //
                    .map(object -> ObjectIdentifier.builder().key(object.key()).build()) //
                    .collect(Collectors.toList());
            deleteObjectIdentifiers(bucketName, objectIdentifiers);
        } catch (final SdkClientException exception) {
            throw new ExasolConnectionException(
                    ExaError.messageBuilder("E-SEC-27")
                            .message("Failed to delete objects in {{BUCKET}} with key {{KEY}}.", bucketName,
                                    bucketKey.orElse("emptyBucketKey"))
                            .mitigation("Please check that credentials and bucket name are correct.").toString(),
                    exception);
        } catch (final S3Exception exception) {
            throw new ExasolConnectionException(ExaError.messageBuilder("E-SEC-28")
                    .message("Failed to delete objects in {{BUCKET}} with key {{KEY}} because of unexpected S3 exception.")
                    .parameter("BUCKET", bucketName).parameter("KEY", bucketKey.orElse("emptyBucketKey"))
                    .ticketMitigation().toString(), exception);
        }
    }

    private List<S3Object> listObjects(final String bucketName, final Optional<String> bucketKey) {
        final List<S3Object> result = new ArrayList<>();
        String continuationToken = null;
        while (true) {
            final ListObjectsV2Request.Builder builder = ListObjectsV2Request.builder().bucket(bucketName);
            if (bucketKey.isPresent()) {
                builder.prefix(bucketKey.get());
            }
            if (continuationToken != null) {
                builder.continuationToken(continuationToken);
            }
            final ListObjectsV2Response response = s3Client.listObjectsV2(builder.build());
            final boolean isTruncated = response.isTruncated();
            for (final S3Object s3Object : response.contents()) {
                result.add(s3Object);
            }
            if (!isTruncated) {
                break;
            }
            continuationToken = response.nextContinuationToken();
        }
        return result;
    }

    private void deleteObjectIdentifiers(final String bucketName, final List<ObjectIdentifier> objectIdentifiers) {
        if (!objectIdentifiers.isEmpty()) {
            DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder() //
                    .bucket(bucketName) //
                    .delete(Delete.builder().objects(objectIdentifiers).build()) //
                    .build();
            s3Client.deleteObjects(deleteObjectsRequest);
        }
    }

    @Override
    public void close() {
        this.s3Client.close();
    }

}

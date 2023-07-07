package com.exasol.spark.s3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.spark.sql.connector.write.LogicalWriteInfo;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exasol.spark.common.ExasolOptions;
import com.exasol.spark.common.ExasolValidationException;

import io.netty.util.internal.ThreadLocalRandom;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@ExtendWith(MockitoExtension.class)
class ExasolWriteBuilderProviderIT extends S3IntegrationTestSetup {
    static final String s3BucketKey = "testS3BucketKey";
    final String applicationId = "spark-test-app-id";

    final S3BucketKeyPathProvider s3BucketKeyPathProvider = new TestS3BucketKeyPathProvider(applicationId);
    final ExasolOptions options = ExasolOptions.from(new CaseInsensitiveStringMap(getMapWithTable()));

    @Mock
    private StructType schema;

    @AfterEach
    void afterEach() {
        try (final S3FileSystem s3FileSystem = S3FileSystem.fromOptions(options)) {
            s3FileSystem.deleteKeys(DEFAULT_BUCKET_NAME, s3BucketKey + "-" + applicationId);
        }
    }

    @Test
    void testValidateWritePathWhenEmpty() {
        final ExasolWriteBuilderProvider writeBuilderProvider = new ExasolWriteBuilderProvider(options,
                s3BucketKeyPathProvider);
        assertDoesNotThrow(() -> writeBuilderProvider.createWriteBuilder(schema, getLogicalWriteInfo("queryId1")));
    }

    @Test
    void testThrowsValidateWritePathWhenNotEmpty() throws IOException {
        final String bucketKeyPrefix = s3BucketKey + "-" + applicationId + "/queryIdTest";
        PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(DEFAULT_BUCKET_NAME)
                .key(bucketKeyPrefix + "/hello.txt").build();
        s3Client.putObject(objectRequest, RequestBody.fromByteBuffer(getRandomByteBuffer(10)));
        final ExasolWriteBuilderProvider writeBuilderProvider = new ExasolWriteBuilderProvider(options,
                s3BucketKeyPathProvider);
        final LogicalWriteInfo info = getLogicalWriteInfo("queryIdTest");
        assertThrows(ExasolValidationException.class, () -> writeBuilderProvider.createWriteBuilder(schema, info));
    }

    private Map<String, String> getMapWithTable() {
        final Map<String, String> map = new HashMap<>(getSparkOptions());
        map.put("table", "T1");
        return map;
    }

    private static ByteBuffer getRandomByteBuffer(int size) throws IOException {
        byte[] b = new byte[size];
        ThreadLocalRandom.current().nextBytes(b);
        return ByteBuffer.wrap(b);
    }

    private LogicalWriteInfo getLogicalWriteInfo(final String queryId) {
        return new LogicalWriteInfo() {
            @Override
            public String queryId() {
                return queryId;
            }

            @Override
            public StructType schema() {
                return schema;
            }

            @Override
            public CaseInsensitiveStringMap options() {
                return new CaseInsensitiveStringMap(Collections.emptyMap());
            }
        };
    }

    private static class TestS3BucketKeyPathProvider implements S3BucketKeyPathProvider {
        private final String appId;

        public TestS3BucketKeyPathProvider(final String appId) {
            this.appId = appId;
        }

        @Override
        public String getS3BucketKeyForWriteLocation(final String queryId) {
            return s3BucketKey + "-" + this.appId + "/" + queryId;
        }
    }

}

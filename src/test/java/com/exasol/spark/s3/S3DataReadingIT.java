package com.exasol.spark.s3;

import java.util.Map;

import org.junit.jupiter.api.Tag;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.junit.jupiter.Testcontainers;

@Tag("integration")
@Testcontainers
class S3DataReadingIT extends AbstractDataReadingIntegrationTest {

    public S3DataReadingIT() {
        super("exasol-s3");
    }

    @Override
    public Map<String, String> getSparkOptions() {
        final String endpointOverride = DockerClientFactory.instance().dockerHostIpAddress() + ":"
                + S3.getMappedPort(4566);
        final Map<String, String> options = getOptionsMap();
        options.put("awsAccessKeyId", S3.getAccessKey());
        options.put("awsSecretAccessKey", S3.getSecretKey());
        options.put("awsCredentialsProvider", "SimpleAWSCredentialsProvider");
        options.put("awsRegion", S3.getRegion());
        options.put("s3Bucket", DEFAULT_BUCKET_NAME);
        options.put("s3PathStyleAccess", "true");
        options.put("awsEndpointOverride", endpointOverride);
        options.put("useSsl", "false");
        options.put("numPartitions", "3");
        options.put("replaceLocalhostByDefaultS3Endpoint", "true");
        return options;
    }

}

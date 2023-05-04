package com.exasol.spark.s3;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import com.exasol.spark.BaseIntegrationSetup;

// import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
// import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
// import software.amazon.awssdk.regions.Region;
// import software.amazon.awssdk.services.s3.S3Client;
// import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
// import software.amazon.awssdk.services.s3.model.S3Object;

/**
 * An integration test class with {@link LocalStackContainer} S3 setup.
 */
public class S3IntegrationTestSetup extends BaseIntegrationSetup {
    private static final Logger LOGGER = Logger.getLogger(S3IntegrationTestSetup.class.getName());
    protected static final String DEFAULT_BUCKET_NAME = "csvtest";

    @Container
    private static final LocalStackContainer S3 = new LocalStackContainer(
            DockerImageName.parse("localstack/localstack:1.4")).withServices(Service.S3);

    // protected static S3Client s3Client;

    @BeforeAll
    public static void setup() throws SQLException {
        LOGGER.info(() -> "Created localstack S3 client with region '" + S3.getRegion() + "'.");
        // s3Client = S3Client.builder() //
        //         .endpointOverride(S3.getEndpointOverride(Service.S3)) //
        //         .credentialsProvider(StaticCredentialsProvider
        //                 .create(AwsBasicCredentials.create(S3.getAccessKey(), S3.getSecretKey()))) //
        //         .region(Region.of(S3.getRegion())) //
        //         .build();
        // updateExasolContainerHostsFile();
        createBucket(DEFAULT_BUCKET_NAME);
    }

    public static void createBucket(final String bucketName) {
        LOGGER.info(() -> "Creating S3 bucket '" + bucketName + "'.");
        // s3Client.createBucket(b -> b.bucket(bucketName));
    }

}

package com.exasol.spark.s3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import com.exasol.spark.BaseIntegrationSetup;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * An integration test class with {@link LocalStackContainer} S3 setup.
 */
public abstract class S3IntegrationTestSetup extends BaseIntegrationSetup {
    private static final Logger LOGGER = Logger.getLogger(S3IntegrationTestSetup.class.getName());
    protected static final String DEFAULT_BUCKET_NAME = "csvtest";

    @Container
    protected static final LocalStackContainer S3 = new LocalStackContainer(
            DockerImageName.parse("localstack/localstack:2.0")) //
            .withServices(Service.S3) //
            .withReuse(true);

    protected static S3Client s3Client;

    @BeforeAll
    public static void setup() throws SQLException {
        LOGGER.info(() -> "Created localstack S3 client with region '" + S3.getRegion() + "'.");
        s3Client = S3Client.builder() //
                .endpointOverride(S3.getEndpointOverride(Service.S3)) //
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(S3.getAccessKey(), S3.getSecretKey()))) //
                .region(Region.of(S3.getRegion())) //
                .build();
        updateHostsFileInExasol();
        createBucket(DEFAULT_BUCKET_NAME);
    }

    public static void createBucket(final String bucketName) {
        LOGGER.info(() -> "Creating S3 bucket '" + bucketName + "'.");
        s3Client.createBucket(b -> b.bucket(bucketName));
    }

    private static void updateHostsFileInExasol() {
        final List<String> commands = Arrays.asList( //
                "sed -i '/amazonaws/d' /etc/hosts", //
                "echo '" + getS3ContainerInternalIp() + " csvtest.s3.amazonaws.com' >> /etc/hosts" //
        );
        commands.forEach(command -> {
            try {
                final ExecResult exitCode = EXASOL.execInContainer("/bin/sh", "-c", command);
                if (exitCode.getExitCode() != 0) {
                    throw new RuntimeException(
                            "Command to update Exasol container `/etc/hosts` file returned non-zero result.");
                }
            } catch (final InterruptedException | IOException exception) {
                throw new RuntimeException("Failed to update Exasol container `/etc/hosts`.", exception);
            }
        });
    }

    private static String getS3ContainerInternalIp() {
        return S3.getContainerInfo().getNetworkSettings().getNetworks().values().iterator().next().getGateway();
    }

}

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

import com.exasol.containers.ExasolContainer;
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
    private static final String HOSTS_FILE = "/etc/hosts";
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
        redirectIpAddress(EXASOL, "csvtest.s3.amazonaws.com", getS3ContainerInternalIp());
        createBucket(DEFAULT_BUCKET_NAME);
    }

    public static void createBucket(final String bucketName) {
        LOGGER.info(() -> "Creating S3 bucket '" + bucketName + "'.");
        s3Client.createBucket(b -> b.bucket(bucketName));
    }

    private static void redirectIpAddress(final ExasolContainer<?> exasolContainer, final String original,
            final String redirect) {
        final List<String> commands = Arrays.asList( //
                "sed -i '/amazonaws/d' " + HOSTS_FILE, //
                "echo '" + redirect + " " + original + "' >> " + HOSTS_FILE);
        commands.forEach(command -> {
            try {
                final ExecResult exitCode = exasolContainer.execInContainer("/bin/sh", "-c", command);
                if (exitCode.getExitCode() != 0) {
                    throw new RuntimeException(
                            "Command to update Exasol container '" + HOSTS_FILE + "' file returned non-zero result.");
                }
            } catch (final InterruptedException | IOException exception) {
                throw new RuntimeException("Failed to update Exasol container '" + HOSTS_FILE + "'.", exception);
            }
        });
    }

    private static String getS3ContainerInternalIp() {
        return S3.getContainerInfo().getNetworkSettings().getNetworks().values().iterator().next().getGateway();
    }

}

package com.exasol.spark.s3;

import java.util.logging.Logger;

import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.TestcontainersConfiguration;

/**
 * Reusable version of {@code localstack} container.
 *
 * Using {@code .withReuse(true)} on main container does not work.
 */
public final class LocalstackS3WithReuse extends LocalStackContainer {
    private static final Logger LOGGER = Logger.getLogger(LocalstackS3WithReuse.class.getName());

    public LocalstackS3WithReuse(final DockerImageName dockerImageName) {
        super(dockerImageName);
        withServices(Service.S3);
        withReuse(true);
    }

    @Override
    public void stop() {
        if (this.isShouldBeReused() && TestcontainersConfiguration.getInstance().environmentSupportsReuse()) {
            LOGGER.warning("Leaving container running since reuse is enabled. Don't forget to stop and remove "
                    + "the container manually using docker rm -f CONTAINER_ID.");
        } else {
            super.stop();
        }
    }
}

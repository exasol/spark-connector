package com.exasol.spark.s3;

import static com.exasol.spark.s3.Constants.*;

import java.net.URI;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.*;

/**
 * A factory class that creates S3 clients.
 */
public final class S3ClientFactory {
    private final ExasolOptions options;

    /**
     * Creates a new instance of {@link S3ClientFactory}.
     *
     * @param options {@link ExasolOptions} options
     */
    public S3ClientFactory(final ExasolOptions options) {
        this.options = options;
    }

    /**
     * Creates a new AWS S3 client.
     *
     * @return new S3 client
     */
    public S3Client getS3Client() {
        final S3ClientBuilder builder = S3Client.builder() //
                .credentialsProvider(getCredentialsProvider());
        setRegionIfEnabled(builder);
        setPathStyleAccessIfEnabled(builder);
        setEndpointOverrideIfEnabled(builder);
        return builder.build();
    }

    private void setRegionIfEnabled(final S3BaseClientBuilder<?, ?> builder) {
        if (this.options.containsKey(AWS_REGION)) {
            builder.region(Region.of(this.options.get(AWS_REGION)));
        }
    }

    private AwsCredentialsProvider getCredentialsProvider() {
        final String awsAccessKeyId = this.options.get(AWS_ACCESS_KEY_ID);
        final String awsSecretAccessKey = this.options.get(AWS_SECRET_ACCESS_KEY);
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey));
    }

    private void setPathStyleAccessIfEnabled(final S3BaseClientBuilder<?, ?> builder) {
        if (this.options.hasEnabled(S3_PATH_STYLE_ACCESS)) {
            builder.serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build());
        }
    }

    private void setEndpointOverrideIfEnabled(final S3BaseClientBuilder<?, ?> builder) {
        if (this.options.containsKey(S3_ENDPOINT_OVERRIDE)) {
            builder.endpointOverride(URI.create(getEndpointOverride()));
        }
    }

    private String getEndpointOverride() {
        final String protocol = getProtocol();
        return protocol + "://s3." + this.options.get(S3_ENDPOINT_OVERRIDE);
    }

    private String getProtocol() {
        if (!this.options.containsKey(AWS_USE_SSL) || this.options.hasEnabled(AWS_USE_SSL)) {
            return "https";
        } else {
            return "http";
        }
    }

}

package com.exasol.spark.s3;

import java.net.URI;

import com.exasol.spark.common.ExasolOptions;
import com.exasol.spark.common.Option;

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
        if (this.options.containsKey(Option.AWS_REGION.key())) {
            builder.region(Region.of(this.options.get(Option.AWS_REGION.key())));
        }
    }

    private AwsCredentialsProvider getCredentialsProvider() {
        final String awsAccessKeyId = this.options.get(Option.AWS_ACCESS_KEY_ID.key());
        final String awsSecretAccessKey = this.options.get(Option.AWS_SECRET_ACCESS_KEY.key());
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey));
    }

    private void setPathStyleAccessIfEnabled(final S3BaseClientBuilder<?, ?> builder) {
        if (this.options.hasEnabled(Option.S3_PATH_STYLE_ACCESS.key())) {
            builder.serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build());
        }
    }

    private void setEndpointOverrideIfEnabled(final S3BaseClientBuilder<?, ?> builder) {
        if (this.options.containsKey(Option.S3_ENDPOINT_OVERRIDE.key())) {
            builder.endpointOverride(URI.create(getEndpointOverride()));
        }
    }

    private String getEndpointOverride() {
        final String protocol = getProtocol();
        return protocol + "://s3." + this.options.get(Option.S3_ENDPOINT_OVERRIDE.key());
    }

    private String getProtocol() {
        if (!this.options.containsKey(Option.AWS_USE_SSL.key()) || this.options.hasEnabled(Option.AWS_USE_SSL.key())) {
            return "https";
        } else {
            return "http";
        }
    }

}

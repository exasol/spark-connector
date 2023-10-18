package com.exasol.spark.s3;

import com.exasol.spark.common.ExasolOptions;
import com.exasol.spark.common.Option;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearEnvironmentVariable;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import static com.amazonaws.SDKGlobalConfiguration.ACCESS_KEY_ENV_VAR;
import static com.amazonaws.SDKGlobalConfiguration.SECRET_KEY_ENV_VAR;


public class BaseQueryGeneratorTest {
    private final Map<String, String> basic_params = Map.of(
            Option.TABLE.key(), "some_table",
            Option.S3_BUCKET.key(), "bucket");

    @Test
    @ClearEnvironmentVariable(key=ACCESS_KEY_ENV_VAR)
    @ClearEnvironmentVariable(key=SECRET_KEY_ENV_VAR)
    void testCredentialsEmptyOptions() {
        final ExasolOptions options = ExasolOptions.from(new CaseInsensitiveStringMap(basic_params));
        final BaseQueryGenerator generator = new BaseQueryGenerator(options);
        final Map.Entry<String, String> awsCreds = generator.getAWSCredentials();

        assertThat(awsCreds.getKey(), nullValue());
        assertThat(awsCreds.getValue(), nullValue());
    }

    @Test
    @SetEnvironmentVariable(key=ACCESS_KEY_ENV_VAR, value="env_key")
    @SetEnvironmentVariable(key=SECRET_KEY_ENV_VAR, value="env_sec")
    void testCredentialsExplicitOptions() {
        final Map<String, String> params = new HashMap<>(basic_params);
        params.put(Option.AWS_ACCESS_KEY_ID.key(), "access_key");
        params.put(Option.AWS_SECRET_ACCESS_KEY.key(), "secret_key");

        final ExasolOptions options = ExasolOptions.from(new CaseInsensitiveStringMap(params));
        final BaseQueryGenerator generator = new BaseQueryGenerator(options);
        final Map.Entry<String, String> awsCreds = generator.getAWSCredentials();

        assertThat(awsCreds.getKey(), equalTo("access_key"));
        assertThat(awsCreds.getValue(), equalTo("secret_key"));
    }

    @Test
    @SetEnvironmentVariable(key=ACCESS_KEY_ENV_VAR, value="env_key")
    @SetEnvironmentVariable(key=SECRET_KEY_ENV_VAR, value="env_sec")
    void testCredentialsFromEnvironment() {
        final ExasolOptions options = ExasolOptions.from(new CaseInsensitiveStringMap(basic_params));
        final BaseQueryGenerator generator = new BaseQueryGenerator(options);
        final Map.Entry<String, String> awsCreds = generator.getAWSCredentials();

        assertThat(awsCreds.getKey(), equalTo("env_key"));
        assertThat(awsCreds.getValue(), equalTo("env_sec"));
    }

    @Test
    @ClearEnvironmentVariable(key=ACCESS_KEY_ENV_VAR)
    @ClearEnvironmentVariable(key=SECRET_KEY_ENV_VAR)
    void testIdentifierNoCredentials() {
        final ExasolOptions options = ExasolOptions.from(new CaseInsensitiveStringMap(basic_params));
        final BaseQueryGenerator generator = new BaseQueryGenerator(options);
        assertThat(generator.getIdentifier(), equalTo("AT 'https://bucket.s3.amazonaws.com'\n"));
    }

    @Test
    @ClearEnvironmentVariable(key=ACCESS_KEY_ENV_VAR)
    @ClearEnvironmentVariable(key=SECRET_KEY_ENV_VAR)
    void testIdentifierOnlyUserProvided() {
        final Map<String, String> params = new HashMap<>(basic_params);
        params.put(Option.AWS_ACCESS_KEY_ID.key(), "access_key");

        final ExasolOptions options = ExasolOptions.from(new CaseInsensitiveStringMap(params));
        final BaseQueryGenerator generator = new BaseQueryGenerator(options);
        assertThat(generator.getIdentifier(), equalTo("AT 'https://bucket.s3.amazonaws.com' USER 'access_key'\n"));
    }

    @Test
    @ClearEnvironmentVariable(key=ACCESS_KEY_ENV_VAR)
    @ClearEnvironmentVariable(key=SECRET_KEY_ENV_VAR)
    void testIdentifierBothUserAndKey() {
        final Map<String, String> params = new HashMap<>(basic_params);
        params.put(Option.AWS_ACCESS_KEY_ID.key(), "access_key");
        params.put(Option.AWS_SECRET_ACCESS_KEY.key(), "secret_key");

        final ExasolOptions options = ExasolOptions.from(new CaseInsensitiveStringMap(params));
        final BaseQueryGenerator generator = new BaseQueryGenerator(options);
        assertThat(generator.getIdentifier(), equalTo("AT 'https://bucket.s3.amazonaws.com' USER 'access_key'" +
                " IDENTIFIED BY 'secret_key'\n"));
    }

    @Test
    @SetEnvironmentVariable(key=ACCESS_KEY_ENV_VAR, value="key")
    @SetEnvironmentVariable(key=SECRET_KEY_ENV_VAR, value="secret")
    void testIdentifierViaEnv() {
        final ExasolOptions options = ExasolOptions.from(new CaseInsensitiveStringMap(basic_params));
        final BaseQueryGenerator generator = new BaseQueryGenerator(options);
        assertThat(generator.getIdentifier(), equalTo("AT 'https://bucket.s3.amazonaws.com' USER 'key'" +
                " IDENTIFIED BY 'secret'\n"));
    }
}

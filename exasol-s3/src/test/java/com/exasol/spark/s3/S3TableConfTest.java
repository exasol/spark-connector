package com.exasol.spark.s3;

import com.exasol.spark.SparkSessionProvider;
import com.exasol.spark.common.Option;
import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.containsString;

import java.util.HashMap;
import java.util.Map;

class S3TableConfTest {
    private final StructType schema = new StructType(
            new StructField[] {
                    new StructField("f_str", DataTypes.StringType, true, Metadata.empty()),
                    new StructField("f_int", DataTypes.IntegerType, true, Metadata.empty()),
            }
    );
    private final Map<String, String> basic_params = Map.of(Option.TABLE.key(), "some_table");
    private static SparkSession spark;

    @BeforeEach
    public void beforeEach() {
        final SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .set("spark.ui.enabled", "false")
                .set("spark.driver.host", "localhost");
        spark = SparkSessionProvider.getSparkSession(conf);
    }

    @AfterEach
    public void afterEach() {
        spark.close();
    }

    @Test
    void testOptionsWithoutParams() {
        final ExasolS3Table s3Table = new ExasolS3Table(schema);
        s3Table.buildOptions(new CaseInsensitiveStringMap(basic_params));

        final Configuration conf = spark.sparkContext().hadoopConfiguration();
        assertThat(conf.get("fs.s3a.access.key"), nullValue());
        assertThat(conf.get("fs.s3a.secret.key"), nullValue());
    }

    @Test
    void testOptionsWithKeys() {
        final ExasolS3Table s3Table = new ExasolS3Table(schema);
        final Map<String, String> params = new HashMap<>(basic_params);
        params.put(Option.AWS_ACCESS_KEY_ID.key(), "some-key");
        params.put(Option.AWS_SECRET_ACCESS_KEY.key(), "secret-key");
        s3Table.buildOptions(new CaseInsensitiveStringMap(params));

        final Configuration conf = spark.sparkContext().hadoopConfiguration();
        assertThat(conf.get("fs.s3a.access.key"), equalTo("some-key"));
        assertThat(conf.get("fs.s3a.secret.key"), equalTo("secret-key"));
    }

    @Test
    void testNoDefaultCredentialsProvider() {
        final ExasolS3Table s3Table = new ExasolS3Table(schema);
        s3Table.buildOptions(new CaseInsensitiveStringMap(basic_params));

        final Configuration conf = spark.sparkContext().hadoopConfiguration();
        final String val = conf.get("fs.s3a.aws.credentials.provider");
        assertThat(val, containsString("org.apache.hadoop.fs.s3a.SimpleAWSCredentialsProvider"));
        assertThat(val, containsString("com.amazonaws.auth.EnvironmentVariableCredentialsProvider"));
        assertThat(val, containsString("org.apache.hadoop.fs.s3a.auth.IAMInstanceCredentialsProvider"));
    }

    @Test
    void testExplicitCredentialsProvider() {
        final ExasolS3Table s3Table = new ExasolS3Table(schema);
        final Map<String, String> params = new HashMap<>(basic_params);
        final String providerClass = "my-fancy-credentials-provider";
        params.put(Option.AWS_CREDENTIALS_PROVIDER.key(), providerClass);
        s3Table.buildOptions(new CaseInsensitiveStringMap(params));

        final Configuration conf = spark.sparkContext().hadoopConfiguration();
        assertThat(conf.get("fs.s3a.aws.credentials.provider"), equalTo(providerClass));
    }
}

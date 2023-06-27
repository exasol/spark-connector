package com.exasol.spark;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.junit.jupiter.Container;

import com.exasol.containers.ExasolContainer;
import com.exasol.dbbuilder.dialects.exasol.ExasolObjectFactory;
import com.exasol.dbbuilder.dialects.exasol.ExasolSchema;

/**
 * A base integration test class with Exasol docker container setup.
 */
public class BaseIntegrationSetup {
    private static final Logger LOGGER = Logger.getLogger(BaseIntegrationSetup.class.getName());
    private static final String DEFAULT_DOCKER_IMAGE = "8.18.1";

    @Container
    protected static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>(
            getExasolDockerImage()).withReuse(true);

    protected static Connection connection;
    protected static ExasolObjectFactory factory;
    protected static ExasolSchema exasolSchema;
    protected static SparkSession spark;

    @BeforeAll
    public static void beforeAll() throws SQLException {
        EXASOL.purgeDatabase();
        connection = EXASOL.createConnection();
        factory = new ExasolObjectFactory(connection);
        spark = SparkSessionProvider.getSparkSession(createSparkConfiguration());
        createExasolSchema("DEFAULT_SCHEMA");
    }

    @AfterAll
    public static void afterAll() throws SQLException {
        dropExasolSchema();
        connection.close();
        spark.close();
    }

    private static void createExasolSchema(final String exasolSchemaName) {
        LOGGER.fine(() -> "Creating a new Exasol schema '" + exasolSchemaName + '"');
        dropExasolSchema();
        exasolSchema = factory.createSchema(exasolSchemaName);
    }

    private static void dropExasolSchema() {
        if (exasolSchema != null) {
            LOGGER.fine(() -> "Dropping Exasol schema '" + exasolSchema.getName() + '"');
            exasolSchema.drop();
            exasolSchema = null;
        }
    }

    public Map<String, String> getOptionsMap() {
        final Map<String, String> map = Stream.of(new String[][] { //
                { "host", EXASOL.getDockerNetworkInternalIpAddress() }, //
                { "port", EXASOL.getMappedPort(8563) + "" }, //
                { "username", EXASOL.getUsername() }, //
                { "password", EXASOL.getPassword() }, //
                // { "jdbc_url", EXASOL.getJdbcUrl() }, //
                { "fingerprint", getFingerprint() }, //
        }).collect(Collectors.toMap(e -> e[0], e -> e[1]));
        LOGGER.fine(() -> "Prepared options '" + map.toString() + "'.");
        return map;
    }

    private String getFingerprint() {
        return EXASOL.getTlsCertificateFingerprint().get();
    }

    private static SparkConf createSparkConfiguration() {
        return new SparkConf() //
                .setMaster("local[*]") //
                .setAppName("Tests") //
                .set("spark.ui.enabled", "false") //
                .set("spark.app.id", getRandomAppId()) //
                .set("spark.driver.host", "localhost");
    }

    private static String getRandomAppId() {
        return "SparkAppID" + (int) (Math.random() * 1000 + 1);
    }

    private static String getExasolDockerImage() {
        return System.getProperty("com.exasol.dockerdb.image", DEFAULT_DOCKER_IMAGE);
    }

}

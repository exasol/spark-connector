package com.exasol.spark.s3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class ExasolOptionsTest {

    @Test
    void testWithDefaults() {
        final ExasolOptions options = ExasolOptions.builder().build();
        assertAll(() -> assertThat(options.getJdbcUrl(), equalTo("jdbc:exa:localhost:8563")),
                () -> assertThat(options.getUsername(), equalTo("sys")),
                () -> assertThat(options.getPassword(), equalTo("exasol")));
    }

    @Test
    void testGetJDBCUrl() {
        final ExasolOptions options = ExasolOptions.builder().jdbcUrl("jdbc:exa:127.0.0.1:6666").build();
        assertThat(options.getJdbcUrl(), equalTo("jdbc:exa:127.0.0.1:6666"));
    }

    @Test
    void testUsername() {
        final ExasolOptions options = ExasolOptions.builder().username("user").build();
        assertThat(options.getUsername(), equalTo("user"));
    }

    @Test
    void testPassword() {
        final ExasolOptions options = ExasolOptions.builder().password("pass").build();
        assertThat(options.getPassword(), equalTo("pass"));
    }

    @Test
    void testHasTableFalse() {
        assertAll(() -> assertThat(ExasolOptions.builder().build().hasTable(), equalTo(false)),
                () -> assertThat(ExasolOptions.builder().table("").build().hasTable(), equalTo(false)));
    }

    @Test
    void testHasTableTrue() {
        assertThat(ExasolOptions.builder().table("table").build().hasTable(), equalTo(true));
    }

    @Test
    void testGetTable() {
        assertThat(ExasolOptions.builder().table("table").build().getTable(), equalTo("table"));
    }

    @Test
    void testHasQueryFalse() {
        assertAll(() -> assertThat(ExasolOptions.builder().build().hasQuery(), equalTo(false)),
                () -> assertThat(ExasolOptions.builder().query("").build().hasQuery(), equalTo(false)));
    }

    @Test
    void testHasQueryTrue() {
        assertThat(ExasolOptions.builder().query("query").build().hasQuery(), equalTo(true));
    }

    @Test
    void testGetQuery() {
        assertThat(ExasolOptions.builder().query("query").build().getQuery(), equalTo("query"));
    }

    @Test
    void testGetTableOrQuery() {
        assertAll(() -> assertThat(ExasolOptions.builder().table("table").build().getTableOrQuery(), equalTo("table")),
                () -> assertThat(ExasolOptions.builder().query("query").build().getTableOrQuery(), equalTo("query")));
    }

    @Test
    void testNumberOfPartitionsDefault() {
        assertThat(ExasolOptions.builder().query("query").build().getNumberOfPartitions(), equalTo(8));
    }

    @Test
    void testNumberOfPartitionsProvided() {
        final Map<String, String> m = Stream.of(new String[][] { { Option.NUMBER_OF_PARTITIONS.key() + "", "3" } })
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
        assertThat(ExasolOptions.builder().query("query").withOptionsMap(m).build().getNumberOfPartitions(),
                equalTo(3));
    }

    @Test
    void testValidatesOnlyTableOrQueryExists() {
        final ExasolOptions.Builder builder = ExasolOptions.builder().table("table").query("query");
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.build());
        assertThat(exception.getMessage(), startsWith("E-SEC-19"));
    }

    @Test
    void testContainsKeyWithEmptyMap() {
        final ExasolOptions options = ExasolOptions.builder().build();
        assertThat(options.containsKey("aKey"), equalTo(false));
    }

    @Test
    void testGetEmptyMap() {
        final ExasolOptions options = ExasolOptions.builder().build();
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> options.get("key"));
        assertThat(exception.getMessage(), startsWith("E-SEC-21"));
    }

    @Test
    void testGetWithOptionsMap() {
        final Map<String, String> m = Stream.of(new String[][] { { "key", "value" } })
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
        assertThat(ExasolOptions.builder().withOptionsMap(m).build().get("key"), equalTo("value"));
    }

    @Test
    void testDuplicateKeyValuesThrows() {
        final ExasolOptions.Builder builder = ExasolOptions.builder();
        final Map<String, String> pairs = Stream.of(new String[][] { { "k1", "v1" }, { "K1", "v2" } })
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> builder.withOptionsMap(pairs));
        assertThat(exception.getMessage(), startsWith("E-SEC-20: Found case sensitive duplicate key"));
    }

    @Test
    void testObeysCaseInsensitivity() {
        final Map<String, String> m = Stream.of(new String[][] { { "key", "value" } })
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
        final ExasolOptions options = ExasolOptions.builder().withOptionsMap(m).build();
        assertAll(() -> assertThat(options.containsKey("KEY"), equalTo(true)),
                () -> assertThat(options.get("keY"), equalTo("value")));
    }

    @Test
    void testHasEnabled() {
        final Map<String, String> m = Stream
                .of(new String[][] { { "enabled", "true" }, { "notenabled", "false" }, { "key", "10" } })
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
        final ExasolOptions options = ExasolOptions.builder().withOptionsMap(m).build();
        assertAll(() -> assertThat(options.hasEnabled("enabled"), equalTo(true)),
                () -> assertThat(options.hasEnabled("notenabled"), equalTo(false)),
                () -> assertThat(options.hasEnabled("key"), equalTo(false)),
                () -> assertThat(options.hasEnabled("non-existing-key"), equalTo(false)));
    }

    @Test
    void testToStringWithTable() {
        final ExasolOptions options = ExasolOptions.builder().table("table").build();
        final String expected = "ExasolOptions{"
                + "jdbcUrl=\"jdbc:exa:localhost:8563\", username=\"sys\", password=\"*******\", table=\"table\"}";
        assertThat(options.toString(), equalTo(expected));
    }

    @Test
    void testToStringWithQuery() {
        final ExasolOptions options = ExasolOptions.builder().query("query").build();
        final String expected = "ExasolOptions{jdbcUrl=\"jdbc:exa:localhost:8563\", username=\"sys\", "
                + "password=\"*******\", query=\"query\"}";
        assertThat(options.toString(), equalTo(expected));
    }

    @Test
    void testToStringWithOptionsMap() {
        final Map<String, String> m = Stream.of(new String[][] { { "k1", "v1" }, { "k2", "10" } })
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
        final ExasolOptions options = ExasolOptions.builder().table("table").withOptionsMap(m).build();
        final String expected = "ExasolOptions{"
                + "jdbcUrl=\"jdbc:exa:localhost:8563\", username=\"sys\", password=\"*******\", "
                + "table=\"table\", map=\"{k1=v1, k2=10}\"}";
        assertThat(options.toString(), equalTo(expected));
    }

    @Test
    void testEqualsAndHashMethods() {
        EqualsVerifier.forClass(ExasolOptions.class).verify();
    }

}

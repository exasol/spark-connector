package com.exasol.spark.s3;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import org.apache.spark.sql.connector.catalog.Table;
import org.apache.spark.sql.connector.catalog.TableProvider;
import org.apache.spark.sql.connector.expressions.Transform;
import org.apache.spark.sql.sources.DataSourceRegister;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

import com.exasol.errorreporting.ExaError;
import com.exasol.spark.common.*;
import com.exasol.sql.StatementFactory;
import com.exasol.sql.dql.select.Select;
import com.exasol.sql.dql.select.rendering.SelectRenderer;
import com.exasol.sql.rendering.StringRendererConfig;

/**
 * An S3 Spark Connector Source.
 */
public class S3Source implements TableProvider, DataSourceRegister {
    private static final Logger LOGGER = Logger.getLogger(S3Source.class.getName());
    private static final List<String> REQUIRED_OPTIONS = Arrays.asList(Option.HOST.key(), Option.PORT.key(),
            Option.USERNAME.key(), Option.PASSWORD.key());

    @Override
    public String shortName() {
        return "exasol-s3";
    }

    @Override
    public boolean supportsExternalMetadata() {
        return true;
    }

    @Override
    public StructType inferSchema(final CaseInsensitiveStringMap map) {
        LOGGER.fine(() -> "Running schema inference for the S3 source.");
        validateOptions(map);
        return getSchema(ExasolOptions.from(map));
    }

    @Override
    public Table getTable(final StructType schema, final Transform[] partitioning,
            final Map<String, String> properties) {
        return new ExasolS3Table(schema);
    }

    private void validateOptions(final CaseInsensitiveStringMap options) {
        LOGGER.finest(() -> "Validating options of the s3 source.");
        if (!options.containsKey(Option.TABLE.key()) && !options.containsKey(Option.QUERY.key())) {
            throw new IllegalArgumentException(
                    ExaError.messageBuilder("E-SEC-12").message("Missing 'query' or 'table' option.")
                            .mitigation("Please provide either one of 'query' or 'table' options.").toString());
        }
        if (options.containsKey(Option.TABLE.key()) && options.containsKey(Option.QUERY.key())) {
            throw new IllegalArgumentException(
                    ExaError.messageBuilder("E-SEC-13").message("Both 'query' and 'table' options are provided.")
                            .mitigation("Please use only either one of the options.").toString());
        }
        validateRequiredOptions(options);
    }

    private void validateRequiredOptions(CaseInsensitiveStringMap options) {
        for (final String key : REQUIRED_OPTIONS) {
            if (!options.containsKey(key)) {
                throw new IllegalArgumentException(ExaError.messageBuilder("E-SEC-14")
                        .message("Required option {{KEY}} is not found.")
                        .mitigation("Please provide a value for the {{KEY}} option.").parameter("KEY", key).toString());
            }
        }
    }

    private StructType getSchema(final ExasolOptions options) {
        final String limitQuery = generateInferSchemaQuery(options);
        LOGGER.info(() -> "Running schema inference using limited query '" + limitQuery + "' for the s3 source.");
        try (final Connection connection = new ExasolConnectionFactory(options).getConnection();
                final Statement statement = connection.createStatement()) {
            final StructType schema = getSparkSchema(statement.executeQuery(limitQuery));
            LOGGER.info(() -> "Inferred schema as '" + schema.toString() + "' for the s3 source.");
            return schema;
        } catch (final SQLException exception) {
            throw new ExasolConnectionException(ExaError.messageBuilder("E-SEC-15")
                    .message("Could not run the limit query {{limitQuery}} to infer the schema.", limitQuery)
                    .mitigation("Please check that connection properties and original query are correct.").toString(),
                    exception);
        }
    }

    private String generateInferSchemaQuery(final ExasolOptions options) {
        final Select select = StatementFactory.getInstance().select();
        select.all().from().table("<SCHEMA_INFERENCE_TABLE>");
        if (options.hasQuery()) {
            select.limit(1);
        }
        final StringRendererConfig rendererConfig = StringRendererConfig.builder().quoteIdentifiers(true).build();
        final SelectRenderer renderer = new SelectRenderer(rendererConfig);
        select.accept(renderer);
        return renderer.render().replace("\"<SCHEMA_INFERENCE_TABLE>\"", getTableOrQuery(options));
    }

    private String getTableOrQuery(final ExasolOptions options) {
        if (options.hasTable()) {
            return options.getTable();
        } else {
            return "(" + options.getQuery() + ")";
        }
    }

    private StructType getSparkSchema(final ResultSet resultSet) {
        try {
            final ResultSetMetaData metadata = resultSet.getMetaData();
            final int numberOfColumns = metadata.getColumnCount();
            final List<ColumnDescription> columns = new ArrayList<>(numberOfColumns);
            for (int i = 1; i <= numberOfColumns; i++) {
                columns.add(ColumnDescription.builder() //
                        .name(metadata.getColumnLabel(i)) //
                        .type(metadata.getColumnType(i)) //
                        .precision(metadata.getPrecision(i)) //
                        .scale(metadata.getScale(i)) //
                        .isSigned(metadata.isSigned(i)) //
                        .isNullable(metadata.isNullable(i) != ResultSetMetaData.columnNoNulls) //
                        .build());

            }
            return new SchemaConverter().convert(columns);
        } catch (final SQLException exception) {
            throw new ExasolConnectionException(
                    ExaError.messageBuilder("E-SEC-16")
                            .message("Could not create Spark schema from provided Exasol SQL query or table name.")
                            .mitigation("Please make sure that Exasol SQL query or table have columns.").toString(),
                    exception);
        }
    }

}

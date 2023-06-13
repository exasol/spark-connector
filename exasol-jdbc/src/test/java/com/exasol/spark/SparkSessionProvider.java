package com.exasol.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

/**
 * A class that provides Spark session for tests.
 */
public final class SparkSessionProvider {
    private static volatile SparkSession sparkSession;

    private SparkSessionProvider() {
        // Intentionally private
    }

    public static SparkSession getSparkSession(final SparkConf sparkConf) {
        final SparkSession result = sparkSession;
        if (result != null && !isSparkContextStopped()) {
            return result;
        }
        synchronized (SparkSessionProvider.class) {
            if (sparkSession == null || isSparkContextStopped()) {
                sparkSession = createSparkSession(sparkConf);
            }
            return sparkSession;
        }
    }

    private static boolean isSparkContextStopped() {
        return sparkSession.sparkContext().isStopped();
    }

    private static SparkSession createSparkSession(final SparkConf sparkConf) {
        SparkSession.Builder sparkSessionBuilder = SparkSession.builder();
        if (sparkConf != null) {
            sparkSessionBuilder = sparkSessionBuilder.config(sparkConf);
        }
        return sparkSessionBuilder.getOrCreate();
    }

}

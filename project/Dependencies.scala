package com.exasol.spark.sbt

import sbt._

/** A list of required dependencies */
object Dependencies {

  // Versions
  private val DefaultSparkVersion = "3.1.2"
  private val ExasolJdbcVersion = "7.1.0"
  private val ExasolSQLStmtBuilderVersion = "4.4.1"

  private val ScalaTestVersion = "3.2.9"
  private val ScalaTestMockitoVersion = "1.0.0-M2"
  private val MockitoVersion = "3.12.4"
  private val ExasolTestContainersVersion = "4.0.1"
  private val ExasolTestDBBuilderVersion = "3.2.1"
  private val ExasolHamcrestMatcherVersion = "1.4.1"

  private val sparkCurrentVersion =
    sys.env.getOrElse("SPARK_VERSION", DefaultSparkVersion)

  private val SparkTestingBaseVersion = s"${sparkCurrentVersion}_1.1.0"

  val Resolvers: Seq[Resolver] = Seq(
    "Exasol Releases" at "https://maven.exasol.com/artifactory/exasol-releases"
  )

  /** Core dependencies needed for connector */
  private val CoreDependencies: Seq[ModuleID] = Seq(
    "com.exasol" % "exasol-jdbc" % ExasolJdbcVersion,
    "com.exasol" % "sql-statement-builder-java8" % ExasolSQLStmtBuilderVersion,
    "com.exasol" % "error-reporting-java" % "0.4.0",
    "org.apache.spark" %% "spark-core" % sparkCurrentVersion % "provided",
    "org.apache.spark" %% "spark-sql" % sparkCurrentVersion % "provided"
  )

  /** Test dependencies only required in `test` */
  private val TestDependencies: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % ScalaTestVersion,
    "org.scalatestplus" %% "scalatestplus-mockito" % ScalaTestMockitoVersion,
    "org.mockito" % "mockito-core" % MockitoVersion,
    "com.holdenkarau" %% "spark-testing-base" % SparkTestingBaseVersion,
    "com.exasol" % "exasol-testcontainers" % ExasolTestContainersVersion,
    "com.exasol" % "test-db-builder-java" % ExasolTestDBBuilderVersion,
    "com.exasol" % "hamcrest-resultset-matcher" % ExasolHamcrestMatcherVersion
  ).map(_ % Test)

  /** The list of all dependencies for the connector */
  lazy val AllDependencies: Seq[ModuleID] = CoreDependencies ++ TestDependencies

}

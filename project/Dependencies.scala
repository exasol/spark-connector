package com.exasol.spark.sbt

import sbt._

/** A list of required dependencies */
object Dependencies {

  // Versions
  private val DefaultSparkVersion = "2.4.5"
  private val ExasolJdbcVersion = "7.0.0"

  private val ScalaTestVersion = "3.2.2"
  private val ScalaTestMockitoVersion = "1.0.0-M2"
  private val MockitoVersion = "3.5.13"
  private val ContainersJdbcVersion = "1.14.3"
  private val ContainersScalaVersion = "0.38.4"

  private val sparkCurrentVersion =
    sys.env.getOrElse("SPARK_VERSION", DefaultSparkVersion)

  private val SparkTestingBaseVersion = s"${sparkCurrentVersion}_0.14.0"

  val Resolvers: Seq[Resolver] = Seq(
    "Exasol Releases" at "https://maven.exasol.com/artifactory/exasol-releases"
  )

  /** Core dependencies needed for connector */
  private val CoreDependencies: Seq[ModuleID] = Seq(
    "com.exasol" % "exasol-jdbc" % ExasolJdbcVersion,
    "org.apache.spark" %% "spark-core" % sparkCurrentVersion % "provided",
    "org.apache.spark" %% "spark-sql" % sparkCurrentVersion % "provided"
  )

  /** Test dependencies only required in `test` */
  private val TestDependencies: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % ScalaTestVersion,
    "org.scalatestplus" %% "scalatestplus-mockito" % ScalaTestMockitoVersion,
    "org.mockito" % "mockito-core" % MockitoVersion,
    "org.testcontainers" % "jdbc" % ContainersJdbcVersion,
    "com.dimafeng" %% "testcontainers-scala" % ContainersScalaVersion,
    "com.holdenkarau" %% "spark-testing-base" % SparkTestingBaseVersion
  ).map(_ % Test)

  /** The list of all dependencies for the connector */
  lazy val AllDependencies: Seq[ModuleID] = CoreDependencies ++ TestDependencies

}

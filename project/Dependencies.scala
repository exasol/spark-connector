package com.exasol.spark.sbt

import sbt._

/** A list of required dependencies */
object Dependencies {

  // Versions
  private val SparkVersion = "2.4.5"
  private val ExasolJdbcVersion = "6.2.5"

  private val ScalaTestVersion = "3.2.0"
  private val ScalaTestMockitoVersion = "1.0.0-M2"
  private val MockitoVersion = "3.4.6"
  private val ContainersJdbcVersion = "1.14.3"
  private val ContainersScalaVersion = "0.38.1"

  private val sparkCurrentVersion =
    sys.props.get("spark.currentVersion").getOrElse(SparkVersion)

  private val SparkTestingBaseVersion = s"${sparkCurrentVersion}_0.14.0"

  val Resolvers: Seq[Resolver] = Seq(
    "Exasol Releases" at "https://maven.exasol.com/artifactory/exasol-releases"
  )

  /** Core dependencies needed for connector */
  private val CoreDependencies: Seq[ModuleID] = Seq(
    "org.apache.spark" %% "spark-core" % sparkCurrentVersion % "provided",
    "org.apache.spark" %% "spark-sql" % sparkCurrentVersion % "provided",
    "com.exasol" % "exasol-jdbc" % ExasolJdbcVersion
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

package com.exasol.spark.sbt

import sbt._

/** A list of required dependencies */
object Dependencies {

  // Versions
  val ScalaVersion = "2.11.11"
  val CrossScalaVersions = Seq("2.10.7", ScalaVersion)

  private val SparkVersion = "2.3.1"
  private val ExasolJdbcVersion = "6.0.8"
  private val TypesafeLoggingVersion = "3.7.2"

  private val ScalaTestVersion = "3.0.5"
  private val MockitoVersion = "2.21.0"
  private val ContainersJdbcVersion = "1.8.3"
  private val ContainersScalaVersion = "0.19.0"
  private val SparkTestingBaseVersion = s"${SparkVersion}_0.10.0"

  val Resolvers: Seq[Resolver] = Seq(
    "Exasol Releases" at "https://maven.exasol.com/artifactory/exasol-releases"
  )

  /** Core dependencies needed for connector */
  private val CoreDependencies: Seq[ModuleID] = Seq(
    "org.apache.spark" %% "spark-core" % SparkVersion % "provided",
    "org.apache.spark" %% "spark-sql" % SparkVersion % "provided",
    "com.typesafe.scala-logging" %% "scala-logging" % TypesafeLoggingVersion,
    "com.exasol" % "exasol-jdbc" % ExasolJdbcVersion
  )

  /** Test dependencies only required in `test` */
  private val TestDependencies: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % ScalaTestVersion,
    "org.mockito" % "mockito-core" % MockitoVersion,
    "org.testcontainers" % "jdbc" % ContainersJdbcVersion,
    "com.dimafeng" %% "testcontainers-scala" % ContainersScalaVersion,
    "org.apache.spark" %% "spark-hive" % SparkVersion,
    "com.holdenkarau" %% "spark-testing-base" % SparkTestingBaseVersion
  ).map(_ % Test)

  /** The list of all dependencies for the connector */
  lazy val AllDependencies: Seq[ModuleID] = CoreDependencies ++ TestDependencies

}

import com.exasol.spark.sbt.Compilation
import com.exasol.spark.sbt.Dependencies
import com.exasol.spark.sbt.Settings
import com.exasol.spark.sbt.IntegrationTestPlugin

lazy val orgSettings = Seq(
  name := "spark-exasol-connector",
  description := "The Spark Exasol Connector",
  organization := "com.exasol",
  organizationHomepage := Some(url("http://www.exasol.com"))
)

lazy val buildSettings = Seq(
  scalaVersion := "2.11.11",
  crossScalaVersions := Seq("2.10.7", "2.11.11"),
  // Compiler settings
  scalacOptions ++= Compilation.compilerFlagsFn(scalaVersion.value),
  scalacOptions in (Compile, console) := Compilation.consoleFlagsFn(scalaVersion.value),
  javacOptions ++= Compilation.JavacCompilerFlags,
  compileOrder in Compile := CompileOrder.JavaThenScala,
  // Dependency settings
  resolvers ++= Dependencies.Resolvers,
  libraryDependencies ++= Dependencies.AllDependencies
)

lazy val root =
  project
    .in(file("."))
    .settings(orgSettings)
    .settings(buildSettings)
    .settings(Settings.projectSettings)
    .enablePlugins(IntegrationTestPlugin)

addCommandAlias("pluginUpdates", ";reload plugins;dependencyUpdates;reload return")

import com.exasol.spark.sbt.Settings
import com.exasol.spark.sbt.IntegrationTestPlugin

lazy val orgSettings = Seq(
  name := "spark-exasol-connector",
  description := "The Spark Exasol Connector",
  organization := "com.exasol",
  organizationHomepage := Some(url("http://www.exasol.com"))
)

lazy val buildSettings = Seq(
  scalaVersion := "2.11.12",
  crossScalaVersions := Seq("2.10.5", "2.11.12")
)

lazy val root =
  project
    .in(file("."))
    .settings(moduleName := "spark-connector")
    .settings(orgSettings)
    .settings(buildSettings)
    .settings(Settings.projectSettings(scalaVersion))
    .enablePlugins(IntegrationTestPlugin)

addCommandAlias("ci-release", ";reload;clean;release with-defaults")
addCommandAlias("pluginUpdates", ";reload plugins;dependencyUpdates;reload return")

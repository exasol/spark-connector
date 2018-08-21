import com.exasol.spark.sbt.Settings
import com.exasol.spark.sbt.IntegrationTestPlugin

lazy val orgSettings = Seq(
  name := "spark-exasol-connector",
  description := "The Spark Exasol Connector",
  organization := "com.exasol",
  organizationHomepage := Some(url("http://www.exasol.com"))
)

lazy val root =
  project
    .in(file("."))
    .settings(orgSettings)
    .settings(Settings.projectSettings)
    .enablePlugins(IntegrationTestPlugin)

addCommandAlias("pluginUpdates", ";reload plugins;dependencyUpdates;reload return")

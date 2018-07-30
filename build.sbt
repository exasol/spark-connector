
lazy val companySettings = Seq(
  name := "spark-exasol-connector",
  description := "A Spark Exasol Connector",
  organization := "com.exasol",
  organizationHomepage := Some(url("http://www.exasol.com"))
)

lazy val buildSettings = Seq(
  scalaVersion := "2.11.11",
  crossScalaVersions := Seq("2.10.7", "2.11.11"),
  cancelable in Global := true,
  parallelExecution in Test := false,
  compileOrder in Compile := CompileOrder.JavaThenScala,
)

lazy val sparkExasolSettings =
  companySettings ++ buildSettings

lazy val versions = new {
  // core dependency versions
  val spark       = "2.3.1"

  // testing dependency versions
  val scalatest   = "3.0.5"
  val scalacheck  = "1.14.0"
}

lazy val dependencySettings = Seq(
  "org.apache.spark" %% "spark-core"                  % versions.spark % "provided",
  "org.apache.spark" %% "spark-sql"                   % versions.spark % "provided"
) ++ Seq(
  "org.scalatest"    %% "scalatest"                   % versions.scalatest,
  "org.scalacheck"   %% "scalacheck"                  % versions.scalacheck
).map(_ % Test)

lazy val root =
  project.in(file("."))
  .settings(sparkExasolSettings)
  .settings(libraryDependencies ++= dependencySettings)

addCommandAlias("pluginUpdates", ";reload plugins;dependencyUpdates;reload return")

import com.exasol.spark.sbt.IntegrationTestPlugin

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
  resolvers += "Exasol Releases" at "https://maven.exasol.com/artifactory/exasol-releases",
  scalafmtOnCompile := true,
  coverageMinimum := 50,
  coverageOutputHTML := true,
  coverageOutputXML := false,
  coverageFailOnMinimum := false,
  coverageOutputCobertura := false
)

lazy val assemblySettings = Seq(
  test in assembly := {},
  logLevel in assembly := Level.Info,
  // The Scala library is provided by Spark execution environment
  assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false),
  assemblyMergeStrategy in assembly := {
    // Add data source register to assembly jar
    case "META-INF/services/org.apache.spark.sql.sources.DataSourceRegister" =>
      MergeStrategy.concat
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x                             => MergeStrategy.first
  },
  assemblyExcludedJars in assembly := {
    val cp = (fullClasspath in assembly).value
    val exludeSet = Set.empty[String]
    cp.filter { jar =>
      exludeSet(jar.data.getName)
    }
  }
)

lazy val scalaStyleSettings = {
  // Creates a Scalastyle task that runs with tests
  lazy val mainScalastyle = taskKey[Unit]("mainScalastyle")
  lazy val testScalastyle = taskKey[Unit]("testScalastyle")

  Seq(
    scalastyleFailOnError := true,
    (scalastyleConfig in Compile) := baseDirectory.value / "project" / "scalastyle-config.xml",
    (scalastyleConfig in Test) := baseDirectory.value / "project" / "scalastyle-config.xml",
    mainScalastyle := scalastyle.in(Compile).toTask("").value,
    testScalastyle := scalastyle.in(Test).toTask("").value,
    (test in Test) := ((test in Test) dependsOn testScalastyle).value,
    (test in Test) := ((test in Test) dependsOn mainScalastyle).value,
    (test in IntegrationTest) := ((test in IntegrationTest) dependsOn testScalastyle).value,
    (test in IntegrationTest) := ((test in IntegrationTest) dependsOn mainScalastyle).value
  )
}

lazy val sparkExasolSettings =
  companySettings ++ buildSettings ++ assemblySettings ++ scalaStyleSettings

lazy val versions = new {
  // core dependency versions
  val spark = "2.3.1"
  val exasol_jdbc = "6.0.8"

  // testing dependency versions
  val scalatest = "3.0.5"
  val scalacheck = "1.14.0"
  val mockito = "2.21.0"
  val containers_jdbc = "1.8.3"
  val containers_scala = "0.19.0"
  val spark_testing_base = s"${spark}_0.10.0"
}

lazy val dependencySettings = Seq(
  "org.apache.spark" %% "spark-core" % versions.spark % "provided",
  "org.apache.spark" %% "spark-sql" % versions.spark % "provided",
  "com.exasol" % "exasol-jdbc" % versions.exasol_jdbc
) ++ Seq(
  "org.scalatest" %% "scalatest" % versions.scalatest,
  "org.scalacheck" %% "scalacheck" % versions.scalacheck,
  "org.mockito" % "mockito-core" % versions.mockito,
  "com.exasol" % "exasol-jdbc" % versions.exasol_jdbc,
  "org.testcontainers" % "jdbc" % versions.containers_jdbc,
  "com.dimafeng" %% "testcontainers-scala" % versions.containers_scala,
  "org.apache.spark" %% "spark-hive" % versions.spark,
  "com.holdenkarau" %% "spark-testing-base" % versions.spark_testing_base
).map(_ % Test)

lazy val root =
  project
    .in(file("."))
    .settings(sparkExasolSettings)
    .settings(libraryDependencies ++= dependencySettings)
    .enablePlugins(IntegrationTestPlugin)

addCommandAlias("pluginUpdates", ";reload plugins;dependencyUpdates;reload return")

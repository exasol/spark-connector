package com.exasol.spark.sbt

import sbt._
import sbt.Keys._

import sbtassembly.MergeStrategy
import sbtassembly.PathList
import sbtassembly.AssemblyPlugin.autoImport._

import scoverage.ScoverageSbtPlugin.autoImport._
import org.scalastyle.sbt.ScalastylePlugin.autoImport._
import wartremover.WartRemover.autoImport.wartremoverErrors
import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._

/** A list of (boilerplate) settings for build process */
object Settings {

  def projectSettings(scalaVersion: SettingKey[String]): Seq[Setting[_]] =
    buildSettings(scalaVersion) ++
      miscSettings ++
      assemblySettings ++
      apiDocSettings ++
      scalaStyleSettings ++
      Publishing.publishSettings()

  def buildSettings(scalaVersion: SettingKey[String]): Seq[Setting[_]] = Seq(
    // Compiler settings
    scalacOptions ++= Compilation.compilerFlagsFn(scalaVersion.value),
    scalacOptions in (Compile, console) := Compilation.consoleFlagsFn(scalaVersion.value),
    javacOptions ++= Compilation.JavacCompilerFlags,
    compileOrder in Compile := CompileOrder.JavaThenScala,
    // Dependency settings
    resolvers ++= Dependencies.Resolvers,
    libraryDependencies ++= Dependencies.AllDependencies
  )

  def miscSettings(): Seq[Setting[_]] = Seq(
    // Wartremover settings
    wartremoverErrors in (Compile, compile) := Compilation.WartremoverFlags,
    wartremoverErrors in (Test, compile) := Compilation.WartremoverTestFlags,
    // General settings
    cancelable in Global := true,
    // ScalaFmt settings
    scalafmtOnCompile := true,
    // Scoverage settings
    coverageOutputHTML := true,
    coverageOutputXML := true,
    coverageFailOnMinimum := false,
    coverageOutputCobertura := true
  )

  def assemblySettings(): Seq[Setting[_]] = Seq(
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

  def apiDocSettings(): Seq[Setting[_]] = Seq(
    autoAPIMappings := true,
    apiMappings ++= scalaInstance.value.libraryJars.collect {
      case file if file.getName.startsWith("scala-library") && file.getName.endsWith(".jar") =>
        file -> url(s"http://www.scala-lang.org/api/${scalaVersion.value}/")
    }.toMap ++
      // Since Java 9+ introduced modules, API links changed, update these
      // links based on used Java modules.
      Map(
        file("/modules/java.sql") -> url(
          "https://docs.oracle.com/en/java/javase/11/docs/api/java.sql"
        )
      ),
    // Override doc task in 2.11.x versions since linking external Java
    // 11+ classes does not work.
    (Compile / doc) := Def.taskDyn {
      val docTaskValue = (Compile / doc).taskValue
      if (scalaBinaryVersion.value == "2.11") {
        (Compile / doc / target).toTask
      } else {
        Def.task(docTaskValue.value)
      }
    }.value
  )

  def scalaStyleSettings(): Seq[Setting[_]] = {
    // Creates a Scalastyle task that runs with tests
    lazy val mainScalastyle = taskKey[Unit]("mainScalastyle")
    lazy val testScalastyle = taskKey[Unit]("testScalastyle")
    lazy val itTestScalastyle = taskKey[Unit]("itTestScalastyle")

    Seq(
      scalastyleFailOnError := true,
      (scalastyleConfig in Compile) := baseDirectory.value / "project" / "scalastyle-config.xml",
      (scalastyleConfig in Test) := baseDirectory.value / "project" / "scalastyle-test-config.xml",
      (scalastyleConfig in IntegrationTest) := (scalastyleConfig in Test).value,
      (scalastyleSources in IntegrationTest) := Seq((scalaSource in IntegrationTest).value),
      mainScalastyle := scalastyle.in(Compile).toTask("").value,
      testScalastyle := scalastyle.in(Test).toTask("").value,
      itTestScalastyle := scalastyle.in(IntegrationTest).toTask("").value,
      (test in Test) := ((test in Test) dependsOn mainScalastyle).value,
      (test in Test) := ((test in Test) dependsOn testScalastyle).value,
      (test in IntegrationTest) := ((test in IntegrationTest) dependsOn mainScalastyle).value,
      (test in IntegrationTest) := ((test in IntegrationTest) dependsOn itTestScalastyle).value
    )
  }

}

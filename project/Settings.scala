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

  def miscSettings(): Seq[Setting[_]] = Seq(
    // Wartremover settings
    wartremoverErrors in (Compile, compile) := Compilation.WartremoverFlags,
    wartremoverErrors in (Test, compile) := Compilation.WartremoverTestFlags,
    // General settings
    cancelable in Global := true,

    // ScalaFmt settings
    scalafmtOnCompile := true,

    // Scoverage settings
    coverageMinimum := 50,
    coverageOutputHTML := true,
    coverageOutputXML := true,
    coverageFailOnMinimum := false,
    coverageOutputCobertura := false
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

  def scalaStyleSettings(): Seq[Setting[_]] = {
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

  lazy val projectSettings: Seq[Setting[_]] =
    miscSettings ++ assemblySettings ++ scalaStyleSettings

}

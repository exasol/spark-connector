package com.exasol.spark.sbt

import sbt._
import sbt.Keys._

import sbtassembly.MergeStrategy
import sbtassembly.PathList
import sbtassembly.AssemblyPlugin.autoImport._

import scoverage.ScoverageSbtPlugin.autoImport._
import org.scalastyle.sbt.ScalastylePlugin.autoImport._
import wartremover.WartRemover.autoImport.wartremoverErrors

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
    Compile / console / scalacOptions := Compilation.consoleFlagsFn(scalaVersion.value),
    javacOptions ++= Compilation.JavacCompilerFlags,
    Compile / compileOrder := CompileOrder.JavaThenScala,
    // Dependency settings
    resolvers ++= Dependencies.Resolvers,
    libraryDependencies ++= Dependencies.AllDependencies
  )

  def miscSettings(): Seq[Setting[_]] = Seq(
    // Wartremover settings
    Compile / compile / wartremoverErrors := Compilation.WartremoverFlags,
    Test / compile / wartremoverErrors := Compilation.WartremoverTestFlags,
    // General settings
    Global / cancelable := true,
    // Scoverage settings
    coverageOutputHTML := true,
    coverageOutputXML := true,
    coverageOutputCobertura := true,
    coverageFailOnMinimum := false
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

  /** Creates a Scalastyle tasks that run for source, unit and integration tests. */
  def scalaStyleSettings(): Seq[Setting[_]] = {
    lazy val mainScalastyle = taskKey[Unit]("mainScalastyle")
    lazy val testScalastyle = taskKey[Unit]("testScalastyle")
    Seq(
      scalastyleFailOnError := true,
      Compile / scalastyleConfig := (ThisBuild / baseDirectory).value / "project" / "scalastyle-config.xml",
      Test / scalastyleConfig := (ThisBuild / baseDirectory).value / "project" / "scalastyle-test-config.xml",
      mainScalastyle := (Compile / scalastyle).toTask("").value,
      testScalastyle := (Compile / scalastyle).toTask("").value,
      Test / test := (Test / test).dependsOn(mainScalastyle).value,
      Test / test := (Test / test).dependsOn(testScalastyle).value
    )
  }

  def assemblySettings(): Seq[Setting[_]] = Seq(
    assembly / test := {},
    assembly / logLevel := Level.Info,
    // The Scala library is provided by Spark execution environment
    assembly / assemblyOption := (assembly / assemblyOption).value.copy(includeScala = false),
    assembly / assemblyJarName := moduleName.value + "-" + version.value + ".jar",
    assembly / assemblyMergeStrategy := {
      case "META-INF/services/org.apache.spark.sql.sources.DataSourceRegister" =>
        MergeStrategy.concat
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case x                             => MergeStrategy.first
    },
    assembly / assemblyExcludedJars := {
      val cp = (assembly / fullClasspath).value
      val exludeSet = Set.empty[String]
      cp.filter { jar =>
        exludeSet(jar.data.getName)
      }
    }
  )

}

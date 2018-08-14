package com.exasol.spark.sbt

import sbt._
import sbt.Keys._
import sbt.Def.Setting
import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin

/** Settings for running integration tests */
object IntegrationTestSettings {

  /**
   * Extend Test settings in integration tests, so for instance dependencies in `% test` are also
   * available for integration tests
   */
  lazy val IntegrationTestConfig: Configuration = config("it").extend(Test)

  /** Integration test related configurations */
  lazy val configurations: Seq[Configuration] = Seq(IntegrationTestConfig)

  /** Integration test settings to add to the projects */
  lazy val settings: Seq[Setting[_]] = {
    val itSettings =
      ScalafmtCorePlugin.autoImport.scalafmtSettings ++ Seq(
        fork := true,
        parallelExecution := false,
        scalaSource := baseDirectory.value / "src/it/scala"
      )

    Seq.concat(
      Defaults.itSettings,
      inConfig(IntegrationTest)(itSettings),
      // `sbt test` will not trigger integration tests. Integration tests should be specifically
      // specified with `sbt it:test` or `sbt it:testOnly testFile`
      Seq(test := {})
    )
  }

}

package com.exasol.spark.sbt

import sbt._
import sbt.Keys._
import sbt.Def.Setting
import org.scalastyle.sbt.ScalastylePlugin
import org.scalafmt.sbt.ScalafmtPlugin

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
      ScalastylePlugin.rawScalastyleSettings ++
        ScalafmtPlugin.scalafmtConfigSettings ++
        Seq(
          fork := true,
          parallelExecution := false,
          scalaSource := baseDirectory.value / "src/it/scala"
        )

    Seq.concat(Defaults.itSettings, inConfig(IntegrationTest)(itSettings))
  }

}

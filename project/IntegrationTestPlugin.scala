package com.exasol.spark.sbt

import sbt._
import com.lucidchart.sbt.scalafmt.ScalafmtPlugin

/** A plugin for creating an integration test task and settings */
object IntegrationTestPlugin extends AutoPlugin {

  /**
   * Ensure the scalafmt plugin loads before this, so we can enable it for the integration tests
   */
  override def requires: Plugins = ScalafmtPlugin

  /** Add integration test settings to the projects */
  override val projectSettings: Seq[Setting[_]] =
    IntegrationTestSettings.settings

  /** Add the IntegrationTest configuration to the projects */
  override val projectConfigurations: Seq[Configuration] =
    IntegrationTestSettings.configurations
}

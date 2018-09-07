package com.exasol.spark.sbt

import sbt._
import org.scalastyle.sbt.ScalastylePlugin
import com.lucidchart.sbt.scalafmt.ScalafmtPlugin

/** A plugin for creating an integration test task and settings */
object IntegrationTestPlugin extends AutoPlugin {

  /**
   * Ensure the scalastyle and scalafmt plugins are loaded before integration test plugin, so
   * that, we can enable them for the integration test sources
   */
  override def requires: Plugins = ScalastylePlugin && ScalafmtPlugin

  /** Add integration test settings to the projects */
  override val projectSettings: Seq[Setting[_]] =
    IntegrationTestSettings.settings

  /** Add the IntegrationTest configuration to the projects */
  override val projectConfigurations: Seq[Configuration] =
    IntegrationTestSettings.configurations
}

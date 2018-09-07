package com.exasol.spark

import org.scalatest.Suite

import com.dimafeng.testcontainers.Container
import com.dimafeng.testcontainers.ExasolDockerContainer
import com.dimafeng.testcontainers.ForAllTestContainer

import com.exasol.spark.util.ExasolConfiguration
import com.exasol.spark.util.ExasolConnectionManager

/** A Base Integration Suite with Exasol DB Docker Container Setup */
trait BaseDockerSuite extends ForAllTestContainer { self: Suite =>

  override val container = ExasolDockerContainer()

  lazy val exaConfiguration = ExasolConfiguration(container.configs)

  lazy val exaManager = ExasolConnectionManager(exaConfiguration)

  val EXA_SCHEMA = "TEST_SCHEMA"
  val EXA_TABLE = "TEST_TABLE"

  def runExaQuery(queries: Seq[String]): Unit =
    exaManager.withConnection[Unit] { conn =>
      queries.foreach(conn.createStatement.execute(_))
      ()
    }

  def runExaQuery(queryString: String): Unit =
    runExaQuery(Seq(queryString))

  def createDummyTable(): Unit = {
    runExaQuery(s"DROP SCHEMA IF EXISTS $EXA_SCHEMA CASCADE")
    runExaQuery(s"CREATE SCHEMA $EXA_SCHEMA")
    runExaQuery(s"""
                   |CREATE OR REPLACE TABLE $EXA_SCHEMA.$EXA_TABLE (
                   |   ID INTEGER IDENTITY NOT NULL,
                   |   NAME VARCHAR(100) UTF8,
                   |   CITY VARCHAR(2000) UTF8,
                   |   UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
                   |)""".stripMargin)
    runExaQuery(s"INSERT INTO $EXA_SCHEMA.$EXA_TABLE (name, city) VALUES ('Germany', 'Berlin')")
    runExaQuery(s"INSERT INTO $EXA_SCHEMA.$EXA_TABLE (name, city) VALUES ('France', 'Paris')")
    runExaQuery(s"INSERT INTO $EXA_SCHEMA.$EXA_TABLE (name, city) VALUES ('Portugal', 'Lisbon')")
    runExaQuery("commit")
  }

}

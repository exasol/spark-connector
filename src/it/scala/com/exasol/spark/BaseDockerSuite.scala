package com.exasol.spark

import com.exasol.spark.util.ExasolConfiguration
import com.exasol.spark.util.ExasolConnectionManager
import com.exasol.spark.util.Types._

import com.dimafeng.testcontainers.ExasolDockerContainer
import com.dimafeng.testcontainers.ForAllTestContainer
import org.scalatest.Suite

/** A Base Integration Suite with Exasol DB Docker Container Setup */
trait BaseDockerSuite extends ForAllTestContainer { self: Suite =>

  override val container = ExasolDockerContainer()

  lazy val exaConfiguration = ExasolConfiguration(container.configs)

  lazy val exaManager = ExasolConnectionManager(exaConfiguration)

  val EXA_SCHEMA = "TEST_SCHEMA"
  val EXA_TABLE = "TEST_TABLE"
  val EXA_ALL_TYPES_TABLE = "TEST_ALL_TYPES_TABLE"

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
                   |   DATE_INFO DATE,
                   |   UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
                   |)""".stripMargin)
    runExaQuery(s"""
                   |INSERT INTO $EXA_SCHEMA.$EXA_TABLE (name, city, date_info)
                   | VALUES ('Germany', 'Berlin', '2017-12-31')
                   | """.stripMargin)
    runExaQuery(s"""
                   |INSERT INTO $EXA_SCHEMA.$EXA_TABLE (name, city, date_info)
                   | VALUES ('France', 'Paris', '2018-01-01')
                   | """.stripMargin)
    runExaQuery(s"""
                   |INSERT INTO $EXA_SCHEMA.$EXA_TABLE (name, city, date_info)
                   | VALUES ('Portugal', 'Lisbon', '2018-10-01')
                   | """.stripMargin)
    runExaQuery("commit")
  }

  def createAllTypesTable(): Unit = {
    runExaQuery(s"DROP SCHEMA IF EXISTS $EXA_SCHEMA CASCADE")
    runExaQuery(s"CREATE SCHEMA $EXA_SCHEMA")
    val maxDecimal = " DECIMAL(" + getMaxPrecision() + "," + getMaxScale() + ")"
    runExaQuery(
      s"""
         |CREATE OR REPLACE TABLE $EXA_SCHEMA.$EXA_ALL_TYPES_TABLE (
         |   myID INTEGER,
         |   myTINYINT DECIMAL(3,0),
         |   mySMALLINT DECIMAL(9,0),
         |   myBIGINT DECIMAL(36,0),
         |   myDECIMALSystemDefault DECIMAL,
         |   myDECIMALmax $maxDecimal,
         |   myNUMERIC DECIMAL( 5,2 ),
         |   myDOUBLE DOUBLE PRECISION,
         |   myCHAR CHAR,
         |   myNCHAR CHAR(2000),
         |   myLONGVARCHAR VARCHAR( 2000000),
         |   myBOOLEAN BOOLEAN,
         |   myDATE DATE,
         |   myTIMESTAMP TIMESTAMP)""".stripMargin
    )
    //   |   myDECIMALmax $maxDecimal,
    // Types not covered : GEOMETRY, INTERVAL, TIMESTAMP WITH LOCAL TIME ZONE,
    runExaQuery("commit")
  }
}

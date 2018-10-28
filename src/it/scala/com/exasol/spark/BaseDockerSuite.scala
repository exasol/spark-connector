package com.exasol.spark

import com.exasol.spark.util.ExasolConfiguration
import com.exasol.spark.util.ExasolConnectionManager
import com.dimafeng.testcontainers.Container
import com.dimafeng.testcontainers.ExasolDockerContainer
import com.dimafeng.testcontainers.ForAllTestContainer
import org.apache.spark.sql.types.DecimalType
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
    runExaQuery(s"""
                   |CREATE OR REPLACE TABLE $EXA_SCHEMA.$EXA_ALL_TYPES_TABLE (
                   |   myID INTEGER,
                   |   myIDsigned INTEGER,
                   |   myTINYINT DECIMAL(3,0),
                   |   mySMALLINT DECIMAL(9,0),
                   |   myBIGINT DECIMAL(36,0),
                   |   myBIGINTisSigned DECIMAL(36,0),
                   |   myDECIMALSystemDefault DECIMAL(0,0),
                   |   myDECIMALmax DECIMAL(""" + DecimalType.MAX_PRECISION.toString + """,""" + DecimalType.MAX_SCALE.toString + """),
                   |   myNUMERICsysDefault DECIMAL( 0,0 ),
                   |   myNUMERIC DECIMAL( 5,2 ),
                   |   myDOUBLE DOUBLE PRECISION,
                   |   myFLOAT DOUBLE PRECISION,
                   |   myREAL DOUBLE PRECISION,
                   |   myCHAR CHAR,
                   |   myNCHAR CHAR(2000),
                   |   mvLONGVARCHAR VARCHAR( 2000000),
// in Exasol ?     |   BINARY,
// in Exasol ?     |   VARBINARY
// in Exasol ?     |   myLONGVARBINARY ,
// just a boolean  |   myBIT BOOLEAN,
                   |   myBOOLEAN BOOLEAN,
                   |   myDATE DATE,
                   |   myTIMESTAMP TIMESTAMP
                   |)""".stripMargin)
    val longString = new scala.util.Random(32).nextString(100000)
    runExaQuery(s"""
                   |INSERT INTO $EXA_SCHEMA.$EXA_ALL_TYPES_TABLE (
                   |   myID,
                   |   myIDsigned,
                   |   myTINYINT ,
                   |   mySMALLINT,
                   |   myBIGINT,
                   |   myBIGINTisSigned,
                   |   myDECIMALSystemDefault,
                   |   myDECIMALmax,
                   |   myNUMERIC,
                   |   myNUMERICsysDefault,
                   |   myDOUBLE,
                   |   myFLOAT,
                   |   myREAL,
                   |   myCHAR,
                   |   myNCHAR,
                   |   mvLONGVARCHAR,
                   |   myBOOLEAN,
                   |   myDATE,
                   |   myTIMESTAMP)
                   | VALUES (
                   |   1,
                   |   -1,
                   |   123 ,
                   |   123456789,
                   |   1000000000000,
                   |   -1000000000000,
                   |   1.1,
                   |   1000000000000.0000000000001,
                   |   1.1,
                   |   1000000000000.0000000000001,
                   |   -1.79E+308,
                   |   1.79E+308,
                   |   -3.40E+38,
                   |   'a',
                   |   'abcd',
                   |   """ + longString + """,
                   |   true,
                   |   '2018-01-01',
                   |   '2000-01-01 00:00:00')
                   | """.stripMargin)
    runExaQuery("commit")
  }
}

package com.exasol.spark

import com.exasol.containers.ExasolContainer
import com.exasol.spark.util.ExasolConfiguration
import com.exasol.spark.util.ExasolConnectionManager
import com.exasol.spark.util.Types._

import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

/**
 * A base integration suite with Exasol docker container setup.
 */
trait BaseDockerSuite extends AnyFunSuite with BeforeAndAfterAll {

  private[this] val DEFAULT_EXASOL_DOCKER_IMAGE = "7.0.6"

  val network = DockerNamedNetwork("spark-it-network", true)
  val container = {
    val c: ExasolContainer[_] = new ExasolContainerWithReuse(getExasolDockerImageVersion())
    c.withExposedPorts(8563)
    c.withNetwork(network)
    c
  }

  var jdbcHost: String = _
  var jdbcPort: String = _
  var connectionManager: ExasolConnectionManager = _

  def prepareExasolDatabase(): Unit = {
    container.start()
    jdbcHost = container.getDockerNetworkInternalIpAddress()
    jdbcPort = s"${container.getDefaultInternalDatabasePort()}"
    connectionManager = ExasolConnectionManager(ExasolConfiguration(getConfiguration()))
  }

  def getConfiguration(): Map[String, String] = Map(
    "host" -> jdbcHost,
    "port" -> jdbcPort,
    "username" -> container.getUsername(),
    "password" -> container.getPassword(),
    "max_nodes" -> "200"
  )

  def getConnection(): java.sql.Connection =
    container.createConnection("")

  override def beforeAll(): Unit =
    prepareExasolDatabase()

  override def afterAll(): Unit = {
    container.stop()
    network.close()
  }

  val EXA_SCHEMA = "TEST_SCHEMA"
  val EXA_TABLE = "TEST_TABLE"
  val EXA_ALL_TYPES_TABLE = "TEST_ALL_TYPES_TABLE"
  val EXA_TYPES_NOT_COVERED_TABLE = "TEST_TYPES_NOT_COVERED_TABLE"

  // scalastyle:off nonascii
  def createDummyTable(): Unit = {
    val queries = Seq(
      s"DROP SCHEMA IF EXISTS $EXA_SCHEMA CASCADE",
      s"CREATE SCHEMA $EXA_SCHEMA",
      s"""|CREATE OR REPLACE TABLE $EXA_SCHEMA.$EXA_TABLE (
          |   ID INTEGER IDENTITY NOT NULL,
          |   NAME VARCHAR(100) UTF8,
          |   CITY VARCHAR(2000) UTF8,
          |   DATE_INFO DATE,
          |   UNICODE_COL VARCHAR(100) UTF8,
          |   UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
          |)""".stripMargin,
      s"""|INSERT INTO $EXA_SCHEMA.$EXA_TABLE (name, city, date_info, unicode_col)
          | VALUES ('Germany', 'Berlin', '2017-12-31', 'öäüß')
          |""".stripMargin,
      s"""|INSERT INTO $EXA_SCHEMA.$EXA_TABLE (name, city, date_info, unicode_col)
          | VALUES ('France', 'Paris', '2018-01-01','\u00d6')
          |""".stripMargin,
      s"""|INSERT INTO $EXA_SCHEMA.$EXA_TABLE (name, city, date_info, unicode_col)
          | VALUES ('Portugal', 'Lisbon', '2018-10-01','\u00d9')
          |""".stripMargin,
      "commit"
    )
    connectionManager.withExecute(queries)
  }
  // scalastyle:on nonascii

  def createAllTypesTable(): Unit = {
    val maxDecimal = " DECIMAL(" + getMaxPrecisionExasol() + "," + getMaxScaleExasol() + ")"
    val queries = Seq(
      s"DROP SCHEMA IF EXISTS $EXA_SCHEMA CASCADE",
      s"CREATE SCHEMA $EXA_SCHEMA",
      s"""|CREATE OR REPLACE TABLE $EXA_SCHEMA.$EXA_ALL_TYPES_TABLE (
          |   MYID INTEGER,
          |   MYTINYINT DECIMAL(3,0),
          |   MYSMALLINT DECIMAL(9,0),
          |   MYBIGINT DECIMAL(36,0),
          |   MYDECIMALSystemDefault DECIMAL,
          |   MYDECIMALMAX $maxDecimal,
          |   MYNUMERIC DECIMAL( 5,2 ),
          |   MYDOUBLE DOUBLE PRECISION,
          |   MYCHAR CHAR,
          |   MYNCHAR CHAR(2000),
          |   MYLONGVARCHAR VARCHAR( 2000000),
          |   MYBOOLEAN BOOLEAN,
          |   MYDATE DATE,
          |   MYTIMESTAMP TIMESTAMP,
          |   MYGEOMETRY Geometry,
          |   MYINTERVAL INTERVAL YEAR TO MONTH
          |)""".stripMargin,
      "commit"
    )
    connectionManager.withExecute(queries)
  }

  private[this] def getExasolDockerImageVersion(): String =
    System.getProperty("EXASOL_DOCKER_VERSION", DEFAULT_EXASOL_DOCKER_IMAGE)

}

package com.exasol.spark

class BaseTableQueryIT extends AbstractTableQueryIT {

  val schema = "TEST_SCHEMA"
  override val tableName: String = s"$schema.TEST_TABLE"
  override def createTable(): Unit =
    exasolConnectionManager.withExecute(
      // scalastyle:off nonascii
      Seq(
        s"DROP SCHEMA IF EXISTS $schema CASCADE",
        s"CREATE SCHEMA $schema",
        s"""|CREATE OR REPLACE TABLE $tableName (
            |   ID INTEGER IDENTITY NOT NULL,
            |   NAME VARCHAR(100) UTF8,
            |   CITY VARCHAR(2000) UTF8,
            |   DATE_INFO DATE,
            |   UNICODE_COL VARCHAR(100) UTF8,
            |   UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
            |)""".stripMargin,
        s"""|INSERT INTO $tableName (name, city, date_info, unicode_col)
            | VALUES ('Germany', 'Berlin', '2017-12-31', 'öäüß')
            |""".stripMargin,
        s"""|INSERT INTO $tableName (name, city, date_info, unicode_col)
            | VALUES ('France', 'Paris', '2018-01-01','\u00d6')
            |""".stripMargin,
        s"""|INSERT INTO $tableName (name, city, date_info, unicode_col)
            | VALUES ('Portugal', 'Lisbon', '2018-10-01','\u00d9')
            |""".stripMargin,
        "commit"
      )
      // scalastyle:on nonascii
    )

}

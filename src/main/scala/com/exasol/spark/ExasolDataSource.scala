package com.exasol.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources.BaseRelation
import org.apache.spark.sql.sources.RelationProvider
import org.apache.spark.sql.sources.DataSourceRegister

class ExasolDataSource extends RelationProvider {
   override def createRelation(sqlContext: SQLContext, parameters: Map[String, String]): BaseRelation = {
     null
   }
}

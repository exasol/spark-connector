package com.exasol.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Row
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SaveMode

import org.mockito.Mockito.when
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

class DefaultSourceSuite extends AnyFunSuite with Matchers with MockitoSugar {

  test("when reading should throw an Exception if no `query` parameter is provided") {
    val sqlContext = mock[SQLContext]
    when(sqlContext.getAllConfs).thenReturn(Map.empty[String, String])
    val thrown = intercept[IllegalArgumentException] {
      new DefaultSource().createRelation(sqlContext, Map[String, String]())
    }
    assert(thrown.getMessage().startsWith("E-SCCJ-10"))
  }

  test("throws an Exception if host parameter is not an ip address") {
    val parameters = Map("query" -> "SELECT 1", "host" -> "a.b.c.d")
    val sqlContext = mock[SQLContext]
    when(sqlContext.getAllConfs).thenReturn(Map.empty[String, String])
    val thrown = intercept[IllegalArgumentException] {
      new DefaultSource().createRelation(sqlContext, parameters)
    }
    assert(thrown.getMessage().startsWith("E-SEC-4"))
    assert(thrown.getMessage().contains("host value should be an IPv4 address of the first Exasol datanode"))
  }

  test("when saving should throw an Exception if no `table` parameter is provided") {
    val df = mock[DataFrame]
    val sqlContext = mock[SQLContext]
    when(sqlContext.getAllConfs).thenReturn(Map.empty[String, String])
    val thrown = intercept[IllegalArgumentException] {
      new DefaultSource().createRelation(sqlContext, SaveMode.Append, Map[String, String](), df)
    }
    assert(thrown.getMessage().startsWith("E-SCCJ-10"))
  }

  test("`repartitionPerNode` should reduce dataframe partitions number") {
    val df = mock[DataFrame]
    val rdd = mock[RDD[Row]]

    when(df.rdd).thenReturn(rdd)
    when(rdd.getNumPartitions).thenReturn(2)

    val source = new DefaultSource()

    assert(source.repartitionPerNode(df, 2) === df)
    assert(source.repartitionPerNode(df, 2).rdd.getNumPartitions === 2)

    val repartedDF = mock[DataFrame]
    val repartedRdd = mock[RDD[Row]]
    when(repartedDF.rdd).thenReturn(repartedRdd)
    when(repartedRdd.getNumPartitions).thenReturn(3)

    when(df.repartition(3)).thenReturn(repartedDF)
    assert(source.repartitionPerNode(df, 3).rdd.getNumPartitions === 3)

    val coalescedDF = mock[DataFrame]
    val coalescedRdd = mock[RDD[Row]]
    when(coalescedDF.rdd).thenReturn(coalescedRdd)
    when(coalescedRdd.getNumPartitions).thenReturn(1)

    when(df.coalesce(1)).thenReturn(coalescedDF)
    assert(source.repartitionPerNode(df, 1) === coalescedDF)
    assert(source.repartitionPerNode(df, 1).rdd.getNumPartitions === 1)
  }

  test("`mergeConfigurations` should merge runtime sparkConf into user provided parameters") {
    val sparkConf = Map[String, String](
      "spark.exasol.username" -> "newUsername",
      "spark.exasol.host" -> "hostName",
      "spark.other.options" -> "irrelevance"
    )
    val parameters = Map[String, String]("username" -> "oldUsername", "password" -> "oldPassword")

    val newConf = new DefaultSource().mergeConfigurations(parameters, sparkConf)
    // overwrite config if both are provided
    assert(newConf.getOrElse("username", "not available") === "newUsername")

    // use config from parameters if sparkConf doesn't provide
    assert(newConf.getOrElse("password", "some random password") === "oldPassword")

    // use config from sparkConf if parameters doesn't provide
    assert(newConf.getOrElse("host", "some random host") === "hostName")

    // should not contains irrelevant options for exasol
    assert(!newConf.contains("spark.other.options") && !newConf.contains("options"))
  }
}

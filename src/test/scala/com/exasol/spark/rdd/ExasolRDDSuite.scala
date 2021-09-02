package com.exasol.spark.rdd

import java.sql.Statement

import org.apache.spark.SparkContext
import org.apache.spark.sql.types.StructType

import com.exasol.jdbc.EXAConnection
import com.exasol.jdbc.EXAResultSet
import com.exasol.spark.util.ExasolConnectionManager

import org.mockito.Mockito._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

class ExasolRDDSuite extends AnyFunSuite with Matchers with MockitoSugar {

  test("`getPartitions` returns correct set of partitions") {
    val sparkContext = mock[SparkContext]
    val mainConnection = mock[EXAConnection]
    val mainStatement = mock[Statement]
    val mainResultSet = mock[EXAResultSet]
    val manager = mock[ExasolConnectionManager]

    val handle: Int = 7

    when(manager.mainConnection).thenReturn(mainConnection)
    when(manager.subConnections(mainConnection)).thenReturn(Seq("url1", "url2"))
    when(mainConnection.createStatement()).thenReturn(mainStatement)
    when(mainStatement.executeQuery("")).thenReturn(mainResultSet)
    when(mainResultSet.GetHandle()).thenReturn(handle)

    val rdd = new ExasolRDD(sparkContext, "", StructType(Nil), manager)
    val partitions = rdd.getPartitions

    assert(partitions.size == 2)
    partitions.zipWithIndex.foreach { case (part, idx) =>
      assert(part.index === idx)
      assert(part.isInstanceOf[ExasolRDDPartition])
      assert(part.asInstanceOf[ExasolRDDPartition].handle === handle)
      assert(part.asInstanceOf[ExasolRDDPartition].connectionUrl === s"url${idx + 1}")
    }
    verify(manager, times(1)).mainConnection
    verify(manager, times(1)).subConnections(mainConnection)
  }

  test("`getPartitions` throws exceptions if main connection is null") {
    val sparkContext = mock[SparkContext]
    val manager = mock[ExasolConnectionManager]

    when(manager.mainConnection).thenReturn(null)

    val thrown = intercept[RuntimeException] {
      new ExasolRDD(sparkContext, "", StructType(Nil), manager).getPartitions
    }
    assert(thrown.getMessage === "Could not establish main connection to Exasol!")

    verify(manager, times(1)).mainConnection
  }

}

package com.exasol.spark.rdd

import org.apache.spark.SparkContext
import org.apache.spark.sql.types.StructType

import com.exasol.jdbc.EXAConnection
import com.exasol.spark.util.ExasolConnectionManager

import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.Matchers
import org.scalatest.mockito.MockitoSugar

class ExasolRDDSuite extends FunSuite with Matchers with MockitoSugar {

  test("`getPartitions` returns correct set of partitions") {
    val sparkContext = mock[SparkContext]
    val mainConnection = mock[EXAConnection]
    val manager = mock[ExasolConnectionManager]

    when(manager.mainConnection).thenReturn(mainConnection)
    when(manager.subConnections(mainConnection)).thenReturn(Seq("url1", "url2"))

    val rdd = new ExasolRDD(sparkContext, "", StructType(Nil), manager)
    val partitions = rdd.getPartitions

    assert(partitions.size == 2)
    partitions.zipWithIndex.foreach {
      case (part, idx) =>
        assert(part.index == idx)
        assert(part.isInstanceOf[ExasolRDDPartition])
        assert(part.asInstanceOf[ExasolRDDPartition].connectionUrl == s"url${idx + 1}")
    }
    verify(manager, times(1)).mainConnection
    verify(manager, times(1)).subConnections(mainConnection)
  }

  test("`getPartitions` throws exceptions if main connection is null") {
    val sparkContext = mock[SparkContext]
    val manager = mock[ExasolConnectionManager]

    when(manager.mainConnection).thenReturn(null) // scalastyle:ignore null

    val thrown = intercept[RuntimeException] {
      new ExasolRDD(sparkContext, "", StructType(Nil), manager).getPartitions
    }
    assert(thrown.getMessage === "Could not establish main connection to Exasol!")

    verify(manager, times(1)).mainConnection
  }

}

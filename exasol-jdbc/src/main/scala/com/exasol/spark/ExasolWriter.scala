package com.exasol.spark.writer

import java.sql.SQLException
import org.apache.spark.SparkContext
import org.apache.spark.TaskContext
//import org.apache.spark.scheduler.{SparkListener, SparkListenerTaskEnd}
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StructType
//import com.exasol.errorreporting.ExaError
//import com.exasol.jdbc.EXAConnection
import com.exasol.spark.common.ExasolOptions
import com.exasol.spark.util.Constants._
import com.exasol.spark.util.Converter
import com.exasol.spark.util.ExasolConnectionManager
import com.exasol.spark.util.Types

//import java.util.Calendar

/**
 */
class ExasolWriter(
  @transient val sc: SparkContext,
  tableName: String,
  rddSchema: StructType,
  options: ExasolOptions,
  hosts: Seq[String],
  manager: ExasolConnectionManager
) extends Serializable {

//  @transient var mainConnection: EXAConnection = null
//  @transient private var subConnection: EXAConnection = null

//  def closeMainResources(): Unit = {
//    val time = Calendar.getInstance().getTime
//    println(s"closeMainResources: $time")
//    if (mainConnection != null && !mainConnection.isClosed) {
//      println("main closed")
//      mainConnection.close()
//      mainConnection = null
//    } else {
//      println("main already closed")
//    }
//  }
//
//  def closeJobResources(): Unit = {
//    val time = Calendar.getInstance().getTime
//    println(s"closeJobResources: $time")
//    if (subConnection != null && !subConnection.isClosed) {
//      println("subconnection closed")
////      subConnection.close()
////      subConnection = null
//    } else {
//      println("already closed")
//    }
//  }
//
//  def startParallel(): Int = {
//    mainConnection = manager.writerMainConnection()
//
//    if (mainConnection == null) {
//      throw new RuntimeException(
//        ExaError
//          .messageBuilder("F-SEC-7")
//          .message("Could not create main JDBC connection to Exasol cluster.")
//          .mitigation("Please make sure that there network connection between Spark and Exasol clusters.")
//          .toString()
//      )
//    }
//
//    val cnt = manager.initParallel(mainConnection)
//
//    // Close Exasol main connection when SparkContext finishes. This is a lifetime of a Spark
//    // application.
//    sc.addSparkListener(new SparkListener {
//      override def onTaskEnd(taskEnd: SparkListenerTaskEnd): Unit =
//        closeJobResources()
//    })
//
//    // Populate hosts
//    hosts = manager.subConnections(mainConnection)
//
//    cnt
//  }

  def insertStmt(): String = {
    val columns = rddSchema.fields.map(_.name).mkString(",")
    val placeholders = rddSchema.fields.map(_ => "?").mkString(",")
    s"INSERT INTO $tableName ($columns) VALUES ($placeholders)"
  }

  def insertPartition(iter: Iterator[Row]): Unit = {
    val partitionId = TaskContext.getPartitionId()
    val subConnectionUrl = hosts(partitionId)
    println(s"opening subconn for partition $partitionId")
    val subConnection = manager.subConnection(subConnectionUrl)
    val stmt = subConnection.prepareStatement(insertStmt())

    val setters = rddSchema.fields.map(f => Converter.makeSetter(f.dataType))
    val nullTypes = rddSchema.fields.map(f => Types.jdbcTypeFromSparkDataType(f.dataType))
    val fieldCnt = rddSchema.fields.length

    val batchSize = if (options.containsKey(BATCH_SIZE)) options.get(BATCH_SIZE).toInt else DEFAULT_BATCH_SIZE

    try {
      var rowCnt = 0
      var totalCnt = 0

      while (iter.hasNext) {
        val row = iter.next()
        var i = 0
        while (i < fieldCnt) {
          if (row.isNullAt(i)) {
            stmt.setNull(i + 1, nullTypes(i))
          } else {
            setters(i).apply(stmt, row, i)
          }
          i = i + 1
        }
        stmt.addBatch()
        rowCnt += 1
        if (rowCnt % batchSize == 0) {
          val _ = stmt.executeBatch()
          totalCnt += rowCnt
          rowCnt = 0
        }
      }
      if (rowCnt > 0) {
        val _ = stmt.executeBatch()
        totalCnt += rowCnt
      }
      println(s"written $totalCnt rows")
      ()
    } catch {
      case ex: SQLException =>
        throw ex
    } finally {
      println(s"$partitionId: finally block")
      stmt.close()
//      subConn.commit()
      subConnection.close()
    }

  }

}

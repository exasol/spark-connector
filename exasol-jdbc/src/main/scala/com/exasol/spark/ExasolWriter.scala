package com.exasol.spark.writer

import java.sql.SQLException
import org.apache.spark.SparkContext
import org.apache.spark.TaskContext
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StructType
import com.exasol.spark.common.ExasolOptions
import com.exasol.spark.util.Constants._
import com.exasol.spark.util.Converter
import com.exasol.spark.util.ExasolConnectionManager
import com.exasol.spark.util.Types

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
  def insertStmt(): String = {
    val columns = rddSchema.fields.map(_.name).mkString(",")
    val placeholders = rddSchema.fields.map(_ => "?").mkString(",")
    s"INSERT INTO $tableName ($columns) VALUES ($placeholders)"
  }

  def insertPartition(iter: Iterator[Row]): Unit = {
    val partitionId = TaskContext.getPartitionId()
    val subConnectionUrl = hosts(partitionId)
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
      ()
    } catch {
      case ex: SQLException =>
        throw ex
    } finally {
      stmt.close()
      subConnection.close()
    }

  }

}

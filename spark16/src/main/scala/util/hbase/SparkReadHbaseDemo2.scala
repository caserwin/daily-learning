package util.hbase

import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SQLContext}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

import scala.language.implicitConversions
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

/**
  * Created by yidxue on 2018/5/22
  */
object SparkReadHbaseDemo2 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("SparkReadHBaseDemo2")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    val rawDF = getSourceTableDF("localhost:2181", "student", sqlContext, "rowkey,name,gender,age")
    rawDF.show(truncate = false)
    sc.stop()
  }

  def getSourceTableDF(zkAddr: String, sourceTableName: String, sqlContext: SQLContext, schemaString: String): DataFrame = {
    val hbaseConf = HBaseConfiguration.create()
    //    hbaseConf.set("hbase.mapreduce.scan.row.start", day)
    //    hbaseConf.set("hbase.mapreduce.scan.row.stop", TimeUtils.getAfterDay(day, "yyyy-MM-dd", "yyyy-MM-dd"))
    hbaseConf.set("hbase.zookeeper.quorum", zkAddr)
    hbaseConf.set(TableInputFormat.INPUT_TABLE, sourceTableName)
    val hbaseRDD = sqlContext.sparkContext.newAPIHadoopRDD(hbaseConf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result]).map(_._2)

    val hbaseRowRDD = hbaseRDD.map(result => {
      val records = result.listCells().toList.foldLeft(collection.mutable.Map.empty[String, String])(
        (map, cell) => map += (new String(CellUtil.cloneQualifier(cell)) -> new String(CellUtil.cloneValue(cell)))
      )
      val rowSeq = schemaString.split(",").foldLeft(ListBuffer.empty[String])(
        (list, field) => if ("rowkey".equals(field.toLowerCase)) list += Bytes.toString(result.getRow) else list += records.getOrElse(field, "")
      )
      Row.fromSeq(rowSeq)
    })
    val schema = StructType(schemaString.split(",").map(fieldName => StructField(fieldName, StringType, nullable = true)))
    sqlContext.createDataFrame(hbaseRowRDD, schema)
  }
}

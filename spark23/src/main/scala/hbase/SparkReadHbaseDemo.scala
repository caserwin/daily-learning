package hbase

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/5/7
  */
object SparkReadHbaseDemo {

  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession.builder.appName("HbaseTest").enableHiveSupport().getOrCreate()

    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "10.29.42.41,10.29.42.42,10.29.42.43")
    hbaseConf.set("hbase.master", "10.29.42.40:60010")
    hbaseConf.set("hbase.zookeeper.property.clientPort", "2181")

    hbaseConf.set(TableInputFormat.INPUT_TABLE, "student")
    hbaseConf.set(TableInputFormat.SCAN_ROW_START, "2018-05-18")
    hbaseConf.set(TableInputFormat.SCAN_ROW_STOP, "2018-11-27")
    hbaseConf.set(TableInputFormat.SCAN_COLUMNS, "info:name info:gender")
    val hbaseRDD = spark.sparkContext.newAPIHadoopRDD(
      hbaseConf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result]).cache()

    hbaseRDD.map(transformMap).collect().foreach(println(_))
    spark.stop()
  }

  def transformMap(tuple: (ImmutableBytesWritable, Result)): (String, String, String) = {
    val result = tuple._2
    val rowkey = Bytes.toString(result.getRow)
    val name = Bytes.toString(result.getValue("info".getBytes, "name".getBytes))
    val gender = Bytes.toString(result.getValue("info".getBytes, "gender".getBytes))

    (rowkey, name, gender)
  }
}

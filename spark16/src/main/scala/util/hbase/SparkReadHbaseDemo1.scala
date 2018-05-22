package util.hbase

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/5/18
  */
object SparkReadHbaseDemo1 {

  def createHbaseConf: Configuration = {
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "10.29.42.41,10.29.42.42,10.29.42.43")
    hbaseConf.set("hbase.master", "10.29.42.40:60010")
    hbaseConf.set("hbase.zookeeper.property.clientPort", "2181")
    hbaseConf
  }

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("SparkReadHBaseDemo")
    val sc = new SparkContext(sparkConf)

    val hbaseconf = createHbaseConf
    // 包括起始和终止所有记录。
    hbaseconf.set("hbase.mapreduce.scan.row.start", "2018-05-18")
    hbaseconf.set("hbase.mapreduce.scan.row.stop", "2018-05-19")
    hbaseconf.set(TableInputFormat.INPUT_TABLE, "student")

    val resRdd = sc.newAPIHadoopRDD(hbaseconf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result]).cache()

    println("count:" + resRdd.count().toString)
    resRdd.map(transformMap).collect().foreach(println(_))
  }

  def transformMap(tuple: (ImmutableBytesWritable, Result)): (String, String, String, String) = {
    val result = tuple._2
    val rowkey = Bytes.toString(result.getRow)
    val name = Bytes.toString(result.getValue("info".getBytes, "name".getBytes))
    val gender = Bytes.toString(result.getValue("info".getBytes, "gender".getBytes))
    val age = Bytes.toString(result.getValue("info".getBytes, "age".getBytes))

    (rowkey, name, gender, age)
  }
}
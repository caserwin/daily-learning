package util.hbase

import java.text.SimpleDateFormat
import java.util.Date
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.KeyValue.Type
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration}
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapred.JobConf
import org.apache.spark._


/**
  * Created by yidxue on 2018/5/18
  */
object SparkWriteHbaseDemo1 {

  def createHbaseConf: Configuration = {
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "127.0.0.1")
    hbaseConf.set("hbase.master", "127.0.0.1:60010")
    hbaseConf.set("hbase.zookeeper.property.clientPort", "2181")
    hbaseConf
  }

  val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("SparkWriteHBaseDemo")
    val sc = new SparkContext(sparkConf)

    val hbaseconf = createHbaseConf
    val jobConf = new JobConf(hbaseconf, this.getClass)
    jobConf.setOutputFormat(classOf[TableOutputFormat])
    jobConf.set(TableOutputFormat.OUTPUT_TABLE, "student")

    val indataRDD = sc.makeRDD(Array("1,Rongcheng,M,26", "2,Guanhua,M,27")) //构建两行记录
    val rdd = indataRDD.map(_.split(',')).map {
      arr => {
        val rowkey = dateFormat.format(new Date()) + "_" + arr(0)
        val put = new Put(Bytes.toBytes(rowkey)) //行健的值
        val timestamp = dateFormat.parse(dateFormat.format(new Date())).getTime / 1000
        val cellType = Type.Put

        val nameCell = CellUtil.createCell(Bytes.toBytes(rowkey), Bytes.toBytes("info"), Bytes.toBytes("name"), timestamp, cellType.getCode, Bytes.toBytes(arr(1)))
        val genderCell = CellUtil.createCell(Bytes.toBytes(rowkey), Bytes.toBytes("info"), Bytes.toBytes("gender"), timestamp, cellType.getCode, Bytes.toBytes(arr(2)))
        val ageCell = CellUtil.createCell(Bytes.toBytes(rowkey), Bytes.toBytes("info"), Bytes.toBytes("age"), timestamp, cellType.getCode, Bytes.toBytes(arr(3)))

        put.add(nameCell)
        put.add(genderCell)
        put.add(ageCell)
        (new ImmutableBytesWritable, put)
      }
    }
    rdd.saveAsHadoopDataset(jobConf)
  }
}

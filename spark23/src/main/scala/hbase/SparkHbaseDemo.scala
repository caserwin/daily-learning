package hbase

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/5/7
  * 参考：http://lxw1234.com/archives/2015/07/335.htm
  */
object SparkHbaseDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("simple demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val conf = HBaseConfiguration.create()
    conf.set(TableInputFormat.INPUT_TABLE, "test")
    val hbaseRDD = sc.newAPIHadoopRDD(conf, classOf[org.apache.hadoop.hbase.mapreduce.TableInputFormat], classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable], classOf[org.apache.hadoop.hbase.client.Result])

    println(hbaseRDD.count)
  }
}

package util.phoenix

import java.sql.Connection
import org.apache.phoenix.spark._
import org.apache.spark.sql.{SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}
import util.phoenix.conn.PhoenixConn


/**
  * 1 官网参考: https://phoenix.apache.org/phoenix_spark.html
  * 2 github参考: https://github.com/apache/phoenix/tree/master/phoenix-spark
  */

object SparkPhoenixDemo1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RowKey Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    // 参数
    val zkAddr = "localhost:2181"

    // 创建phoenix表
    val tableName = "ROWKEYTEST"
    val fields = Seq("ID", "NAME", "CITY", "NUM")
    val conn = new PhoenixConn(zkAddr).getConn
    createPhoenixTable(tableName, fields, conn)

    // 数据源
    val dataSeq1 = Seq(
      ("id1238", "aaa", "wuhan", "unknown"),
      ("id1239", "bbb", "tianjin", "0.3"),
      ("id1240", "ccc", "shenzhen", "0.4")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF(fields: _*)

    inputDF.show()
    // 写入phoenix表
    inputDF.write.format("org.apache.phoenix.spark").mode(SaveMode.Overwrite).options(Map("table" -> tableName, "zkUrl" -> zkAddr)).save()

    // 查询phoenix表
    val df = sqlContext.phoenixTableAsDataFrame(table = tableName, columns = Seq("ID", "CITY", "NUM"), predicate = Some(s"ID like 'id%'"), zkUrl = Some(zkAddr))
    df.show()
  }

  /**
    * 建表
    */
  def createPhoenixTable(tableName: String, fields: Seq[String], conn: Connection, buckets: Long = 20, TTL: Long = 31536000): Int = {
    val sql = "CREATE TABLE IF NOT EXISTS " + tableName + s"(${fields.head} VARCHAR PRIMARY KEY," + fields.drop(1).mkString("INFO.", " VARCHAR, INFO.", " VARCHAR)") + " SALT_BUCKETS=" + buckets + "," + " TTL=" + TTL
    println(sql)
    conn.createStatement.executeUpdate(sql)
  }
}

package util.phoenix

import org.apache.spark.sql.{SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.phoenix.spark._
import org.apache.spark.sql.functions.when
import util.phoenix.conn.PhoenixConn


/**
  * 1 官网参考: https://phoenix.apache.org/phoenix_spark.html
  * 2 github参考: https://github.com/apache/phoenix/tree/master/phoenix-spark
  */

object RowKeyBySparkTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RowKey Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    // 参数
    val zkAddr = "10.29.42.42:2181"
    val day = "2017-12-02"
    val afterDay = "2017-12-04"

    // 创建phoenix表
    val tableName = "ROWKEYTEST"
    val fields = Seq("ROWKEY", "NAME", "CITY", "NUM")
    val conn = new PhoenixConn(zkAddr).getConn
    PhoenixJDBCService.createPhoenixTable(tableName, fields, conn)

    // 数据源
    val dataSeq1 = Seq(
      ("2017-12-02_id1238", "aaa", "wuhan", "unknown"),
      ("2017-12-03_id1239", "bbb", "tianjin", "0.3"),
      ("2017-12-03_id1240", "ccc", "shenzhen", "0.4")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF(fields: _*)

    inputDF.show()
    // 写入phoenix表
    inputDF.write.format("org.apache.phoenix.spark").mode(SaveMode.Overwrite).options(Map("table" -> tableName, "zkUrl" -> zkAddr)).save()

    // 查询phoenix表。其他查询方式：http://phoenix.apache.org/phoenix_spark.html
    val df = sqlContext.phoenixTableAsDataFrame(table = tableName, columns = Seq("ROWKEY", "CITY", "NUM"), predicate = Some(s"rowkey between '$day' and '$afterDay'"), zkUrl = Some(zkAddr))
    df.show()

    // 更新数据
    val dataSeq2 = Seq(("wuhan", "0.8"), ("beijing", "0.5"))
    val updateDF = sc.parallelize(dataSeq2).toDF("CITY", "NUM_UPDATE")

    val newDF = df.join(updateDF, Seq("CITY"), "left_outer")
      .select(
        $"ROWKEY",
        $"NUM",
        $"CITY",
        $"NUM_UPDATE"
      )
      .withColumn("NUM", when($"NUM" === "unknown", $"NUM_UPDATE").otherwise($"NUM"))
      .drop("NUM_UPDATE")

    newDF.show()

    // 把更新的数据写入phoenix
    newDF.write.format("org.apache.phoenix.spark").mode(SaveMode.Overwrite).options(Map("table" -> tableName, "zkUrl" -> zkAddr)).save()
  }
}

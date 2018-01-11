package phoenix

import org.apache.spark.sql.{SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.phoenix.spark._


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

    // 日期
    val day = "2017-12-01"
    val afterDay = "2017-12-02"

    // 数据源
    val tableName = "ROWKEYTEST"
    val fields = Seq("ROWKEY", "NAME", "CITY", "DISCOUNT")
    val zkAddr = "10.29.42.42:2181"

    val dataSeq1 = Seq(
      ("2017-12-02_id1238", "aaa", "wuhan", "0.2"),
      ("2017-12-03_id1239", "bbb", "tianjin", "0.3"),
      ("2017-12-03_id1240", "ccc", "shenzhen", "0.4")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF("ROWKEY", "NAME", "CITY")

    // 写入phoenix表
    inputDF.write.format("org.apache.phoenix.spark").mode(SaveMode.Overwrite).options(Map("table" -> "RowKeyTest", "zkUrl" -> zkAddr)).save()

    // 查询phoenix表。其他查询方式：http://phoenix.apache.org/phoenix_spark.html
    val df = sqlContext.phoenixTableAsDataFrame(table = tableName, columns = fields, predicate = Some(s"rowkey between '$day' and '$afterDay'"), zkUrl = Some(zkAddr))
    df.show()

  }
}

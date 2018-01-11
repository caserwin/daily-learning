//package spark.phoenix
//
//import org.apache.spark.sql.SparkSession
//
///**
//  * User: Erwin
//  * Date: 17/12/28 上午10:04
//  * Description:
//  */
//object RowKeyByJDBCTest {
//
//  def main(args: Array[String]): Unit = {
//    val spark = SparkSession.builder().appName("Spark SQL basic example").config("spark.master", "local[*]").getOrCreate()
//    import spark.implicits._
//
//    // 数据源
//    val dataSeq1 = Seq(
//      ("a1dsc", "zhangsan", "hangzhou"),
//      ("a2dsd", "lisi", "beijing"),
//      ("a3ace", "wangwu", "shanghai")
//    )
//    val inputDF = spark.parallelize(dataSeq1).toDF("ROWKEY", "NAME", "CITY")
//
//    // 创建phoenix表
//    val fields = "ROWKEY,NAME,CITY"
//    PhoenixJDBCService.createPhoenixTable("RowKeyTest", fields, "10.29.42.42:2181")
//
//    // 写入phoenix表
//
//    // 查询phoenix表
//
//    // 删除表中数据
//
//    // 修改表中数据
//
//    // 删除表
//  }
//}

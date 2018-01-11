//package spark.phoenix
//
//import org.apache.phoenix.spark._
//import org.apache.spark.sql.{SaveMode, SparkSession}
//
//
//object RowKeyBySparkTest {
//  def main(args: Array[String]): Unit = {
//    val spark = SparkSession.builder().appName("Spark SQL basic example").config("spark.master", "local[*]").getOrCreate()
//    import spark.implicits._
//
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
//
//
//    // 写入phoenix表
//    inputDF.write.format("org.apache.phoenix.spark").mode(SaveMode.Overwrite).options(Map("table" -> "RowKeyTest", "zkUrl" -> "10.29.42.42:2181")).save()
//
//    // 查询phoenix表。
//    val df = new SparkSqlContextFunctions(spark.sqlContext).phoenixTableAsDataFrame("SAP_MEETINGJMF", columns = Seq("CONFID", "UID_", "GID", "TIMESTAMP"), predicate = Some("rowkey between '2017-12-05' and '2017-12-06'"), conf = configuration)
//    df.show()
//
//    // 删除表中数据
//
//    // 修改表中数据
//
//    // 删除表
//
//  }
//}

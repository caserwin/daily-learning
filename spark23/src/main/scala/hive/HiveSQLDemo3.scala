package hive

import java.io.File
import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * Created by yidxue on 2018/8/7
  */
object HiveSQLDemo3 {

  private val warehouseLocation = new File("/user/hive/warehouse").getAbsolutePath

  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession
      .builder.appName("SQL Application")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .enableHiveSupport()
      .getOrCreate()
    import spark.implicits._
    // 插入hive字段必须小写
    val fields = Seq("id", "name", "city", "l_date")

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou", "2018-07-01"),
      (2, "lisi", "hangzhou", "2018-07-01"),
      (3, "wangwu", "shanghai", "2018-07-02"),
      (4, "zhaoliu", "shanghai", "2018-07-02")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF(fields: _*)

    // 存储表，不分区
    inputDF.write.mode(SaveMode.Overwrite).saveAsTable("testtable3")

    // 动态存储分区表: 字段要小写
    spark.sqlContext.setConf("hive.exec.dynamic.partition", "true")
    spark.sqlContext.setConf("hive.exec.dynamic.partition.mode", "nonstrict")
    // 插入hive字段必须小写
//    inputDF.write.mode(SaveMode.Append).partitionBy("l_date").format("hive").saveAsTable("testtable4")    // 不推荐，有bug
//    inputDF.write.mode(SaveMode.Append).partitionBy("l_date").saveAsTable("testtable4")                   // 这个可以用
    inputDF.write.mode(SaveMode.Overwrite).partitionBy("l_date").saveAsTable("testtable4")                  // 推荐，这个也可以用

    spark.stop()
  }
}

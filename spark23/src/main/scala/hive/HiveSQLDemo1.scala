package hive

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/1/28
  */
object HiveSQLDemo1 {
  private val warehouseLocation = new File("/user/hive/warehouse").getAbsolutePath

  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession
      .builder.appName("SQL Application")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .enableHiveSupport()
      .getOrCreate()
    import spark.implicits._
    val curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date)
    val fields = Seq("id", "name", "city")

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF(fields: _*)

    HiveUtil.createHiveTable("testtable1", fields)
    HiveUtil.insertHiveTable("testtable1", curDate, inputDF, fields)

    spark.stop()
  }
}

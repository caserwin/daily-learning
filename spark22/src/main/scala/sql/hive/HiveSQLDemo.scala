package sql.hive

import java.text.SimpleDateFormat
import java.util.Date
import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/1/28
  */
object HiveSQLDemo {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date)
    val fields = Seq("id", "name", "city")

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF(fields: _*)

    HiveUtil.createHiveTable(spark, "testTable", curDate, fields)
    HiveUtil.insertHiveTable(spark, "testTable", curDate, inputDF, fields)
  }
}

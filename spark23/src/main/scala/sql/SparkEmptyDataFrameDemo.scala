package sql

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/11/13
  * https://stackoverflow.com/questions/31477598/how-to-create-an-empty-dataframe-with-a-specified-schema
  */
object SparkEmptyDataFrameDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._
    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF1 = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city")
    val oldSiteDF = Seq.empty[(String, String, String)].toDF("id", "name", "city")

    inputDF1.union(oldSiteDF).show()

    spark.stop()
  }
}

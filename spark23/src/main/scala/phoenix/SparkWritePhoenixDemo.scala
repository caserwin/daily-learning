package phoenix

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * Created by yidxue on 2018/8/8
  */
object SparkWritePhoenixDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Spark SQL basic example").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF1 = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city")

    inputDF1.write.format("org.apache.phoenix.spark").mode(SaveMode.Overwrite).options(Map("table" -> "test", "zkUrl" -> "localhost:2181")).save()
  }
}

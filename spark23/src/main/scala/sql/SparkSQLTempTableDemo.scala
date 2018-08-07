package sql

import org.apache.spark.sql.SparkSession

object SparkSQLTempTableDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )

    val input1 = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city")

    // 创建局部视图
    input1.createOrReplaceTempView("people")
    spark.sql("select * from people").show()
    // 以下code报错
    //    spark.newSession().sql("SELECT * FROM people").show()

    // 创建全局视图
    input1.createGlobalTempView("people1")
    spark.sql("select * from global_temp.people1").show()
    spark.newSession().sql("SELECT * FROM global_temp.people1").show()


    spark.stop()
  }
}

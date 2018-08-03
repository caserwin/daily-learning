package sql.createDF

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/7/31
  */
object SparkSQLCreateDFDemo1 {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq = Seq(
      (1, "zhangsan", "cd"),
      (1, "lisi", "33"),
      (3, "wangwu", "shanghai")
    )

    val inputDF = spark.sparkContext.parallelize(dataSeq).toDF("id", "name", "city")
    inputDF.show()
  }
}

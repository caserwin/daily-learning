package sql

import org.apache.spark.sql.SparkSession

object SparkSQLWhenCaseSQLDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou", "0"),
      (2, "lisi", "beijing", "1"),
      (3, "wangwu", "shanghai", "1")
    )

    val input1 = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city", "sex")

    input1.createOrReplaceTempView("person")
    val sql = "select CASE WHEN sex ='1' THEN '男' WHEN sex = '0' THEN '女' ELSE '其他' END sex, id,name,city from person"

    val df1 = spark.sql(sql)
    df1.show()
  }
}

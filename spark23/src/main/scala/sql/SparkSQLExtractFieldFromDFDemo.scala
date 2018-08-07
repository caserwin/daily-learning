package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.avg

/**
  * Created by yidxue on 2018/4/12
  */
object SparkSQLExtractFieldFromDFDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq = Seq(
      ("1", "lisi1", 1),
      ("1", "lisi2", 2),
      ("1", "wangwu3", 3),
      ("2", "wangwu4", 4)
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq).toDF("id", "name", "num")

    // extracting single value from DataFrame
    println("整个DF平均值：" + inputDF.select(avg("id")).head().getDouble(0))
    println("第1行，第3列：" + inputDF.head().getInt(2))
    println("第4行，第1列：" + inputDF.rdd.take(4).last.getString(0))
    println("列名为name，第2行：" + inputDF.select("name").map(r => r.getString(0)).collect.toList(1))
    println("把DF中第一行，放到List中，并返回DF中的第1行：" + inputDF.takeAsList(1))
    println("把DF中第一行，放到List中，并返回DF中的第1行，第2列：" + inputDF.takeAsList(1).get(0).get(1))


    spark.stop()
  }
}

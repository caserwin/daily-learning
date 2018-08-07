package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._

/**
  * Created by yidxue on 2018/3/2
  *
  * 举例一些 spark sql function 的例子，更多的例子得根据实际业务，自己探索、、、
  */
object SparkSQLFunctionsDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    // create dataframe
    val dataSeq1 = Seq(
      ("a", 3, 2, ""),
      ("a", 3, 1, "BeiJing"),
      ("a", 7, 3, "ShangHai"),
      ("a", 5, 6, "HangZhou"),
      ("b", 6, 3, "ShenZheng"),
      ("b", 3, 8, "ChangChun"),
      ("b", 2, 4, "WuHan"),
      ("b", 3, 3, "HeFei")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF("category", "num1", "num2", "city")

    // common function
    inputDF
      .select(
        $"num1".alias("id1"), // 字段重命名
        sqrt($"num1"), // 字段开方
        lower($"city"), // lower: 小写转大写
        upper($"city") // upper: 大写转小写
      )
      .sort(asc("id1")) // 按照 id1 进行升序排序
      .show()

    // Aggregate statistic function
    inputDF
      .select(
        mean($"num1"), // num1 字段的均值
        variance($"num1"), // num1 字段的方差
        stddev($"num1"), // num1 字段的标准差
        corr($"num1", $"num2"), // num1，num2 字段的 person相关系数
        skewness($"num1"), // num1 字段的 skewness 偏度
        kurtosis($"num1") // num1 字段的 kurtosis 峰度
      )
      .show()

    sc.stop()
  }
}

package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions._

/**
  * Created by yidxue on 2018/1/29
  */
object SparkSQLGroupByDemo1 {

  val flatten: UserDefinedFunction = udf((xs: Seq[Seq[String]]) => xs.flatten)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq = Seq(
      ("1", "lisi", "12", Seq(1, 2, 3, 4)),
      ("1", "lisi", "3", Seq(4, 5, 6)),
      ("1", "wangwu", "3", Seq(6, 7, 8)),
      ("2", "wangwu", "4", Seq(1, 3, 4))
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq).toDF("id", "name", "num", "array")
    inputDF.printSchema()

    // 对 array 类型进行聚合
    inputDF.groupBy("id").agg(sum(size($"array")).alias("arraySum")).show()

    // 对 array 类型 merge
    inputDF.groupBy("id").agg(flatten(collect_list("array")).alias("mergeArray")).show(truncate = false)

    // 聚合
    inputDF.groupBy("id").agg(count(lit(1)).alias("COUNT")).show()
    inputDF.groupBy("id").agg(sum($"num".cast("integer")).alias("COUNT")).show()

    spark.stop()
  }
}

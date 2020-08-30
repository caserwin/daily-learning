package sql.myudf

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions._

/**
  * Created by yidxue on 2020-08-30
  */
object SparkSQLArrayInUDFDemo {

  val createBucketUDF: UserDefinedFunction = udf((rand: Double, bucketNum: Seq[Int], bucketWeight: Seq[Double]) => dd(rand, bucketNum, bucketWeight))

  def dd(c: Double, a: Seq[Int], b: Seq[Double]): Int = {
    a(2)
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    // dataframe filter
    val dataSeq1 = Seq(
      ("1", "zhangsan", ""),
      ("2", "lisi", "beijing"),
      ("3", "wangwu", "shanghai")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city")

    val bucketNum: Seq[Int] = "1,2,3,4".split(",").map(x => x.toInt).toSeq
    val bucketWeight: Seq[Double] = "1,2,3,4".split(",").map(x => x.toDouble).toSeq

    val outdf = inputDF
      .withColumn("rand", rand())
      .withColumn("bucketNum", typedLit(bucketNum))
      .withColumn("bucketWeight", typedLit(bucketWeight))
      .withColumn("bucket", createBucketUDF($"rand", $"bucketNum", $"bucketWeight"))

    outdf.show(100, truncate = false)
    outdf.printSchema()

    spark.stop()
  }
}

package sql

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/11/19
  */
object ProcessContextDemo {

  var processContext: ProcessContext = _
  def main(args: Array[String]): Unit = {
    processContext = new ProcessContext("test app")
    val spark: SparkSession = processContext.spark
    import spark.implicits._

    val dataSeq1 = Seq(
      ("yidxue", 20, "aa bb", List(("a", 1), ("a", 2), ("b", 2))),
      ("erwin", 20, "aa cc", List(("b", 1), ("c", 1))),
      ("yuyi", 20, "bb cc", List(("b", 1), ("c", 1)))
    )

    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF("name", "age", "interest", "other")
    inputDF.show()
    println(processContext.jobStartTime)

    spark.stop()
  }
}

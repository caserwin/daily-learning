package core.transformation

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/5/7
  * 该函数根据权重，将一个RDD切分成多个RDD。
  */
object SparkRandomSplitDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local[*]").getOrCreate()
    val rdd = spark.sparkContext.makeRDD(1 to 11, 10)

    val RDDArray = rdd.randomSplit(Array(1.0, 2.0, 3.0, 4.0))
    println(RDDArray.length)
  }
}

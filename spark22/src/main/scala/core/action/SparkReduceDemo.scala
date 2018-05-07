package util.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/4/12
  */
object SparkReduceDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("spark demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.parallelize(Array(1, 2, 3, 4, 5))
    val res: Int = rdd.reduce(_ + _)
    println(res)
  }
}

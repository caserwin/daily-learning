package core.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/4/12
  */
object SparkCountDemo {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("spark demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val a = sc.parallelize(List("dog", "tiger", "lion", "cat", "panther", "eagle"), 2)

    println(a.count())
  }
}

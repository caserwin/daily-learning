package util.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/4/12
  * top 可以排序（正序、倒序、自定义排序）后，取前n个数值
  */
object SparkTopDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("spark demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val c = sc.parallelize(Array(6, 9, 4, 7, 5, 8), 2)

    val arr1: Array[Int] = c.top(2)
    arr1.foreach(println(_))

    implicit val myOrd = implicitly[Ordering[Int]].reverse
    val arr2: Array[Int] = c.top(2)
    arr2.foreach(println(_))
  }
}

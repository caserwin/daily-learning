package core.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/4/12
  * takeOrdered 可以排序（正序、倒序、自定义排序）后，取前n个数值。
  * 和 Top 不同在于，takeOrdered 默认是正序排序后取前n。
  */
object SparkTakeOrderedDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("spark demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val c = sc.parallelize(Array(6, 9, 4, 7, 5, 8), 2)
    // 默认取正序排序后，前n个数。
    val arr: Array[Int] = c.takeOrdered(2)

    arr.foreach(println(_))
  }
}

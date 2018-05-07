package util.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/4/12
  * take: 用于获取RDD中从0到num-1下标的元素，不排序。
  */
object SparkTakeDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("spark demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val arr = sc.parallelize(Array(6, 9, 4, 7, 5, 8), 2)

    // 取前3个数
    val arrTake: Array[Int] = arr.take(3)
    arrTake.foreach(println(_))
  }
}

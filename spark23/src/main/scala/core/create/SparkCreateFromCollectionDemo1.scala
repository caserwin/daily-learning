package core.create

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/3/17
  * 从集合创建 RDD: 主要有 makeRDD 和 parallelize 两种方式
  */
object SparkCreateFromCollectionDemo1 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("simple demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val input = 1 to 10
    sc.makeRDD(input).foreach(println(_))

    sc.parallelize(input).foreach(println(_))
  }
}

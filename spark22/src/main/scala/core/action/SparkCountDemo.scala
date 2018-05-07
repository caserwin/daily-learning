package util.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/4/12
  * countByKey 和 count demo
  */
object SparkCountDemo {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("spark demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.parallelize(List("dog", "tiger", "lion", "cat", "panther", "eagle"), 2)
    println(rdd.count())

    val rdd1 = sc.makeRDD(Array(("A",0),("A",2),("B",1),("B",2),("B",3)))
    println(rdd1.countByKey)
  }
}

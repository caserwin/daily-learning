package demo

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/4/4
  */
object SparkWordCountDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val env = new SparkContext(sparkConf)

    val data = List("hi", "spark cluster", "hi", "spark")
    val dataSet = env.parallelize(data)
    val words = dataSet.flatMap(value => value.split("\\s+"))
    val mappedWords = words.map(value => (value, 1))
    val sum = mappedWords.reduceByKey(_ + _)
    println(sum.foreach(println(_)))
  }
}

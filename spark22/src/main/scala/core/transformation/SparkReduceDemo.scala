package core.transformation

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/5/7
  */
object SparkReduceDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val textFile = sc.textFile("data/README.md")
    // scala 中集合的长度可用 size 和 length 两种方法实现。
    val maxCount = textFile.map(line => line.split(" ").length).reduce((a, b) => if (a > b) a else b)
    println(maxCount)
    //    val wordCounts = textFile.flatMap(line => line.split(" ")).map(x => (x, 1L)).groupByKey(identity).count()
    //    wordCounts.collect()
  }
}

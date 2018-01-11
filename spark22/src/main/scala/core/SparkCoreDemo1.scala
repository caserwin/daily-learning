package core

import org.apache.spark.{SparkConf, SparkContext}

object SparkCoreDemo1 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val textFile = sc.textFile("data/README.md")

    println("文件总行数: "+textFile.count())
    println("第一行内容: "+textFile.first())
    println("统计包含 Spark 的行数: "+ textFile.filter(line => line.toLowerCase.contains("spark")).count())

    // scala 中集合的长度可用 size 和 length 两种方法实现。
    val maxCount = textFile.map(line => line.split(" ").length).reduce((a, b) => if (a > b) a else b)
    println(maxCount)

//    val wordCounts = textFile.flatMap(line => line.split(" ")).map(x => (x, 1L)).groupByKey(identity).count()
//    wordCounts.collect()
  }
}

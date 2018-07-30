package core.create

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/5/7
  *
  * 这种形式的 textFile 返回 RDD类型
  */
object SparkTextFileDemo1 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val textFile = sc.textFile("data/README.md")

    println("文件总行数: " + textFile.count())
    println("第一行内容: " + textFile.first())
  }
}

package core.transformation.basic

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/3/17
  */
object SparkPartitionsDemo1 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    // 读数据时，设置分区个数
    val rdd = sc.parallelize(Array("job of day last for"), 3).flatMap(line => line.split("\\s+"))

    // 查看rdd分区个数
    println("getNumPartitions 查看分区个数：" + rdd.getNumPartitions.toString)
    println("partitionsLength 查看分区个数：" + rdd.partitions.length)

    // 重新调整分区
    val rddRepart = rdd.repartition(2)
    println("分区个数：" + rddRepart.getNumPartitions.toString)

    // 打印全部数据
    rddRepart.foreach(println(_))
    sc.stop()
  }
}

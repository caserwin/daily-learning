package core.transformation

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/5/7
  * 函数作用同mapPartitions，不过提供了两个参数，第一个参数为分区的索引。
  */
object SparkPartitionsDemo2 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    //设为 rdd1 两个分区
    val rdd1 = sc.makeRDD(1 to 5, 2)
    //rdd2将rdd1中每个分区的数字累加，并在每个分区的累加结果前面加了分区索引
    val rdd2 = rdd1.mapPartitionsWithIndex {
      (x, iter) => {
        val result = List[String]()
        var i = 0
        while (iter.hasNext) {
          i += iter.next()
        }
        result.::(x + "|" + i).iterator
      }
    }
    rdd2.foreach(println(_))

    // 打印 rdd1 第一个分区的数据
    rdd1.mapPartitionsWithIndex((idx, iter) =>
      if (idx == 0) {
        iter
      } else {
        Iterator.empty
      }).foreach(println(_))
  }
}

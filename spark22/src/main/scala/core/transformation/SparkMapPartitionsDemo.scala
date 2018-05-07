package core.transformation

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/5/7
  */
object SparkMapPartitionsDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val rdd1 = sc.makeRDD(1 to 5, 2)

    rdd1.mapPartitions {
      x => {
        val result = List[Int]()
        var i = 0
        while (x.hasNext) {
          i += x.next()
        }
        result.::(i).iterator
      }
    }.foreach(println(_))

    sc.stop()
  }
}

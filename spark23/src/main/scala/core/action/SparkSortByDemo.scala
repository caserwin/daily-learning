package core.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/4/12
  */
object SparkSortByDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("spark demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val rdd1 = sc.makeRDD(Array(3, 6, 7, 1, 2, 0))
    // 升序排序
    rdd1.sortBy(x => x, ascending = true).collect.foreach(println(_))

    val rdd2 = sc.makeRDD(Array(("A", 2), ("A", 1), ("B", 6), ("B", 3), ("B", 7)))
    // 降序排序
    rdd2.sortBy(x => x._2, ascending = false).collect.foreach(println(_))
  }
}

package core.transformation.kv

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/5/7
  */
object SparkLeftOuterJoinDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("spark demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val rdd1 = sc.makeRDD(Array(("A", "1"), ("B", "2"), ("C", "3")), 2)
    val rdd2 = sc.makeRDD(Array(("A", "a"), ("C", "c"), ("D", "d")), 2)

    rdd1.leftOuterJoin(rdd2).foreach(println(_))
    sc.stop()
  }
}

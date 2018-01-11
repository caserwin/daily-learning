package core

import org.apache.spark.{SparkConf, SparkContext}

object SparkSaveAsText {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val rdd1 = sc.makeRDD(Seq("job of day: last for "+1000))
    rdd1.repartition(1).saveAsTextFile("runtime")
  }
}

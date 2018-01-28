package core

import org.apache.spark.{SparkConf, SparkContext}

object SparkRDDSaveAsTextDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(Seq("job of day: last for "+1000))

    // 要合成一个文件，必须有.repartition(1)
    rdd.repartition(1).saveAsTextFile("output")
  }
}

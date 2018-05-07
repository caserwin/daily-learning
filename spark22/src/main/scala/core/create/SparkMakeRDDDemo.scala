package util.create

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/3/17
  */
object SparkMakeRDDDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("simple demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val output = Seq("aaaaa", "bbbb")
    sc.makeRDD(output).foreach(println(_))
  }
}

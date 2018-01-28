package core

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object SparkDataFrameSaveAsTextDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    val df = sqlContext.read.json("data/cityinfo.json")
    df.show()

    df.rdd.map(x=>x.mkString("\t\t")).repartition(1).saveAsTextFile("output")

  }
}

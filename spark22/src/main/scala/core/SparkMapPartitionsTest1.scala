package core

import org.apache.spark.{SparkConf, SparkContext}

/**
  * User: Erwin
  * Date: 17/11/21 下午5:02
  * Description: 
  */
object SparkMapPartitionsTest1 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val aRdd = sc.parallelize(1 to 9, 3)

    val result = aRdd.mapPartitions(
      iter => {
        val res = for (cur <- iter) yield {val c =1; (cur, cur * 2)}
        println("==================")
        res
      }
    )
    println(result.collect().mkString)
    println(result.partitions.length)
  }
}

package core

import org.apache.spark.{SparkConf, SparkContext}

/**
  * User: Erwin
  * Date: 17/11/21 下午4:46
  * Description: 
  */
object SparkMapTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val aRdd = sc.parallelize(1 to 9, 3)

    val result = aRdd.map((a: Int) => {
      println("==================")
      (a, a * 2)
    })

    println(result.collect().mkString)
    println(result.partitions.length)
  }
}

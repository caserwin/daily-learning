package performace

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/10/10
  */
object SparkMapPartitionsDemo3 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val aRdd = sc.parallelize(1 to 9, 4)

//    aRdd.foreach(println(_))

    val result = aRdd.mapPartitions(
      iter => {
        val newPartition = iter.map(x => x + 1)
        newPartition.foreach(println(_))
        println("=========")
        newPartition
      }
    )

    result.foreach(println(_))
    println(result.partitions.length)
  }
}

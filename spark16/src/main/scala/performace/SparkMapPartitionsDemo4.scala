package performace

import org.apache.spark.{SparkConf, SparkContext}
import util.DataSouce

/**
  * Created by yidxue on 2018/10/22
  * https://stackoverflow.com/questions/45648913/map-partition-iterator-return
  */
object SparkMapPartitionsDemo4 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val data = List("a", "b", "c", "d")

    val aRdd = sc.parallelize(data, 2)
    val result = aRdd.mapPartitions(
      iter => {
        val cond = "'" + iter.reduce((x, y) => x + "','" + y) + "'"
        import scala.collection.JavaConversions._
        DataSouce.getElements(cond).toList.iterator
      }
    )
    result.foreach(println(_))
    println(result.partitions.length)
  }
}

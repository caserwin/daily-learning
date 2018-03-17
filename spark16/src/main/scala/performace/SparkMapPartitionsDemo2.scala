package performace

import org.apache.spark.{SparkConf, SparkContext}

/**
  * User: Erwin
  * Date: 17/11/21 下午4:46
  * Description:
  * http://blog.csdn.net/lsshlsw/article/details/48627737
  */
object SparkMapPartitionsDemo2 {
  def doubleFunc(iter: Iterator[Int]): Iterator[(Int, Int)] = {
    var res = List[(Int, Int)]()
    while (iter.hasNext) {
      val cur = iter.next
      res.::=(cur, cur * 2)
    }
    res.iterator
  }

  def doubleFunc1(iter: Iterator[Int]): Iterator[(Int, Int)] = {
    val res = for (cur <- iter) yield (cur, cur * 2)
    res
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val aRdd = sc.parallelize(1 to 9, 3)

    val result = aRdd.mapPartitions(doubleFunc1)
    println(result.collect().mkString)
  }
}

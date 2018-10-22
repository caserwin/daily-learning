package performace

import org.apache.spark.{SparkConf, SparkContext}

/**
  * User: Erwin
  * Date: 17/11/21 下午5:02
  * Description:
  * 如果是java贝尔安bean 的，必须加上：
  * implicit val SensorDataEncoder1: Encoder[MeetingValueBean] = Encoders.bean(classOf[bean.MeetingValueBean])
  */
case class test() {
  println("==================")

  def colse(): Unit = {
    println("close !")
  }
}

object SparkMapPartitionsDemo1 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val aRdd = sc.parallelize(1 to 9, 3)

    aRdd.foreach(println(_))

    val result = aRdd.mapPartitions(
      iter => {
        val t = new test()
        for (cur <- iter) yield {
          if (cur % 2 == 0) {
            (cur, cur * 2)
          }
        }
      }
    )

    println(result.count())
    println(result.collect().mkString)
    println(result.filter(x => filterSize(x)).collect().mkString)
    println(result.partitions.length)
  }

  def filterSize(ss: Any): Boolean = {
    if (ss.==()) {
      false
    } else {
      true
    }
  }
}

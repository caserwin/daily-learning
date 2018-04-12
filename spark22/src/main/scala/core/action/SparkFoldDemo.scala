package core.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/4/12
  * fold是aggregate的简化，将aggregate中的seqOp 和combOp 使用同一个函数op。
  */
object SparkFoldDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("spark demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    // 表示这些数值在同一个分区
    val rdd = sc.parallelize(Array(1, 2, 3, 4, 5), 1)

    // 每个分区的初始计算值为6，这里rdd 虽然只有一个分区，但是实际计算如下：(1+2+3+4+5+6) + (6) = 27.
    // 这里(6) 是另一个初始分区。
    val res: Int = rdd.fold(6)(_ + _)
    println(res)
  }
}

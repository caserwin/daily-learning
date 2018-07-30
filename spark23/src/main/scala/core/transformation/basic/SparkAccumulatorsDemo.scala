package core.transformation.basic

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/4/10
  * RDD 元素求和两种方式：累加器和reduce 算子
  */
object SparkAccumulatorsDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("spark demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val accum = sc.longAccumulator
    val rdd = sc.parallelize(Array(1, 2, 3, 4, 5))

    // 使用累加器
    rdd.foreach(num => accum.add(num))
    println(accum.value)

    // 使用reduce
    val res = rdd.reduce((a, b) => a + b)
    println(res)

    sc.stop()
  }
}

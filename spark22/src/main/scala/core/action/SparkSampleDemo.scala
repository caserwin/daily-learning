package core.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/4/12
  *
  * takeSample(withReplacement, num, [seed])
  * takeSample(withReplacement, fraction, [seed])
  * 这里 withReplacement 表示是否能重复采样，seed是指随机数种子。
  * takeSample 的num 表示采样多少个数。 sample 的fraction 表示采样比例。
  */
object SparkSampleDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("spark demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(1 to 10, 3)

    rdd.takeSample(withReplacement = true, 7, 9).foreach(x => print(x + " "))
    println()
    rdd.sample(withReplacement = false, 0.7, 9).foreach(x => print(x + " "))
    println()
  }
}

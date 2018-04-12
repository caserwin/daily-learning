package core.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/4/12
  * def aggregate[U](zeroValue: U)(seqOp: (U, T) ⇒ U, combOp: (U, U) ⇒ U)(implicit arg0: ClassTag[U]): U
  *
  * seqOp操作会聚合各分区中的元素，然后combOp操作把所有分区的聚合结果再次聚合，两个操作的初始值都是zeroValue。
  * seqOp的操作是遍历分区中的所有元素(T)，第一个T跟zeroValue做操作，结果再作为与第二个T做操作的zeroValue，直到遍历完整个分区。combOp操作是把各分区聚合的结果，再聚合。
  */
object SparkAggregateDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("spark demo").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.parallelize(List(1, 2, 3, 4, 5), 1)
    //    val rdd = List(1, 2, 3, 4, 5)

    // init 表示每个分区的初始值。值得注意的是：在实际计算中，aggregate 最后会多计算一个分区，而且这个多计算的分区的数值就是 init。
    // 其实多计算一个分区也是合理的。因为 aggregate 的combOp操作必须是涉及两个分区的数据，如果只设置一个分区的话，在计算中肯定得补上一个初始分区进行计算。
    val init = 1
    val res = rdd.aggregate(init)(
      (sum, number) => sum + number,
      (sum1, sum2) => sum1 + sum2
      //      (sum1, sum2) => sum1 * sum2
    )
    println(res)
  }
}

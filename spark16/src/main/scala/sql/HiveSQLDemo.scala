package sql

import org.apache.spark.{SparkConf, SparkContext}

/**
  * code 必须在装有Hive的环境下，才能运行
  */
object HiveSQLDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark join Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val hqlContext = new org.apache.spark.sql.hive.HiveContext(sc)
    import hqlContext.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val input1 = sc.parallelize(dataSeq1).toDF("id", "name", "city")

    input1.registerTempTable("cityinfo")
    hqlContext.sql("select * from cityinfo").show()
  }
}

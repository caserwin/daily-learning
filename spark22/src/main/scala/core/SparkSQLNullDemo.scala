package core

import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLNullDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Scala UDAF Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq = Seq(
      (1, "zhangsan", null),
      (1, "lisi", "33"),
      (3, "wangwu", "shanghai")
    )

    val input = sc.parallelize(dataSeq).toDF("id", "name", "city")
    input.printSchema()

    val res = input.select($"id", $"name", $"city").rdd.map {
      r: Row =>
        val id = r.getInt(0)
        val name = r.getString(1)
        val fuck = r.getString(2)
        println("===="+ (fuck ==null))
        (id, name, fuck)
    }

    for (r <- res.collect()) {
      println(r)
    }
  }
}

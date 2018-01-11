package core

import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLNullDemo1 {

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

    val res = input.select($"id", $"name", $"city").na.fill("").rdd.map {
      case Row(id: Integer, name: String, city: String) => (id, name, city)
    }

    for (r <- res.collect()) {
      println(r)
    }
  }
}

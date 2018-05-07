package core.transformation

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/5/7
  */
object SparkMapDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local[*]").getOrCreate()
    val rdd = spark.sparkContext.parallelize(Array("erwin", "caroline"))
    rdd.map(name => "hello: "+ name).foreach(println(_))

    val aRdd = spark.sparkContext.parallelize(1 to 9, 3)
    aRdd.map((a: Int) => {(a, a * 2)}).foreach(println(_))

    spark.stop()
  }
}

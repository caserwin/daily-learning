package core

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/11/9
  */
object SparkCreateMultiRowsDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    val sparkContext = spark.sparkContext

    val array = Array(
      (Array("1", "fruit"), "apple,banana,pear,jwb"),
      (Array("2", "animal"), "pig,cat,dog,tiger")
    )

    val a = sparkContext.parallelize(array)
    val b = a.flatMapValues(_.split(",")).map(ele => {
      val num = ele._1(0)
      val name = ele._1(1)
      val cate = ele._2
      (num, name, cate)
    })

    import spark.implicits._
    b.toDF("num", "name", "cate").show()
  }
}

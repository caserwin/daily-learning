package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType

/**
  * Created by yidxue on 2018/2/27
  */
object SparkSQLFieldsOperateDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val df = sqlContext.read.json("data/cityinfo.json")
    df.show()

    // 增加字段
    df.withColumn("newCol", lit("new")).show()

    // 删除 name 字段
    df.drop("name").show()

    // 修改字段类型，也是通过 withColumn 实现
    df.printSchema()
    val inputDF1 = df.withColumn("Cindex", $"Cindex".cast("string"))
    inputDF1.printSchema()
    val inputDF2 = df.withColumn("Cindex", $"Cindex".cast(IntegerType))
    inputDF2.printSchema()

    sc.stop()
  }
}

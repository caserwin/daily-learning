package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.types.IntegerType

/**
  * Created by yidxue on 2018/1/31
  */
object SparkSQLFieldsOperateDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val df = spark.read.json("data/cityinfo.json")
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

    spark.stop()
  }
}

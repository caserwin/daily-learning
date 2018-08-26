package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/2/27
  */
object SparkSQLFieldsOperateDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", null),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val df = sc.parallelize(dataSeq1).toDF("id", "name", "city")
    df.show()

    // 修改字段类型，也是通过 withColumn 实现
    df.printSchema()
    val inputDF1 = df.withColumn("id", $"id".cast("string"))
    inputDF1.printSchema()
    val inputDF2 = df.withColumn("id", $"id".cast(IntegerType))
    inputDF2.printSchema()

    sc.stop()
  }
}

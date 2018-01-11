package core

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLCaseWhenDemoFail {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Scala UDAF Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

//    val df = Seq("Red", "Green", "Blue").map(Tuple1.apply).toDF("color")

    // Use when/otherwise syntax
//    val df1 = df.withColumn("Green_Ind", when($"color" === "Green", 1).otherwise(0))

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val input1 = sc.parallelize(dataSeq1).toDF("id", "name", "city")


    input1.createOrReplaceTempView("cityinfo")

    val df1 = sqlContext.sql("select ni from cityinfo CASE WHEN name='zhangsan' THEN 1 ELSE 0 END ni")
    df1.show()
  }
}

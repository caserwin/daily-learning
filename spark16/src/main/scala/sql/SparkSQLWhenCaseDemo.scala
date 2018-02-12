package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.when
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLWhenCaseDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val df = Seq("Red", "Green", "Blue").map(Tuple1.apply).toDF("color")

    // demo 1
    val resDF1 = df.withColumn("Green_Ind", when($"color" === "Green", 1).otherwise(0))
    resDF1.show()

    // demo 2 : 如果能join则取resDF2的name，作为join后的DataFrame的name。
    //          如果不能join则取color，作为join后的DataFrame的name。
    val dataSeq = Seq(
      ("Red", "rrr"),
      ("Green", "ggg"),
      ("yellow", "yyy")
    )
    val resDF2 = sc.parallelize(dataSeq).toDF("color", "name")

    resDF1
      .join(resDF2, Seq("color"), "left")
      .withColumn("name", when($"name".isNotNull, $"name").otherwise($"color"))
      .show()
  }
}

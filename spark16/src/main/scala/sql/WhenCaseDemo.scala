package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.{udf, when}
import org.apache.spark.{SparkConf, SparkContext}

object WhenCaseDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val df = Seq("Red", "Green", "Blue").map(Tuple1.apply).toDF("color")
    df.show()

    df.withColumn("Green_Ind", when($"color" === "Green", 1).otherwise(0)).show()
  }
}

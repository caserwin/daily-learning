package core

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.{udf, when}
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLCaseWhenDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Scala UDAF Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val df = Seq("Red", "Green", "Blue").map(Tuple1.apply).toDF("color")

    val isGreen = udf((color: String) => {
      if (color == "Green") 1
      else 0
    })

    df.withColumn("Green_Ind", isGreen($"color")).show()
    df.withColumn("Green_Ind", when($"color" === "Green", 1).otherwise(0)).show()

  }
}

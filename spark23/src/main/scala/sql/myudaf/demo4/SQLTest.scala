package sql.myudaf.demo4

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.max
import org.apache.spark.sql.functions._

object SQLTest {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._
    val df1 = spark.read.json("data/cityinfo.json")

    val constr = new ConcatStrUDAF()
//    val constr = new EventTypeUDAF()

    df1.groupBy("country").agg(constr($"et").as("et"), max($"Cindex").as("Cindex")).show(truncate = false)
    df1.groupBy("country").agg(collect_list($"et").as("et"), max($"Cindex").as("Cindex")).show(truncate = false)

    spark.stop()
  }
}

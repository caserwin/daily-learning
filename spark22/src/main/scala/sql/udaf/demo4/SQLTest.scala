package sql.udaf.demo4

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.max

object SQLTest {

  def manOf[T: Manifest](t: T): Manifest[T] = manifest[T]

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    //    val df = sqlContext.read.json("data/temperatures.json")
    val df1 = spark.read.json("data/cityinfo.json")

    val constr = new EventTypeUDAF()

    //    df1.groupBy("country").count().show(truncate = false)
    //    df1.write.mode(SaveMode.Overwrite).option("delimiter", "\t").format("com.databricks.spark.csv").save("data/home.csv")
    //    df1.groupBy("country").count().rdd.repartition(1).saveAsTextFile("data/out")

    df1.groupBy("country").agg(constr($"Et").as("et"), max($"Cindex").as("Cindex")).show(truncate = false)
  }
}

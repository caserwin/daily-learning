package sql.udaf.demo4

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.max
import org.apache.spark.{SparkConf, SparkContext}


object SQLTest {

  def manOf[T: Manifest](t: T): Manifest[T] = manifest[T]

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Scala UDAF Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._


//    val df = sqlContext.read.json("data/temperatures.json")
    val df1 = sqlContext.read.json("data/cityinfo.json")

    val constr = new EventTypeUDAF()


//    df1.groupBy("country").count().show(truncate = false)
//    df1.write.mode(SaveMode.Overwrite).option("delimiter", "\t").format("com.databricks.spark.csv").save("data/home.csv")
//    df1.groupBy("country").count().rdd.repartition(1).saveAsTextFile("data/out")

    df1.groupBy("country").agg(constr($"Et").as("et"), max($"Cindex").as("Cindex")).show(truncate = false)
  }
}

package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/1/30
  */
object SaprkSQLExplodeDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      ("yidxue", 20, "aa bb"),
      ("erwin", 20, "aa cc"),
      ("yuyi", 20, "bb cc")
    )
    val inputDF = sc.parallelize(dataSeq1).toDF("name", "age", "interest")
    inputDF.show()

    inputDF.explode("interest", "interests") { interest: String => interest.split("\\s+") }
      .select(
        $"name",
        $"age",
        $"interests"
      ).show()
  }
}

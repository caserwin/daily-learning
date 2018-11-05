package sql

import org.apache.spark.sql.{Row, SparkSession}

/**
  * Created by yidxue on 2018/11/5
  */
object SparkSQLFilterDemo2 {

  def filterText(r: Row): Boolean = {
    val text = r.getAs("CONFNAME").toString
    if (text.toLowerCase().endsWith("'s personal room") && text.split("\\s+").length < 5) {
      false
    } else {
      true
    }
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    // dataframe filter
    val dataSeq1 = Seq(
      ("1", "Colin Joyce's Personal Room"),
      ("2", "Jp Shipherd's Personal Room"),
      ("3", "Ian Proudlock's Personal oom")
    )
    val inputDF = spark.sparkContext.parallelize(dataSeq1).toDF("id", "CONFNAME")
    inputDF.filter(r => filterText(r)).show(truncate = false)
    spark.stop()
  }
}

package sql.createDF

import org.apache.spark.sql.SparkSession

/**
  * Created by yidxue on 2018/2/1
  */
object SparkSQLCreateDFByClassDemo {

  case class Person(name: String, age: Long)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val caseClassDF = Seq(Person("Andy", 32), Person("erwin", 23)).toDF()
    caseClassDF.printSchema()
    caseClassDF.show()
  }
}

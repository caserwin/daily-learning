package sql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

object SparkSQLSelectDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()

    val df = spark.read.json("data/cityinfo.json")
    df.show()

    // 只显示3行"name"
    df.select("name", "country").limit(3).show()

    // 将Cindex + 1
    df.select(df("name"), df("Cindex") + 1).show()

    // 使用SQL语句进行操作
    df.createOrReplaceTempView("cityinfo") // 旧版API
    spark.sql("SELECT * FROM cityinfo WHERE Cindex >= 2").show() // 执行 SQL 查询

    import spark.implicits._
    // 使用 $ 进行查询
    df.select($"name", $"country").limit(3).show()

    // 多字段查询
    val cols = "name,country,et".split(",").map(name => col(name))
    df.select("name,country,et".split(",").map(name => col(name)): _*).show()

    spark.stop()
  }
}

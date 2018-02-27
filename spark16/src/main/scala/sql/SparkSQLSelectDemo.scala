package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.col
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLSelectDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val df = sqlContext.read.json("data/cityinfo.json")
    df.show()

    // 只显示3行"name"
    df.select("name", "country").limit(3).show()

    // 将Cindex + 1
    df.select(df("name"), df("Cindex") + 1).show()

    // 使用SQL语句进行操作
    df.registerTempTable("cityinfo") // 旧版API
    sqlContext.sql("SELECT * FROM cityinfo WHERE Cindex >= 2").show() // 执行 SQL 查询

    import sqlContext.implicits._
    // 使用 $ 进行查询
    df.select($"name", $"country").limit(3).show()

    // 多字段查询
    val cols = "name,country,et".split(",").map(name => col(name))
    df.select(cols: _*).show()

  }
}

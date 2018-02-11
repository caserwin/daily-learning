package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLSelectDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val df = sqlContext.read.json("data/cityinfo.json")
    df.show()

    // 只显示3行"name"
    df.select("name").limit(3).show()

    // 将Cindex + 1
    df.select(df("name"), df("Cindex") + 1).show()

    // 过滤语句
    df.where(df("Cindex") > 2).show()

    // 聚合操作
    df.groupBy("country").count().show()

    // 使用SQL语句进行操作
    df.registerTempTable("cityinfo") // 旧版API
    sqlContext.sql("SELECT * FROM cityinfo WHERE Cindex >= 2").show() // 执行 SQL 查询

    // 删除 name 字段
    df.drop("name").show()

    df.show()
    // 工具 Cindex 字段排序： desc降序， asc升序
    df.orderBy(df("Cindex").asc).show(false)
  }
}

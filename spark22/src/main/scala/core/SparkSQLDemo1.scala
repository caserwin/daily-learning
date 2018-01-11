package core

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLDemo1 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Scala UDAF Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val df = sqlContext.read.json("data/cityinfo.json")
    df.show()

    // 只显示"name"
    df.select("name").show()

    // 将Cindex + 1
    df.select(df("name"), df("Cindex") + 1).show()

    // 过滤语句
    df.filter(df("Cindex") > 2).show()

    // 聚合操作
    df.groupBy("country").count().show()

    // 使用SQL语句进行操作
//    df.registerTempTable("cityinfo")     // 旧版API
    df.createOrReplaceTempView("cityinfo")
    val result = sqlContext.sql("SELECT * FROM cityinfo WHERE Cindex >= 2")  // 执行 SQL 查询
    result.show()        // 输出结果

  }
}

package sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLWhenCaseSQLDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark SQL Example").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou", "0"),
      (2, "lisi", "beijing", "1"),
      (3, "wangwu", "shanghai", "1")
    )

    val input1 = sc.parallelize(dataSeq1).toDF("id", "name", "city", "sex")
    input1.registerTempTable("person")
    val sql = "select CASE WHEN sex ='1' THEN '男' WHEN sex = '0' THEN '女' ELSE '其他' END sex, id,name,city from person"

    val df1 = sqlContext.sql(sql)
    df1.show()

    sc.stop()
  }
}

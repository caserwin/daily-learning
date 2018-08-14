package util.phoenix

import java.sql.DriverManager
import org.apache.spark.sql.functions.{concat, lit}
import org.apache.spark.sql.{SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/7/19
  */
object PhoenixRowkeyTest {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("rowkey test demo")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._
    val table1 = "hbase_rowkey_test1"
    val table2 = "hbase_rowkey_test2"
    val zkAddr = "10.29.42.42:2181"
    val dataSeq = Seq(
      ("1", "zhang", "hangzhou", "0"),
      ("2", "lisi", "beijing", "1"),
      ("3", "wangwu", "shanghai", "1"),
      ("4", "zhaoliu", "guangzhou", "1")
    )
    val input = sc.parallelize(dataSeq).toDF("id", "name", "city", "sex")

    createPhoenixTable1(table1, "rowkey,id,name,city,sex", zkAddr)
    createPhoenixTable2(table2, "id,name,city,sex", zkAddr)

    // 写入 phoenix 表1
    input
      .withColumn("ROWKEY", concat(input("id"), lit("_"), input("name")))
      .write.format("org.apache.phoenix.spark")
      .mode(SaveMode.Overwrite)
      .options(Map("table" -> table1, "zkUrl" -> zkAddr)).save()

    // 写入 phoenix 表2
    input
      .write.format("org.apache.phoenix.spark")
      .mode(SaveMode.Overwrite)
      .options(Map("table" -> table2, "zkUrl" -> zkAddr)).save()
  }

  def createPhoenixTable1(sourceTableName: String, sourceViewFields: String, zkAddr: String, buckets: Long = 20, TTL: Long = 31536000): Int = {
    val conn = DriverManager.getConnection(s"jdbc:phoenix:$zkAddr")
    val sourceTableNameDecorator = if (sourceTableName.contains("-")) "\"" + sourceTableName + "\"" else sourceTableName
    val sql = "CREATE TABLE IF NOT EXISTS " + sourceTableNameDecorator + s"(${sourceViewFields.split(",")(0)} VARCHAR PRIMARY KEY," + sourceViewFields.split(",").toList.drop(1).mkString("INFO.", " VARCHAR, INFO.", " VARCHAR)") + " SALT_BUCKETS=" + buckets + "," + " TTL=" + TTL

    println(sql)
    conn.createStatement.executeUpdate(sql)
  }

  def createPhoenixTable2(sourceTableName: String, sourceViewFields: String, zkAddr: String, buckets: Long = 20, TTL: Long = 31536000): Int = {
    val conn = DriverManager.getConnection(s"jdbc:phoenix:$zkAddr")
    val sourceTableNameDecorator = if (sourceTableName.contains("-")) "\"" + sourceTableName + "\"" else sourceTableName
    val sql = "CREATE TABLE IF NOT EXISTS " + sourceTableName + " (id VARCHAR NOT NULL, name VARCHAR NOT NULL, info.city VARCHAR, info.sex VARCHAR CONSTRAINT PK PRIMARY KEY(id, name)) SALT_BUCKETS=" + buckets + ", TTL=" + TTL

    println(sql)
    conn.createStatement.executeUpdate(sql)
  }
}

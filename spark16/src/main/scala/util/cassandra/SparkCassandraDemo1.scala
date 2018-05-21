package util.cassandra

import java.util
import com.datastax.spark.connector._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/5/21
  */
object SparkCassandraDemo1 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Get Data Example")
    conf.set("spark.cassandra.connection.host", "10.29.42.154")
    conf.set("spark.cassandra.auth.username", "cassandra")
    conf.set("spark.cassandra.auth.password", "cassandra")
    val sc = new SparkContext(conf)

    val list = new util.ArrayList[Int]
    list.add(1)
    list.add(2)
    list.add(10)
    val rdd = sc.cassandraTable("dev", "emp").select("empid", "emp_first", "emp_last", "emp_dept").where("empid in ?", list)

    // 读数据
    rdd.map(row => (row.getInt("empid"), row.getString("emp_first"), row.getString("emp_last"), row.getString("emp_dept"))).collect().foreach(println(_))

    // 写数据
    val collection = sc.parallelize(Seq((3, "erwin", "smith", "eng"), (4, "yidxue", "smith", "eng")))
    collection.saveToCassandra("dev", "emp", SomeColumns("empid", "emp_first", "emp_last", "emp_dept"))
  }
}

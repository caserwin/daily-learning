package util.cassandra

import java.util
import com.datastax.spark.connector._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/5/21
  */
object SparkCassandraDemo1 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Get Data Example").setMaster("local[*]")
    conf.set("spark.cassandra.connection.host", "localhost")
    conf.set("spark.cassandra.connection.port", "9042")
    conf.set("spark.cassandra.auth.username", "")
    conf.set("spark.cassandra.auth.password", "")
//    conf.set("spark.cassandra.connection.local_dc", "")
    conf.set("spark.cassandra.query.retry.count", "0")
    conf.set("spark.cassandra.output.ttl", "0")
    conf.set("spark.cassandra.connection.reconnection_delay_ms.min", "1000")
    conf.set("spark.cassandra.connection.reconnection_delay_ms.max", "600000")
    val sc = new SparkContext(conf)

    // 写数据
    val collection = sc.parallelize(Seq((1, "yuyi", "pppp", "gz"),(2, "yuyi", "pppp", "hz"), (3, "erwin", "smith", "bj"), (4, "yidxue", "smith", "sh")))
    collection.saveToCassandra("dev", "emp", SomeColumns("empid", "emp_first", "emp_last", "emp_dept"))

    val list = new util.ArrayList[Int]
    list.add(1)
    list.add(2)
    list.add(10)
    val rdd = sc.cassandraTable("dev", "emp").select("empid", "emp_first", "emp_last", "emp_dept").where("empid in ?", list)

    // 读数据
    rdd.map(row => (row.getInt("empid"), row.getString("emp_first"), row.getString("emp_last"), row.getString("emp_dept"))).collect().foreach(println(_))

    sc.stop()
  }
}

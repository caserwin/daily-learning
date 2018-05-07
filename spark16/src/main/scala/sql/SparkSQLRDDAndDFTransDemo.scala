package sql

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by yidxue on 2018/1/29
  */
object SparkSQLRDDAndDFTransDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Scala UDAF Example").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val rawRDD = sc.parallelize(Array("zhangsan  1", "lisi 2", "yidxue 3"))

    val resRdd = rawRDD.map(
      json => {
        val strs = json.split("\\s+")
        val name = strs(0)
        val id = strs(1)
        Row(name, id)
      }
    )

    val columns = "NAME,ID"
    val schema = StructType(columns.split(",").map(fieldName => StructField(fieldName.trim, StringType, nullable = true)))
    val jmtJoinMethodDF = sqlContext.createDataFrame(resRdd, schema)

    jmtJoinMethodDF.show()
  }
}

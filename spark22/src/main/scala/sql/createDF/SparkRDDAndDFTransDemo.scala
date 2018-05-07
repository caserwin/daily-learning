package sql.createDF

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/**
  * Created by yidxue on 2018/1/29
  */
object SparkRDDAndDFTransDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()

    val dataSeq = Array("zhangsan  1", "lisi 2", "yidxue 3")
    val rawRDD = spark.sparkContext.parallelize(dataSeq)

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
    val jmtJoinMethodDF = spark.createDataFrame(resRdd, schema)

    jmtJoinMethodDF.show()
  }
}

package sql.myudaf.demo3

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/** * Spark SQL UDAS：user defined aggregation function * UDF: 函数的输入是一条具体的数据记录，实现上讲就是普通的scala函数-只不过需要注册 * UDAF：用户自定义的聚合函数，函数本身作用于数据集合，能够在具体操作的基础上进行自定义操作 */
object SparkSQLUDF {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()

    val bigData = Array("Spark","Hadoop","Flink","Spark","Hadoop","Flink","Spark","Hadoop","Flink","Spark","Hadoop","Flink")
    val bigDataRDD = spark.sparkContext.parallelize(bigData)

    val bigDataRowRDD = bigDataRDD.map(line => Row(line))
    val structType = StructType(Array(StructField("name",StringType,true)))
    val bigDataDF = spark.createDataFrame(bigDataRowRDD, structType)

    bigDataDF.createOrReplaceTempView("bigDataTable")

    /* * 通过HiveContext注册UDF，在scala2.10.x版本UDF函数最多可以接受22个输入参数 */
    spark.udf.register("computeLength",(input:String) => input.length)
    spark.sql("select name,computeLength(name) as length from bigDataTable").show

    //while(true){}

    spark.udf.register("wordCount",new MyUDAF)
    spark.sql("select name,wordCount(name) as count,computeLength(name) as length from bigDataTable group by name ").show()

    spark.stop()
  }
}

package sql.udaf.demo3

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/** * Spark SQL UDAS：user defined aggregation function * UDF: 函数的输入是一条具体的数据记录，实现上讲就是普通的scala函数-只不过需要注册 * UDAF：用户自定义的聚合函数，函数本身作用于数据集合，能够在具体操作的基础上进行自定义操作 */
object SparkSQLUDF {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[*]").setAppName("SparkSQLWindowFunctionOps")
    val sc = new SparkContext(conf)

    val sqlContext = new SQLContext(sc)

    val bigData = Array("Spark","Hadoop","Flink","Spark","Hadoop","Flink","Spark","Hadoop","Flink","Spark","Hadoop","Flink")
    val bigDataRDD = sc.parallelize(bigData)

    val bigDataRowRDD = bigDataRDD.map(line => Row(line))
    val structType = StructType(Array(StructField("name",StringType,true)))
    val bigDataDF = sqlContext.createDataFrame(bigDataRowRDD, structType)

    bigDataDF.registerTempTable("bigDataTable")

    /* * 通过HiveContext注册UDF，在scala2.10.x版本UDF函数最多可以接受22个输入参数 */
    sqlContext.udf.register("computeLength",(input:String) => input.length)
    sqlContext.sql("select name,computeLength(name) as length from bigDataTable").show

    //while(true){}

    sqlContext.udf.register("wordCount",new MyUDAF)
    sqlContext.sql("select name,wordCount(name) as count,computeLength(name) as length from bigDataTable group by name ").show
  }
}
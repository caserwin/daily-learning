package phoenix

import org.apache.phoenix.spark._
import org.apache.spark.sql.{DataFrame, SQLContext, SaveMode}

/**
  * User: Erwin
  * Date: 17/12/27 下午2:47
  * Description: 
  */
object PhoenixSparkService {

  // base on spark: http://phoenix.apache.org/phoenix_spark.html
  def savePhoenix(dataFrame: DataFrame, tableName: String, zkAddr: String): Unit = {
    dataFrame.write.format("org.apache.phoenix.spark").mode(SaveMode.Overwrite).options(Map("table" -> tableName, "zkUrl" -> zkAddr)).save()
  }

  // base on spark: http://phoenix.apache.org/phoenix_spark.html
  def selectPhoenixBySpark(sqlContext: SQLContext, zkAddr: String, tableName: String, columns: Seq[String], predicate: Option[String] = None): DataFrame = {
    sqlContext.phoenixTableAsDataFrame(table = tableName, columns = columns, predicate = predicate, zkUrl = Some(zkAddr))
  }

  // create table


  //
}

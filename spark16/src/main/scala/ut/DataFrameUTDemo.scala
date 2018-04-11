package ut

import org.apache.spark.sql.{DataFrame, SQLContext}

/**
  * Created by yidxue on 2018/4/11
  */
object DataFrameUTDemo {

  def transformDF(sqlContext: SQLContext, inDF: DataFrame): DataFrame = {
    import sqlContext.implicits._

    val outDF = inDF
      .withColumn("REFNUM1", $"REFNUM1".cast("int") + 1)
      .withColumn("REFNUM2", $"REFNUM2".cast("int") + 1)
      .withColumn("REFNUM3", $"REFNUM3".cast("int") + 1)
      .withColumn("REFNUM4", $"REFNUM4".cast("int") + 1)
      .withColumn("REFNUM5", $"REFNUM5".cast("int") + 1)
      .withColumn("REFNUM6", $"REFNUM6".cast("int") + 1)
    outDF
  }
}

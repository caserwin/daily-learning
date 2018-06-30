package util.dataclean.strategy

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{avg, col}

/**
  * Created by yidxue on 2018/6/29
  */
object ValueStrategy {

  def getStringValueStrategy(df: DataFrame, field: String): String = {
    val fieldDF = df.select(field).groupBy(field).count()
    val category = fieldDF.orderBy(col("count").desc).head().getString(0)
    category
  }

  def getIntValueStrategy(df: DataFrame, field: String): Int = {
    // 平均值策略
    val avg_value = df.select(avg(field)).head().getDouble(0)
    avg_value.toInt
  }

  def getFloatValueStrategy(df: DataFrame, field: String): Float = {
    // 平均值策略
    val avg_value = df.select(avg(field)).head().getDouble(0)
    avg_value.toFloat
  }
}

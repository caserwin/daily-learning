package ignite

import org.apache.ignite.spark.IgniteDataFrameSettings._
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

/**
  * Created by yidxue on a2019/6/3
  */
object IgniteService {

  val CONFIG: String = "example-ignite-bts.xml"

  def save(df: DataFrame, tableName: String, primaryKey: String): Unit = {
    df.write
      .format(FORMAT_IGNITE)
      .mode(SaveMode.Append)
      .option(OPTION_CONFIG_FILE, CONFIG)
      .option(OPTION_TABLE, tableName)
      .option(OPTION_STREAMER_ALLOW_OVERWRITE, "true")
      .option(OPTION_CREATE_TABLE_PRIMARY_KEY_FIELDS, primaryKey)
      .option(OPTION_CREATE_TABLE_PARAMETERS, "template=partitioned,backups=2")
      .save()
  }

  def load(tableName: String, key: String, value: String)(implicit spark: SparkSession): DataFrame = {
    spark.read
      .format(FORMAT_IGNITE)
      .option(OPTION_TABLE, tableName)
      .option(OPTION_CONFIG_FILE, CONFIG)
      .load()
      .filter(col(key) === value)
  }
}

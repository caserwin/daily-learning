package ignite

import org.apache.spark.sql.SparkSession
import org.apache.ignite.spark.IgniteDataFrameSettings._
import org.apache.spark.storage.StorageLevel

/**
  * Created by yidxue on 2018/7/30
  */
object SparkSaveIgniteDemo {

  def main(args: Array[String]): Unit = {
    val config = "example-ignite.xml"

    val spark = SparkSession.builder.appName("SQL Application").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val inputDF1 = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city")

    inputDF1.persist(StorageLevel.MEMORY_AND_DISK_SER)
    inputDF1.write.format(FORMAT_IGNITE)
      .option(OPTION_CONFIG_FILE, config)
      .option(OPTION_TABLE, "person")
      .option(OPTION_CREATE_TABLE_PRIMARY_KEY_FIELDS, "id")
      .option(OPTION_CREATE_TABLE_PARAMETERS, "template=replicated,backups=1")
      .save()

    spark.stop()
  }
}

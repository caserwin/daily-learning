package hive

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by yidxue on 2018/1/28
  */
object HiveUtil {

  /**
    * create hive table with date partition
    */
  def createHiveTable(tableName: String, fields: Seq[String])(implicit spark: SparkSession): Unit = {
    val cols = fields.map(field => s"$field  string  comment  '$field'").mkString(",")

    val createTableHQL =
      s"""
        create table if not exists $tableName ($cols)
            partitioned by (l_date string)
            row format delimited
            fields terminated by '\t'
            collection items terminated by ','
        """

    println(createTableHQL)

    spark.sql(createTableHQL)
  }

  /**
    * insert dataframe to hive table
    */
  def insertHiveTable(targetTableName: String, date: String, df: DataFrame, fields: Seq[String])(implicit spark: SparkSession): Unit = {
    df.createOrReplaceTempView("tmpTable")
    val cols = fields.mkString(",")
    val insertTableHQL = s"insert overwrite TABLE $targetTableName PARTITION (l_date='$date') select $cols from tmpTable"

    println(insertTableHQL)

    spark.sql(insertTableHQL)
  }
}

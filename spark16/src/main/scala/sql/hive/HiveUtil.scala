package sql.hive

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.hive.HiveContext

/**
  * Created by yidxue on 2018/1/28
  */
object HiveUtil {

  /**
    * create hive table with date partition
    */
  def createHiveTable(hqlContext: HiveContext, tableName: String, fields: Seq[String]): Unit = {
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
    hqlContext.sql(createTableHQL)
  }

  /**
    * insert dataframe to hive table
    */
  def insertHiveTable(hqlContext: HiveContext, targetTableName: String, date: String, df: DataFrame, fields: Seq[String]): Unit = {
    df.registerTempTable("tmpTable")
    val cols = fields.mkString(",")
    val insertTableHQL = s"insert overwrite TABLE $targetTableName PARTITION (l_date='$date') select $cols from tmpTable"

    println(insertTableHQL)
    hqlContext.sql(insertTableHQL)
  }

  /**
    * insert hive table by dynamic partition
    */
  def insertByDynamic(hqlContext: HiveContext, targetTableName: String, df: DataFrame, fields: Seq[String]): Unit = {
    df.registerTempTable("tmpTable")
    val cols = fields.mkString(",")
    val insertTableHQL = s"insert overwrite TABLE $targetTableName PARTITION (l_date) select $cols from tmpTable"

    println(insertTableHQL)
    hqlContext.sql(insertTableHQL)
  }
}

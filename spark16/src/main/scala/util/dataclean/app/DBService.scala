package util.dataclean.app

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.hive.HiveContext

/**
  * Created by yidxue on 2018/6/29
  */
object DBService {

  def getData(hqlContext: HiveContext, tableName: String): DataFrame = {
    val sql = s"select * from $tableName"
    hqlContext.sql(sql)
  }
}

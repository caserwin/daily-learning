package util.oracle

import java.util
import java.util.Properties

import org.apache.spark.sql.{DataFrame, SQLContext}

import scala.collection.JavaConversions._

/**
  * Created by larwu on 12/20/17.
  */
object OracleUtils {

  def getConnectionProperties(userName: String, passWord: String): Properties = {
    val connectionProperties = new java.util.Properties()
    connectionProperties.setProperty("user", userName)
    connectionProperties.setProperty("password", passWord)
    connectionProperties.setProperty("driver", "oracle.jdbc.driver.OracleDriver")
    connectionProperties
  }

  def getJDBCURL(jdbcHostname: String, jdbcPort: String, jdbcConnType: (String, String)): String = {
    val jdbcURL = jdbcConnType._1 match {
      case "SERVICENAME" => s"jdbc:oracle:thin:@//$jdbcHostname:$jdbcPort/${jdbcConnType._2}"
      case "SID" => s"jdbc:oracle:thin:@$jdbcHostname:$jdbcPort:${jdbcConnType._2}"
    }
    jdbcURL
  }

  def getOracleDFByDate(sqlContext: SQLContext, dbConf: util.HashMap[String, String], startDate: String, endDate: String, tableName: String, tableFields: String, targetField: String): DataFrame = {
    val jdbcQuery = s"(select $tableFields " +
      s"from $tableName where $targetField>=TO_DATE('$startDate','YYYY-MM-DD HH24:MI:SS') " +
      s"AND $targetField<TO_DATE('$endDate','YYYY-MM-DD HH24:MI:SS')) tmp"

    val connType = if (dbConf.get("SERVICENAME") != null) ("SERVICENAME", dbConf.get("SERVICENAME")) else ("SID", dbConf.get("SID"))
    val df = sqlContext.read.jdbc(getJDBCURL(dbConf.get("HOSTNAME"), dbConf.get("PORT"), connType), jdbcQuery, getConnectionProperties(dbConf.get("USERANME"), dbConf.get("PASSWORD")))
    df
  }

  def selectOracleSimpleDF(sqlContext: SQLContext, dbConf: util.HashMap[String, String], sql: String, predicates: Array[String] = Array()): DataFrame = {
    println(sql)
    val connType = if (dbConf.get("SERVICENAME") != null) ("SERVICENAME", dbConf.get("SERVICENAME")) else ("SID", dbConf.get("SID"))
    if (predicates.length > 0) {
      sqlContext.read.jdbc(getJDBCURL(dbConf.get("HOSTNAME"), dbConf.get("PORT"), connType), sql, predicates, getConnectionProperties(dbConf.get("USERANME"), dbConf.get("PASSWORD")))
    } else {
      sqlContext.read.jdbc(getJDBCURL(dbConf.get("HOSTNAME"), dbConf.get("PORT"), connType), sql, getConnectionProperties(dbConf.get("USERANME"), dbConf.get("PASSWORD")))
    }
  }

  def selectOracleDF(sqlContext: SQLContext, tableName: String, tableFields: String, dbConf: util.HashMap[String, String], whereConf: util.HashMap[String, String] = new util.HashMap[String, String](), predicates: Array[String] = Array()): DataFrame = {
    var jdbcQuery = s"(select $tableFields from $tableName "
    for (condition <- whereConf) {
      if (!jdbcQuery.contains(" where ")) {
        jdbcQuery = jdbcQuery.concat("where ")
      }
      jdbcQuery = jdbcQuery.concat(s"${condition._1} ${condition._2} ")
    }
    jdbcQuery = jdbcQuery.concat(") tmp")

    println(jdbcQuery)

    val connType = if (dbConf.get("SERVICENAME") != null) ("SERVICENAME", dbConf.get("SERVICENAME")) else ("SID", dbConf.get("SID"))
    if (predicates.length > 0) {
      sqlContext.read.jdbc(getJDBCURL(dbConf.get("HOSTNAME"), dbConf.get("PORT"), connType), jdbcQuery, predicates, getConnectionProperties(dbConf.get("USERANME"), dbConf.get("PASSWORD")))
    } else {
      sqlContext.read.jdbc(getJDBCURL(dbConf.get("HOSTNAME"), dbConf.get("PORT"), connType), jdbcQuery, getConnectionProperties(dbConf.get("USERANME"), dbConf.get("PASSWORD")))
    }
  }
}
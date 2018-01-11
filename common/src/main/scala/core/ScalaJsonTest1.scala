package core

import net.minidev.json.JSONObject
import net.minidev.json.JSONValue


/**
  * User: Erwin
  * Date: 17/12/5 上午9:29
  * Description: 
  */
object ScalaJsonTest1 {

  def getJMTTemplate(pipeLine: String, sendTime: String, jobType: String): String = {
    val message =
      s"""
      {
        "pipeLine": $pipeLine,
        "phase": "Data Process",
        "component": "Apache Spark",
        "sendTime": $sendTime,
        "data": {
          "name": "amount of data",
          "category": "Json",
          "size": [
            {
              "type": $jobType,
              "source": {
                "count": "16316"
              },
              "target": {
                "max_count": "30000",
                "min_count": "10000",
              }
            }
          ]
        }
      }
    """
    message
  }

  def main(args: Array[String]): Unit = {
    val jsonObj = JSONValue.parse(getJMTTemplate("4", "2017-12-01", "workend")).asInstanceOf[JSONObject]

    println(jsonObj.toJSONString)

  }
}

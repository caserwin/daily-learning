package kafka.message

import java.text.SimpleDateFormat
import java.util.Date
import net.minidev.json.{JSONObject, JSONValue}

/**
  * User: Erwin
  * Date: 17/12/4 下午8:14
  * Description: 
  */
object KafkaTemplate {

  def getJMTAndJMSTemplate(pipeLine: String, name: String, date: String, source: Long): String = {
    val sendTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
    val message =
      s"""
      {
        "pipeLine": $pipeLine,
        "phase": "Data Process",
        "component": "Apache Spark",
        "sendTime": $sendTime,
        "data": {
          "name": $name,
          "createTime": $date 00:00:00,
          "unit": "Records",
          "category": "Integer",
          "size": $source
        }
      }
    """
    JSONValue.parse(message).asInstanceOf[JSONObject].toJSONString
  }
}

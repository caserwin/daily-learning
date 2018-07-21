package core.collections

import java.util.Calendar
import scala.collection.JavaConversions._

/**
  * Created by yidxue on 2018/7/16
  */
object ScalaUseJavaCollectionsDemo {

  def main(args: Array[String]): Unit = {
    JavaHashMapDemo()
  }

  def JavaHashMapDemo(): Unit = {
    val map = new java.util.LinkedHashMap[String, Int]
    map.put("Monday", Calendar.MONDAY)
    map.put("Tuesday", Calendar.TUESDAY)
    map.put("Thursday", Calendar.THURSDAY)
    map.put("Wednesday", Calendar.WEDNESDAY)
    map.put("Friday", Calendar.FRIDAY)
    map.put("Saturday", Calendar.SATURDAY)
    map.put("Sunday", Calendar.SUNDAY)
    map.foreach(x => print(x + "\t"))
    println()
  }
}

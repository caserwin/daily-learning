package core.collections.javaconversions

import java.util
import java.util.Calendar
import scala.collection.JavaConversions._

/**
  * Created by yidxue on 2018/7/16
  */
object ScalaUseJavaCollectionsDemo {

  def main(args: Array[String]): Unit = {

    val brandDistinctMap = new java.util.HashMap[String, java.lang.Double]
    brandDistinctMap.put("a", 1d)
    // 这里必须是写成  java.lang.Double 这种形式
    val vm: util.HashMap[String, java.lang.Double] = CollectionDemo.setHashMap(brandDistinctMap)
    for (en <- vm.entrySet()) {
      println(en.getKey + "->" + en.getValue)
    }

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

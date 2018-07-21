package core.collections.map

import scala.collection.mutable

/**
  * User: Erwin
  * Date: 17/12/28 上午9:27
  * Description: 
  */
object ScalaMutableMapDemo {

  def main(args: Array[String]): Unit = {
    //    var map = new mutable.HashMap[String, String]()
    var map = new mutable.LinkedHashMap[String, String]()

    // 增加元素
    map += ("ML" -> "hadoop")
    map += ("DL" -> "spark", "DL1" -> "spark1")
    println("==================")
    println(map.mkString(","))

    // 查询元素
    println("==================")
    println(map.getOrElse("DL", "null"))

    // 是否包含
    println("==================")
    println(map.contains("ML"))

    // 引用传递示例
    addNewItem(map)

    // 遍历
    println("==================")
    map.foreach(x => {
      println(x._1 + "\t" + x._2)
    })

    // 得到所有keys
    println("所有的key: "+map.keys)

    // 得到所有values
    println("所有的value: "+map.values)

  }

  def addNewItem(map: mutable.LinkedHashMap[String, String]): Unit = {
    map += ("CV" -> "flink")
  }
}
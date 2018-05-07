package core.map

import scala.collection.mutable

/**
  * User: Erwin
  * Date: 17/12/28 上午9:27
  * Description: 
  */
object ScalaMutableMapDemo {

  def main(args: Array[String]): Unit = {
    var map1 = new mutable.HashMap[String, String]()

    map1 += ("ML" -> "hadoop")
    map1 += ("DL" -> "spark")

    println(map1.mkString(","))
    println(map1.getOrElse("DL", "null"))
  }
}

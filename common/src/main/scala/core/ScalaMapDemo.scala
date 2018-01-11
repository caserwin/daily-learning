package core

import java.util
import java.util.concurrent.ConcurrentHashMap

object ScalaMapDemo {

  def main(args: Array[String]): Unit = {
    val map: util.Map[String, Int] = new ConcurrentHashMap[String, Int]()
    map.put("AA", 1)
    println(map.get("AA"))

    var A = Map("red" -> "#FF0000", "azure" -> "#F0FFFF")
    //    A += ("sss" -> "1")
    //    A += ("sss" -> "22")
    A = modifyMap(A)
    println(A)
    println(A("red"))
    println(A.get("red"))
  }


  def modifyMap(map: Map[String, String]): Map[String, String] = {
    var A = map
    A += ("ss111s" -> "22")
    A
  }
}

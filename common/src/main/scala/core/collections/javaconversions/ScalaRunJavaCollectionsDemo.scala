package core.collections.javaconversions

import scala.collection.JavaConversions._

/**
  * User: Erwin
  * Date: 17/12/25 下午3:30
  * Description: 
  */
object ScalaRunJavaCollectionsDemo {

  def main(args: Array[String]): Unit = {
    val hashmap: java.util.Map[String, String] = new java.util.HashMap[String, String]
    hashmap.put("aa1", "sss")
    hashmap.put("bb1", "ccc")
    for (row <- hashmap) {
      println(row._1 + "\t" + row._2)
    }

    val ls: java.util.ArrayList[String] = CollectionDemo.getArrayList
    val hm: java.util.HashMap[String, String] = CollectionDemo.getHashMap

    println("===:" + ls.stream().findFirst().get())
    println("===:" + hm.get("k1"))
  }
}

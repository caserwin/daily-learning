package core

import java.util
import scala.collection.JavaConversions._

/**
  * User: Erwin
  * Date: 17/12/25 下午3:30
  * Description: 
  */
object ScalaRunJavaCollectionsDemo {

  def main(args: Array[String]): Unit = {
    val hashmap: util.Map[String, String] = new util.HashMap[String, String]
    hashmap.put("aa1", "sss")
    hashmap.put("bb1", "ccc")
    for (row <- hashmap) {
      println(row._1 + "\t" + row._2)
    }
  }
}

package core

import scala.collection.mutable

/**
  * User: Erwin
  * Date: 17/12/28 上午9:27
  * Description: 
  */
object ScalaMutableMapDemo {

  def main(args: Array[String]): Unit = {
    val hashMap = mutable.HashMap
    hashMap.->("key1", "v1")



    println(hashMap)
  }
}

package core.collections

import scala.collection.immutable

/**
  * Created by yidxue on 2018/5/9
  * scala 不可变集合
  * scala.collection.immutable.List[A]
  */
object ScalaImmutableListDemo {

  def main(args: Array[String]): Unit = {

    var test: List[(String, String, String)] = immutable.List[(String, String, String)](("a1", "a2", "a3"), ("b1", "b2", "b3"))

    println(test.map(x => x._1 + ":" + x._2 + ":" + x._3).mkString(","))
  }
}

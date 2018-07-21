package core.collections.set

import scala.collection.immutable.HashSet


/**
  * Created by yidxue on 2018/7/16
  */
object ScalaSetDemo {
  def main(args: Array[String]): Unit = {
    val set1 = new HashSet[Int]()
    // 将元素和set1合并生成一个新的set，原有set不变
    val set2 = set1 + 4
    // 一次添加多个元素
    val set3 = set2 ++ Set(5, 6, 7)
    println(set3)
    println(set3.getClass)

    // 集合求交集
    val b = Set(2, 4)
    val a = Set(1, 2, 3)
    val c = b intersect a
    println("交集：" + c)

    // 求差集
    println("差集：" + (b diff a))
  }
}
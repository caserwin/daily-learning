package core.collections.set

import scala.collection.mutable

/**
  * Created by yidxue on 2018/7/18
  */
object ScalaMutableSetDemo {

  def main(args: Array[String]): Unit = {
    val hashSet = mutable.HashSet(1, 3, 3, 2)
    println(hashSet)

    // 增加一个值
    hashSet.add(4)
    hashSet += 5
    println(hashSet)

    hashSet.remove(1)
    println(hashSet)

    // 添加一个集合的元素
    hashSet ++= Set(10, 13, 15)
    println(hashSet)

    // 删除指定值的元素
    hashSet -= 2
    println(hashSet)
    println("获取Set中第一个元素：" + hashSet.head)
    println("判断是否包含2：" + hashSet.contains(2))

    // 遍历输出
    hashSet.foreach(x => {
      print(x + "\t")
    })
    println()
    // 求交集
    val b = mutable.HashSet(2, 4)
    val a = mutable.HashSet(1, 2, 3)
    println("交集："+ (b intersect a))

    // 求差集
    println("差集：" + (b diff a))
  }
}
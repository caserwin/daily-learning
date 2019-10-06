package core.collections.listAndSeq

import scala.collection.mutable

/**
  * Created by yidxue on 2018/1/31
  */
object ScalaMutableListDemo {

  def main(args: Array[String]): Unit = {
    // 定义一个可变列表, 初始化数值
    val mlsInt = mutable.ArrayBuffer[Int](2)
    mlsInt += 5
    println(mlsInt)

    // 定义一个可变列表, 初始化数值，可存放任何类型
    val mls = mutable.ArrayBuffer(1, 2, 3, 4, "str")

    // 增加一个元素
    mls += 5
    mls.append("444")
    println(mls)

    // 一次增加多个元素
    mls.++=(mlsInt)
    println(mls)

    // 初始化与一个空列表
    val buf = scala.collection.mutable.ArrayBuffer.empty[String]

    // 增加一个元素
    buf += "a"
    buf += "b"
    buf += "c"
    println(buf)

    // 根据index 修改
    buf(1) = "d"
    println(buf)

    // 根据index 查询
    println("根据下标查询第二个元素：" + buf(1))

    // 删除index对应的元素，refer
    println("根据下标删除第一个元素：" + buf.remove(0))

    // 删除指定值的元素
    buf -= "c"
    println(buf)
  }
}

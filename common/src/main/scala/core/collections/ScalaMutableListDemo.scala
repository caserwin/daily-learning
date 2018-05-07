package util.collections

import scala.collection.mutable

/**
  * Created by yidxue on 2018/1/31
  */
object ScalaMutableListDemo {

  def main(args: Array[String]): Unit = {
    // 定义一个可变列表, 只能存放Int类型
    val mlsInt = mutable.MutableList[Int](1, 2, 3, 4)
    mlsInt += 5
    println(mlsInt)

    // 定义一个可变列表, 可以存放任何类型
    val mls = mutable.MutableList(1, 2, 3, 4, "str")
    mls += 5
    println(mls)

    // 可变集合合并
    println(mls ++ mlsInt)

    val buf = scala.collection.mutable.ArrayBuffer.empty[String]
    buf+= "aa"
    buf+= "bb"
    buf+= "cc"

    println(buf.mkString(", "))
  }
}

package core.collections

/**
  * Created by yidxue on 2018/4/13
  */
object ScalaDropAndTakeDemo {

  def main(args: Array[String]): Unit = {
    val a = "1,2,3,4,5"
    // 删除可迭代列表中的前 n 项
    val b = a.split(",").drop(1)
    b.foreach(println(_))

    // 获取前n项
    println(a.split(",").length)
    println(a.split(",").take(4).mkString(","))
  }
}

package util.collections

/**
  * Created by yidxue on 2018/4/13
  */
object ScalaDropDemo {

  def main(args: Array[String]): Unit = {
    val a = "1,2,3,4,5"
    // 删除可迭代列表中的钱 n 项
    val b = a.split(",").drop(1)
    b.foreach(println(_))
  }
}

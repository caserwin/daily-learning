package core

/**
  * Created by yidxue on 2018/7/16
  */
object ScalaCommonDemo {

  def main(args: Array[String]): Unit = {

    // List sort
    val obj = List(("2017-10-09 11:11:11", 1, 4), ("2017-10-09 10:11:11", 3, 7), ("2017-11", 2, 1))
    obj.sortBy(_._1).foreach(println(_))
    obj.sortBy(_._2).foreach(println(_))

    // zip 用法：把两个可迭代对象合并
    val tuple = "Hello".zip("Worl")
    println(tuple)

    val ls1 = Seq("1", "2", "3")
    val ls2 = Seq("a", "b")
    ls1.zip(ls2).foreach(println(_))

    // 判断一个对象的数据类型
    def manOf[T: Manifest](t: T): Manifest[T] = manifest[T]
    println("ls1 的数据类型为：" + manOf(ls1))

  }
}

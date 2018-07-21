package core.collections

object ScalaTupleDemo {

  def main(args: Array[String]): Unit = {

    // 在元组中定义了三个元素，对应的类型分别为[Int, Double, java.lang.String]。
    val t1 = (1, 3.14, "Fred")

    // 也可以如下定义
    val t2 = Tuple3(1, 3.14, "Fred")
    println(t2._1 + " " + t2._2 + " " + t2._3)

    println("======================")
    // 传参打印
    printTuple3(t2)

    println("======================")
    // 遍历
    t2.productIterator.foreach(i => println("Value = " + i))
  }

  def printTuple3(tuple3: (Int, Double, String)): Unit = {
    println(tuple3._1 + " " + tuple3._2 + " " + tuple3._3)
  }
}

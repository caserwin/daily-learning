package core

/**
  * User: Erwin
  * Date: 17/12/31 下午12:20
  * Description: 
  */
object ScalaSeqDemo {

  /**
    *  1 Seq这个数据结构非常奇怪，删除元素默认从1开始，获取元素默认从0开始。
    *
    */

  def main(args: Array[String]): Unit = {

    val fields = Seq("ROWKEY", "NAME", "CITY", "DISCOUNT")
    println(fields)
    // 输出第一个元素
    println(fields(0))
    println(fields.head)
    // 删除第一个元素
    println(fields.drop(1))


    fields.foreach(str => println(str))

  }
}

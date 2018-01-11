package core

import scala.collection.mutable.ListBuffer

/**
  * User: Erwin
  * Date: 17/11/13 下午2:36
  * Description: 
  */
object Mytest2 {

  def main(args: Array[String]): Unit = {

//    val a = (1, 2, 3, 4)
//    val (b1, b2, b3, b4) = (a._1, a._2, a._3, a._4)
//
//    println(b1)
//
//    println(Set("a", "b").contains("a"))
//
//    val x = List(1)
//    val y = 0 +: x
//    val y1 = x :+ 2
//
//    println(y)

    val ls = List(1, 2, 3, 4)
    val ls1 = List(3, 4, 5, 6)

    val ls2 = ls :+ 5
    ls1.foreach(println(_))

    println("=====================")
    val ls3 = ls ++ ls1
    ls3.foreach(println(_))


    val a = new ListBuffer[Int]()
    a += 5
    a += 6
    a.foreach(println(_))

    println("=====================")
    val b = new ListBuffer[Int]()
    b ++= a
    b.foreach(println(_))
  }
}

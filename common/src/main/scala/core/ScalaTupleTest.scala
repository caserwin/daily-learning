package core

object ScalaTupleTest {

  def main(args: Array[String]): Unit = {
    def getUserInfo = ("Al", 42, 200.0)
    val(name, age, weight) = getUserInfo

    println(name)
    println(age)
    println(weight)

    println()
    val things = ("a", 1, 3.5)
    println(things._1)
    println(things._2)
    println(things._3)


    println("==========================")
    val str = "1,2,3"
    val numList = str.split(",").map(Integer.parseInt).toSet


    val b = Set(2,4)
    val a = Set(1,2,3)

    val c = b intersect a

    println(c.nonEmpty)

    println("==========================")

    var t = (1, 3.14, "Fred")
    val (t1, t2, t3) = (2, 3.14, "Fred")

    println(t3)

    val aaa=String.valueOf(null)
    println(aaa)
  }
}

package core

object MyTest {

  def addInt(a: Int, b: Int): Int = {
    var sum: Int = 0
    sum = a + b
    sum
  }

  // 定义一个普通函数
  def foo(s: String): Unit = {
    println(s)
  }

  // 定义一个高阶函数
  def hf(f: String => Unit): Unit = {
    f("higher")
  }

  def main(args: Array[String]): Unit = {
    //    test1()
    //    test2()

    val a =1
    val b =2
    if(a==1||b==3){
      println("======")
    }

    println(Set(0).contains(0))

    var c ="ccc"

    c = "a" match {
      case et if "a".equals(et) => "ca" // Just take one refnum6, do not care whatever, default is max(refnum6).
      case _ => c
    }

    println(c)

    println("16".toInt)

    val sourceViewFields = "ROWKEY,CONFID,GID,UID_"
    println(sourceViewFields.split(",").toList.drop(1).mkString("-"))
    println(sourceViewFields.split(",").toList.drop(1).mkString("(", "-", ")"))
    println(sourceViewFields.split(",").toList.drop(1).mkString)
  }

  def test1(): Unit = {
    // 定义一个List集合
    val num: List[Int] = List(1, 2, 3)
    // _ 指代一个集合中的每个元素。例如在一个Array a中筛出偶数，并乘以2 .
    val b: List[Int] = num.filter(_ % 2 == 0).map(2 * _)
    println(b)
  }

  def test2(): Unit = {
    val ita = Iterator(2, 5, 3, 4, 6)
    val itb = Iterator(2, 5, 3, 4, 6)
    //    while (ita.hasNext){
    //      println(ita.next())
    //    }
    println("最大元素是：" + ita.max)
    println("最小元素是：" + itb.min)
  }

  def test3(): Unit ={
    // 定义一个普通函数
    def foo(s: String): Unit = { println(s) }
    // 定义一个高阶函数hf，输入参数是一个函数f，hf的函数体是调用函数f("higher")
    def hf(f: String => Unit): Unit = { f("higher") }
    hf(foo)
  }

}

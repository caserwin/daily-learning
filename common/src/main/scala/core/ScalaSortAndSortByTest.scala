package core

/**
  * User: Erwin
  * Date: 17/11/17 下午4:20
  * Description: 
  */
object ScalaSortAndSortByTest {

  def main(args: Array[String]): Unit = {
    val obj = List(("2017-10-09 11:11:11", 1, 4), ("2017-10-09 10:11:11", 3, 7), ("2017-11", 2, 1))
    obj.sortBy(_._1).foreach(println(_))
    println()
    obj.sortBy(_._2).foreach(println(_))
    // sort 不能用额外的def
    // obj.sortBy(multiplication(_._2)).foreach(println(_))
    println("2017-10-09 11:11:11".split("\\s+")(1))
  }


  def multiplication(num: Int): Int = {
    num * (-1)
  }
}

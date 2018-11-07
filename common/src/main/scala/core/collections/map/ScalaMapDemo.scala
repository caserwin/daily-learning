package core.collections.map

object ScalaMapDemo {

  val mapping: String =
    """|4801,DNAME
       |10102,vvwd
       |9902,mmwd
       |8301,ktwd
       |10202,rvwd""".stripMargin

  def main(args: Array[String]): Unit = {

    // create map by two list
    val listA = List("a", "f", "d")
    val listB = List(7, 5, 4)
    import scala.collection.breakOut
    val m = (listA zip listB) (breakOut): Map[String, Int]
    println(m)

    // 初始化
    var A = Map("Computer" -> 3000, "Iphone" -> 2000, "Cup" -> 10)
    // 增加元素
    A = modifyMap(A)
    println(A)

    // 查询
    println("不推荐，因为不存在对应的key就会报异常：" + A("Computer"))
    println("感觉一般，和Java中的类似：" + A.get("Computer"))
    println("比较推荐，和Python中的用法类似：" + A.getOrElse("computer", "NULL"))

    // 把Collection 转成Map
    val map1 = mapping.split("\\n").map(x => x.split(",")).map(arr => arr(0) -> arr(1)).toMap
    map1.foreach(println(_))

    // 拼装
    println(A.map(x => x._1 + "=" + x._2).mkString(" and "))

    // 得到所有keys
    println("所有的key: " + map1.keys)

    // 得到所有values
    println("所有的value: " + map1.values)
  }


  def modifyMap(map: Map[String, Int]): Map[String, Int] = {
    var A = map
    A += ("ss111s" -> 22)
    A
  }
}

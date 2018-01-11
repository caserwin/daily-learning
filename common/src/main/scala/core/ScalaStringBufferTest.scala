package core

object ScalaStringBufferTest {

  def main(args: Array[String]): Unit = {
    val Statement = StringBuilder.newBuilder
    Statement.append("123")
    println(Statement.toString)

    val Statement1 = new StringBuffer
    Statement1.append("123")
    println(Statement1.toString)

    val TupleList = List(
      ("a", "1"),
      ("b", "2"),
      ("c", "3")
    )

    for ((name, id) <- TupleList) {
        println(name+"\t"+id)
    }
  }
}

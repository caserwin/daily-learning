package core

object ScalaMkStringDemo {

  def main(args: Array[String]): Unit = {

    val fields = Seq("col1", "col2")
    val cols = fields.map(field => s"$field  string  comment  '$field'").mkString(",")
    println(cols)
  }
}

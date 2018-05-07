package util

object ScalaStringDemo {

  def main(args: Array[String]): Unit = {
    val str: String = "12345 23dd"

    // 字符串是否包含
    println(str.contains("1234"))

    // 字符串切割
    println(str.split("\\s+")(0))

    // 字符串拼接
    println("aa".concat("cc"))

    // mkstring
    val fields = Seq("col1", "col2")
    println(fields.map(field => s"$field  string  comment  '$field'").mkString(","))
  }
}

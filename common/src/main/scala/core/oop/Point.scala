package core.oop

class Point(xc: Int, yc: Int) {
  var x: Int = xc
  var y: Int = yc

  def move(dx: Int, dy: Int) {
    x = x + dx
    y = y + dy
    println ("x 的坐标点: " + x)
    println ("y 的坐标点: " + y)
  }
}

class Book (var title :String, var ISBN: Int) {
  def this(title: String) {
    this(title, 2222)
  }
  def this() {
    this("CSS")
    this.ISBN = 1111
  }
  override def toString = s"$title ISBN- $ISBN"
}

object Test {
  def main(args: Array[String]) {
//    val pt = new Point()
//    // 移到一个新的位置
//    pt.move(10, 10)

    val book1 = new Book
    val book2 = new Book("Clojure")
    val book3 = new Book("Scala", 3333)

    println(book1)
    println(book2)
    println(book3)
  }
}

package core.sort

import java.util
import java.util.{Collections, Comparator}
import scala.collection.JavaConversions._

object ScalaCollectionsSortDemo {

  def main(args: Array[String]): Unit = {
    val res = new util.ArrayList[Integer]()
    res.add(2)
    res.add(8)
    res.add(3)
    res.add(1)
    res.add(5)

    Collections.sort(res, new Comparator[Integer] {
      override def compare(s: Integer, o: Integer): Int = s.compareTo(o)
    })

    for(x <- res) println(x)
  }
}

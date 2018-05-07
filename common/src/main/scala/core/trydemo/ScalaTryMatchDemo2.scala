package util.trydemo

import java.io.{FileNotFoundException, PrintWriter, StringWriter}
import scala.util.{Failure, Success, Try}

/**
  * Created by yidxue on 2018/3/8
  * code reference: https://alvinalexander.com/source-code/scala/scala-try-success-and-failure-example
  */
object ScalaTryMatchDemo2 {
  def main(args: Array[String]): Unit = {
    val sw = new StringWriter

    badAdder(3) match {
      case Success(i) => println(s"success, i = $i")
      case Failure(exception) =>
        exception match {
          case _: ArrayIndexOutOfBoundsException =>
            println("this is ArrayIndexOutOfBoundsException")
            exception.printStackTrace(new PrintWriter(sw))
            println(sw.toString)
          case _: FileNotFoundException =>
            println("this is FileNotFoundException")
            exception.printStackTrace(new PrintWriter(sw))
            println(sw.toString)
          case _ =>
            println("this is other exception")
            exception.printStackTrace(new PrintWriter(sw))
            println(sw.toString)
        }
    }
  }

  def badAdder(b: Int): Try[Int] = {
    Try {
      if (b == 3) {
        b
      } else if (b == 4) {
        throw new ArrayIndexOutOfBoundsException("Boom!")
      } else {
        throw new FileNotFoundException("Boom!")
      }
    }
  }
}

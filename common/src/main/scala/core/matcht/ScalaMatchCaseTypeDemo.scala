//package core.matcht
//
///**
//  * User: Erwin
//  * Date: 17/11/28 上午10:24
//  * Description:
//  */
//object ScalaMatchCaseTypeDemo {
//
////  implicit class Regex(sc: StringContext) {
////    def r = new util.matching.Regex(sc.parts.mkString, sc.parts.tail.map(_ => "x"): _*)
////  }
//
//  def main(args: Array[String]): Unit = {
//
//    "123" match {
//      case "d+" => println("====")
//      case _ => println("----")
//    }
//
//
//    "123" match {
//      case r"(d+)$d" => d.toInt
//      case _ => 0
//    }
//
//  }
//}

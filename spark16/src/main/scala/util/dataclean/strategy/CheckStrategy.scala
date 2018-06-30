package util.dataclean.strategy

import scala.util.{Failure, Success, Try}

/**
  * Created by yidxue on 2018/6/29
  */
object CheckStrategy {
  def getStringCheckStrategy(value: Any, field_action: String, legal_values: String): Boolean = {
    val legal_values_seq = legal_values.split(",")
    val flag = field_action.toLowerCase match {
      case "include" if legal_values_seq.toSet.contains(String.valueOf(value)) => true
      case "exclude" if !legal_values_seq.toSet.contains(String.valueOf(value)) => true
      case _ => false
    }
    flag
  }

  def getIntCheckStrategy(value: Any, field_action: String, legal_values: String): Boolean = {
    def transform(value: Any): Try[Int] = Try(value.toString.toInt)

    val legal_values_range = legal_values.split("-").map(x => x.toInt)

    val flag = transform(value: Any) match {
      case Success(v) =>
        field_action.toLowerCase match {
          case "include" if legal_values_range(0) <= v && v <= legal_values_range(1) => true
          case "exclude" if !(legal_values_range(0) <= v && v <= legal_values_range(1)) => true
          case _ => false
        }
      case Failure(_) => false
    }
    flag
  }

  def getFloatCheckStrategy(value: Any, field_action: String, legal_values: String): Boolean = {
    def transform(value: Any): Try[Float] = Try(value.toString.toFloat)

    val legal_values_range = legal_values.split("-").map(x => x.toFloat)

    val flag = transform(value: Any) match {
      case Success(v) =>
        field_action.toLowerCase match {
          case "include" if legal_values_range(0) <= v && v <= legal_values_range(1) => true
          case "exclude" if !(legal_values_range(0) <= v && v <= legal_values_range(1)) => true
          case _ => false
        }
      case Failure(_) => false
    }
    flag
  }
}

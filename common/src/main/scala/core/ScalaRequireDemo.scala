package util

import date.DateUtil

object ScalaRequireDemo {

  def main(args: Array[String]): Unit = {
    val date = "2018-01-19 05:04"
    require(DateUtil.isValidDate(date, "yyyy-MM-dd HH:mm"), "the date should be format to yyyy-MM-dd HH:mm")
  }
}

package core

import date.DateUtil

object ScalaRequireDemo {

  def main(args: Array[String]): Unit = {
    val date = "2018-01-90"
    require(DateUtil.isValidDate(date), "the date should be format to yyyy-MM-dd")
  }
}

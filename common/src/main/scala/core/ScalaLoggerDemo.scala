package core

import org.apache.log4j.{BasicConfigurator, LogManager}
import org.slf4j.LoggerFactory

/**
  * Created by yidxue on 2018/3/9
  * reference: https://stackoverflow.com/questions/12532339/no-appenders-could-be-found-for-loggerlog4j
  */
object ScalaLoggerDemo {
  var logger1: org.slf4j.Logger = LoggerFactory.getLogger("ScalaLoggerDemo")
  val logger2: org.apache.log4j.Logger = LogManager.getRootLogger

  def main(args: Array[String]): Unit = {
    BasicConfigurator.configure()
    logger1.info("logger1:  job finish.....")
    logger2.info("logger2:  job finish.....")
  }
}

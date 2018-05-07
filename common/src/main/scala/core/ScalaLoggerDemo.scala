package util

import org.slf4j.{Logger, LoggerFactory}
import org.apache.log4j.BasicConfigurator

/**
  * Created by yidxue on 2018/3/9
  * reference: https://stackoverflow.com/questions/12532339/no-appenders-could-be-found-for-loggerlog4j
  */
object ScalaLoggerDemo {
  var logger: Logger = LoggerFactory.getLogger("ScalaLoggerDemo")

  def main(args: Array[String]): Unit = {
    BasicConfigurator.configure()
    logger.info("logger:  job finish.....")
  }
}

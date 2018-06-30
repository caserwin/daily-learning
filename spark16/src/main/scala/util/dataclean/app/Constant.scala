package util.dataclean.app

import com.typesafe.config.ConfigFactory

/**
  * Created by yidxue on 2018/6/29
  */
object Constant {
  private val conf = ConfigFactory.load

  val PERSON_ATTRIBUTE: String = conf.getString("pcia.feature.attribute")

  def GET_PERSON_FIELDS_TYPE(field: String): String = {
    conf.getString(s"pcia.feature.$field.type")
  }

  def GET_PERSON_FIELDS_ACTION(field: String): String = {
    conf.getString(s"pcia.feature.$field.action")
  }

  def GET_PERSON_FIELDS_VALUE(field: String): String = {
    conf.getString(s"pcia.feature.$field.value")
  }
}

package util.dataclean.service

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{lit, _}
import Constant
import util.dataclean.strategy.{CheckStrategy, ValueStrategy}

/**
  * Created by yidxue on 2018/6/29
  */
object FeatureCleanService {
  val fields: Array[String] = Constant.PERSON_ATTRIBUTE.split(",")

  def clean(df: DataFrame): DataFrame = {
    cleanCols(df, 1)
  }

  private val isError = udf(
    (field_type: String, value: Any, field_action: String, legal_values: String) => {
      val flag = field_type.toLowerCase match {
        case "string" => CheckStrategy.getStringCheckStrategy(value, field_action, legal_values)
        case "int" => CheckStrategy.getIntCheckStrategy(value, field_action, legal_values)
        case "float" => CheckStrategy.getFloatCheckStrategy(value, field_action, legal_values)
      }
      flag
    }
  )

  private def cleanCols(df: DataFrame, i: Int): DataFrame = {
    if (i == fields.length) {
      df
    } else {
      val field = fields(i - 1)
      val field_type = Constant.GET_PERSON_FIELDS_TYPE(field)
      val field_action = Constant.GET_PERSON_FIELDS_ACTION(field)
      val legal_values = Constant.GET_PERSON_FIELDS_VALUE(field)

      val clean_value = field_type.toLowerCase match {
        case "string" => ValueStrategy.getStringValueStrategy(df, field)
        case "int" => ValueStrategy.getIntValueStrategy(df, field)
        case "float" => ValueStrategy.getFloatValueStrategy(df, field)
      }
      cleanCols(df.withColumn(fields(i - 1), when(isError(lit(field_type), col(field), lit(field_action), lit(legal_values)), col(field)).otherwise(lit(clean_value))), i + 1)
    }
  }
}

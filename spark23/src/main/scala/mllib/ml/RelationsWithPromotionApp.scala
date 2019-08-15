package mllib.ml

import org.apache.spark.mllib.fpm.{AssociationRules, FPGrowth}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import sql.ProcessContext

object RelationsWithPromotionApp {
  val SAVE_PRECISION = "%.4f"

  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = new ProcessContext("cat-relation").spark
    val support = 0.2
    val confidence = 0.8

    val data = spark.sparkContext.textFile("data/sample_fpgrowth.txt")
    val transactions: RDD[Array[String]] = data.map(s => s.trim.split(' '))

    // 生成频繁项集
    val fpg = new FPGrowth()
      .setMinSupport(support)
      .setNumPartitions(10)
    val model = fpg.run(transactions)

    // 生成关联规则
    val freqItemsets = model.freqItemsets

    // map存储所有的项集及支持度
    val total = transactions.count()
    var mapFreq: Map[String, Double] = Map()
    freqItemsets.collect().foreach(itemset => mapFreq += (itemset.items.mkString(",") -> (itemset.freq * 1.0 / total)))

    val ar = new AssociationRules().setMinConfidence(confidence)
    val ruleRDD = ar.run(freqItemsets)

    val ruleRDDRow = ruleRDD.map(rule => {
      Row(
        rule.antecedent.mkString(","),
        rule.consequent.mkString(","),
        rule.confidence.formatted(SAVE_PRECISION).toString,
        (rule.confidence / mapFreq(rule.consequent.mkString(","))).formatted(SAVE_PRECISION).toString
      )
    })

    val TEMP_COLS = "source,target,confidence,lift"
    val schema = StructType(TEMP_COLS.split(",").map(fieldName => StructField(fieldName.trim, StringType, nullable = true)))
    val ruleDF = spark.createDataFrame(ruleRDDRow, schema)

    ruleDF.show()

    spark.stop()
  }
}

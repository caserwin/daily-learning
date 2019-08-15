package mllib.ml

import org.apache.spark.mllib.fpm.{AssociationRules, FPGrowth}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import sql.ProcessContext

object RelationsApp1 {
  def manOf[T: Manifest](t: T): Manifest[T] = manifest[T]

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

    val ar = new AssociationRules().setMinConfidence(confidence)
    val results = ar.run(freqItemsets)

    //
    results.collect().foreach { rule =>
      println(s"[${rule.antecedent.mkString(",")}=>${rule.consequent.mkString(",")} ]" +
        s" ${rule.confidence}")
    }
  }
}

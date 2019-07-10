package mllib.ml

import org.apache.spark.mllib.fpm.FPGrowth
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object RelationsApp2 {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[2]").appName("example").getOrCreate()
    val data = spark.sparkContext.textFile("data/sample_fpgrowth.txt")

    val transactions: RDD[Array[String]] = data.map(s => s.trim.split(' '))

    val fpg = new FPGrowth()
      .setMinSupport(0.2)
      .setNumPartitions(10)
    val model = fpg.run(transactions)

    model.freqItemsets.collect().foreach { itemset =>
      println(s"${itemset.items.mkString("[", ",", "]")},${itemset.freq}")
    }

    val minConfidence = 0.8
    model.generateAssociationRules(minConfidence).collect().foreach { rule =>
      println(s"${rule.antecedent.mkString("[", ",", "]")}=> " +
        s"${rule.consequent .mkString("[", ",", "]")},${rule.confidence}")
    }
  }
}

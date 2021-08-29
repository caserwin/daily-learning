package mllib

import core.AbstractParams
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Column, Row, SparkSession}
import org.apache.spark.storage.StorageLevel
import scopt.OptionParser


object SparkALSDemo {

  case class Params(
                     day: String = null,
                     objectType: String = "item",
                     jobType: String = "dev",
                     numIterations: Int = 20,
                     rank: Int = 10,
                     topNItem: Int = 10,
                     table: String = "TEST.table_name",
                     output: String = null
                   ) extends AbstractParams[Params]

  def main(args: Array[String]) {
    val defaultParams = Params()
    val parser = new OptionParser[Params]("ALS paramater") {
      head("--- ALS paramater ---")
      opt[String]("day")
        .text(s"day, default: ${defaultParams.day}")
        .action((x, c) => c.copy(day = x))
      opt[String]("objectType")
        .text(s"objectType, default: ${defaultParams.objectType}")
        .action((x, c) => c.copy(objectType = x))
      opt[String]("jobType")
        .text(s"jobType, default: ${defaultParams.jobType}")
        .action((x, c) => c.copy(jobType = x))
      opt[Int]("numIterations")
        .text(s"numIterations, default: ${defaultParams.numIterations}")
        .action((x, c) => c.copy(numIterations = x))
      opt[Int]("rank")
        .text(s"rank, default: ${defaultParams.rank}")
        .action((x, c) => c.copy(rank = x))
      opt[Int]("topNItem")
        .text(s"topNItem, default: ${defaultParams.topNItem}")
        .action((x, c) => c.copy(topNItem = x))
      opt[String]("output")
        .text(s"output, default: ${defaultParams.output}")
        .action((x, c) => c.copy(output = x))
      opt[String]("table")
        .text(s"table, default: ${defaultParams.table}")
        .action((x, c) => c.copy(table = x))
      note(
        """
          | parameter demo:
          | --day 20201120 --objectType item --jobType dev --numIterations 20 --rank 10 --table xxx
        """.stripMargin)
    }

    parser.parse(args, defaultParams).map { params =>
      run(params)
    } getOrElse {
      System.exit(1)
    }
  }

  def run(params: Params): Unit = {
    implicit val spark: SparkSession = SparkSession.builder.appName("ALSExample").getOrCreate()
    import spark.implicits._
    println(params)

    val suffix = if ("dev".equals(params.jobType)) {" limit 10000"} else{""}

    val (sql, object_id) = params.objectType match {
      case "item" => (s"select account_id, user_numId, item_id, true_score from ${params.table} where ds='${params.day}' $suffix", "item_id")
      case _ => (s"select account_id, user_numId, item_id, true_score from ${params.table}  where ds='${params.day}' $suffix", "item_id")
    }
    println(sql)
    val df = spark.sql(sql)
    df.persist(StorageLevel.MEMORY_AND_DISK_SER)
    df.show(100, truncate = false)

    val Array(training, test) =
      if ("dev".equals(params.jobType)) {
        df.randomSplit(Array(0.8, 0.2))
      } else {
        df.randomSplit(Array(1, 0))
      }

    val als = new ALS()
      .setMaxIter(params.numIterations)
      .setRank(params.rank)
      .setRegParam(0.01)
      .setUserCol("user_numId")
      .setItemCol(object_id)
      .setRatingCol("true_score")
    val model = als.fit(training)

    val userEmbeddingDf = model.userFactors
    val itemEmbeddingDf = model.itemFactors
    userEmbeddingDf.persist(StorageLevel.MEMORY_AND_DISK_SER)
    itemEmbeddingDf.persist(StorageLevel.MEMORY_AND_DISK_SER)

    userEmbeddingDf.show(100, truncate = false)
    itemEmbeddingDf.show(100, truncate = false)

    // 指标评估
    val evaluator = new RegressionEvaluator()
      .setMetricName("rmse")
      .setLabelCol("true_score")
      .setPredictionCol("prediction")

    if ("dev".equals(params.jobType)) {
      val (table, colStr) = params.objectType match {
        case "item" => ("test.table_nname", "account_id,user_numId,item_id,true_score,prediction")
        case _ => ("test.table_nname", "account_id,user_numId,item_id,true_score,prediction")
      }
      val cols: Array[Column] = colStr.split(",") map (x => col(x))
      val predictions = model.transform(test).select(cols: _*)

      val rmse = evaluator.evaluate(predictions)
      println("Root-mean-square error in test set= "+rmse)
    }

    val jdf = df.select("account_id", "user_numId").dropDuplicates(Seq("account_id", "user_numId"))
    val userEmbeddingResDf = userEmbeddingDf.withColumnRenamed("id", "user_numId").join(jdf, Seq("user_numId"), "inner")

    // 每个用户最适合的N个商品
    val userRecs = model.recommendForAllUsers(params.topNItem)
    val userRecsRes = userRecs.join(jdf, Seq("user_numId"), "inner").withColumn("recommendations", transform($"recommendations", lit(object_id)))

    userEmbeddingResDf.show(100)
    userRecsRes.show(100)

    val rmse = evaluator.evaluate(model.transform(training))
    println("Root-mean-square error in train set= "+rmse)

    // model 存 volume
    model.write.overwrite().save(params.output + "/" + params.objectType + "_" + params.day)
  }

  val transform: UserDefinedFunction = udf((rows: Seq[Row], object_id: String)
  => {
    rows.map(x => x.getAs[Int](object_id).toString + ":" + x.getAs[Double]("rating").toString).mkString(",")
  })
}









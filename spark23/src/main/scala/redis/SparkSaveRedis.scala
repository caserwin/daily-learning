package redis

import org.apache.spark.sql.{Row, SparkSession}

/**
  * Created by yidxue on 2020-03-14
  */
object SparkSaveRedis {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application").config("spark.master", "local[*]").getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      ("key1", "zhangsan"),
      ("key2", "lisi"),
      ("key3", "wangwu")
    )
    val redisDF = spark.sparkContext.parallelize(dataSeq1).toDF("key", "value")

    redisDF.repartition(10).foreachPartition {
      partition => {
        val redis = new JedisService("localhost:6379").getCluster

        while (partition.hasNext) {
          val row: Row = partition.next()
          val key = row.get(0).toString
          val value = row.get(1).toString

          val mainkey = "prefix_" + key

          redis.set(mainkey, value)
          redis.expire(mainkey, 3000)
        }
        redis.close()
      }
    }
  }
}
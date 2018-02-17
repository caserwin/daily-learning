package sql

import org.apache.spark.sql.SparkSession

object SparkSQLJoinDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("SQL Application")
      .config("spark.master", "local[*]")
      .config("spark.sql.crossJoin.enabled", "true")  // 注意，必须有这个配置。
      .getOrCreate()
    import spark.implicits._

    val dataSeq1 = Seq(
      (1, "zhangsan", "hangzhou"),
      (2, "lisi", "beijing"),
      (3, "wangwu", "shanghai")
    )
    val input1 = spark.sparkContext.parallelize(dataSeq1).toDF("id", "name", "city")


    val dataSeq2 = Seq(
      (1, "US-1"),
      (1, "US-2"),
      (2, "CN")
    )
    val input2 = spark.sparkContext.parallelize(dataSeq2).toDF("id", "country")

    input1.join(input2, Seq("id"), "leftanti").show()
    /* 两个集合根据某个字段求差集
        +---+------+--------+
        | id|  name|    city|
        +---+------+--------+
        |  3|wangwu|shanghai|
        +---+------+--------+
     */


    input1.join(input2, Seq("id"), "left").show()
    /* 左边数据全部保留。且相同key 的数据做笛卡尔积
      +---+--------+--------+-------+
      | id|    name|    city|country|
      +---+--------+--------+-------+
      |  1|zhangsan|hangzhou|   US-1|
      |  1|zhangsan|hangzhou|   US-2|
      |  2|    lisi| beijing|     CN|
      |  3|  wangwu|shanghai|   null|
      +---+--------+--------+-------+
     */


    input1.join(input2, Seq("id"), "leftsemi").show()

    /* 左半连接可以用于判断"是否存在"
      +---+--------+--------+
      | id|    name|    city|
      +---+--------+--------+
      |  1|zhangsan|hangzhou|
      |  2|    lisi| beijing|
      +---+--------+--------+
    */

    input1.join(input2, Seq("id"), "right").show()
    /* 右边数据全部保留。且相同key 的数据做笛卡尔积
      +---+--------+--------+-------+
      | id|    name|    city|country|
      +---+--------+--------+-------+
      |  1|zhangsan|hangzhou|   US-1|
      |  1|zhangsan|hangzhou|   US-2|
      |  2|    lisi| beijing|     CN|
      +---+--------+--------+-------+
     */


    input1.join(input2, Seq("id"), "inner").show()
    /* 左右两边的数据，必须存在相同的key，才能被保留。且相同key 的数据做笛卡尔积
      +---+--------+--------+-------+
      | id|    name|    city|country|
      +---+--------+--------+-------+
      |  1|zhangsan|hangzhou|   US-1|
      |  1|zhangsan|hangzhou|   US-2|
      |  2|    lisi| beijing|     CN|
      +---+--------+--------+-------+
      */

    input1.join(input2, Seq("id"), "outer").show()
    /* 左右两边的数据，全部被保留。且相同 key 的数据做笛卡尔积
      +---+--------+--------+-------+
      | id|    name|    city|country|
      +---+--------+--------+-------+
      |  1|zhangsan|hangzhou|   US-1|
      |  1|zhangsan|hangzhou|   US-2|
      |  2|    lisi| beijing|     CN|
      |  3|  wangwu|shanghai|   null|
      +---+--------+--------+-------+
      */

    input1.join(input2).show()
    /* 左右两边的数据，全部被保留。且所有数据做笛卡尔积
      +---+--------+--------+---+-------+
      | id|    name|    city| id|country|
      +---+--------+--------+---+-------+
      |  1|zhangsan|hangzhou|  1|   US-1|
      |  1|zhangsan|hangzhou|  1|   US-2|
      |  1|zhangsan|hangzhou|  2|     CN|
      |  2|    lisi| beijing|  1|   US-1|
      |  2|    lisi| beijing|  1|   US-2|
      |  2|    lisi| beijing|  2|     CN|
      |  3|  wangwu|shanghai|  1|   US-1|
      |  3|  wangwu|shanghai|  1|   US-2|
      |  3|  wangwu|shanghai|  2|     CN|
      +---+--------+--------+---+-------+
      */
  }
}

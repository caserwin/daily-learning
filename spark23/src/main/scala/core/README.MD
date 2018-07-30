##
Spark 初始化 demo1
```
 val sparkConf = new SparkConf().setAppName("spark demo").setMaster("local[*]")
 val sc = new SparkContext(sparkConf)
```
见：

Spark 初始化 demo2
```
 val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local[*]").getOrCreate()
```
见：SparkMapDemo



## transform 算子

### 读数据
### 持久化
### value型算子
### key-value型算子


## action 算子

foreach：循环遍历每一个元素。
collect：收集元素
collectAsMap：收集key/value型的RDD中的元素
saveAsTextFile：将最终的结果数据以文本格式保存到指定的HDFS目录中


## 参考
http://lxw1234.com/archives/2015/07/363.htm

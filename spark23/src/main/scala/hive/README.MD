注意点：

1. 和Hive相关的操作，spark的字段名和hive表的字段名必须小写。
2. 字段名注意不要是关键字。


会新建一张表，如果表存在的话，就会报错
```
inputDF.write.mode(SaveMode.xxx).partitionBy("l_date").format("hive").saveAsTable("testtable4")
```
上面中的xxx无论是 Append 还是 Overwrite 都有问题。Append会直接报错，Overwrite 则会覆盖历史数据。


### 其他建表和写入方法

1. 先建一张外部表，示例：
```
CREATE EXTERNAL TABLE `test.user`(`source` string, `target` string, `confidence` string, `param` string)
COMMENT '测试表,[创建人:xyd]'
PARTITIONED BY (`l_date` string)
ROW FORMAT SERDE
  'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe'
STORED AS INPUTFORMAT
  'org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat'
OUTPUTFORMAT
  'org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat'
LOCATION
  'hdfs://xxx/xxx/test/user'
TBLPROPERTIES ('parquet.compression'='SNAPPY')
```

2. Spark 中插入语句
```
    val savePath = s"hdfs://xxx/xxx/test/user/l_date=${param.train_date}"
    delete(savePath)
    ruleDF.repartition(20).write.format("parquet").save(savePath)
    spark.sql("MSCK REPAIR TABLE test.user")
```

delete方法：
```
    import java.net.URI
    import org.apache.hadoop.conf.Configuration
    import org.apache.hadoop.fs.{FileSystem, Path}

      def delete(deletePath: String): Unit = {
        val hdfs: FileSystem = FileSystem.get(URI.create(deletePath), new Configuration())
        val outPathClear: Path = new Path(deletePath)
        hdfs.delete(outPathClear, true)
      }
```
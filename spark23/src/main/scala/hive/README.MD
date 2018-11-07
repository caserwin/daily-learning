注意点：

1. 和Hive相关的操作，spark的字段名和hive表的字段名必须小写。
2. 字段名注意不要是关键字。


会新建一张表，如果表存在的话，就会报错
```
inputDF.write.mode(SaveMode.xxx).partitionBy("l_date").format("hive").saveAsTable("testtable4")
```
上面中的xxx无论是 Append 还是 Overwrite 都有问题。Append会直接报错，Overwrite 则会覆盖历史数据。
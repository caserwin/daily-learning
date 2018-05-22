### 参考：<br>
[Spark入门：读写HBase数据](http://dblab.xmu.edu.cn/blog/1094-2/)<br>
该参考资料比较旧，内容需要更新。<br>


### 1 创建 hbase 表
1 hbase shell 进入命令行模式<br>
2 create 'student','info' 建表<br>

### 2 maven pom.xml
读写 Hbase 数据，添加以下jar包
```
<dependency>
    <groupId>org.apache.hbase</groupId>
    <artifactId>hbase-common</artifactId>
    <version>1.2.0</version>
    <scope>provided</scope>
</dependency>

<dependency>
    <groupId>org.apache.hbase</groupId>
    <artifactId>hbase-server</artifactId>
    <version>1.2.0</version>
    <scope>provided</scope>
</dependency>

<dependency>
    <groupId>org.apache.hbase</groupId>
    <artifactId>hbase-client</artifactId>
    <version>1.2.0</version>
    <scope>provided</scope>
</dependency>
```

### 3 写入 hbase code
代码见 SparkWriteHbaseDemo1 <br>
查询：
scan 'student'
<div align=left><img width="1300" height="420" src="https://github.com/caserwin/daily-learning/raw/master/spark16/pic/spark-hbase-write.png"/></div>


### 4 读取 hbase code
实际运行需要改下，hbase.mapreduce.scan.row.start 和 hbase.mapreduce.scan.row.stop 参数。实际select的结果rowkey范围包括start，不包括stop。<br>
见 SparkReadHbaseDemo1
<div align=left><img width="1200" height="600" src="https://github.com/caserwin/daily-learning/raw/master/spark16/pic/spark-hbase-read.png"/></div>


### 其他
#### TableInputFormat
要继承重写 TableInputFormat 这个类。

#### TableOutputFormat
要继承重写 TableOutputFormat 这个类。


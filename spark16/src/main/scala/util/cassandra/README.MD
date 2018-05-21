## 参考
https://github.com/datastax/spark-cassandra-connector/tree/master/doc<br>
https://github.com/koeninger/spark-cassandra-example

## 创建Cassandra表并插入数据
1.创建一个space, 并使用这个space
```
create keyspace dev with replication = {'class':'SimpleStrategy','replication_factor':3};
use dev;
```
2.创建表
```
create table emp (empid int primary key, emp_first varchar, emp_last varchar, emp_dept varchar);
```
3.插入数据
```
insert into emp (empid, emp_first, emp_last, emp_dept) values (1,'fred','smith','eng');
insert into emp (empid, emp_first, emp_last, emp_dept) values (2,'eng','smith','eng');
```
4.查询
```
select * from emp;
```
如下：
<div align=center><img width="750" height="150" src="https://github.com/caserwin/daily-learning/raw/master/spark16/pic/spark-cassandra-demo1.png"/></div>

## Spark 读写
maven pom.xml。这里需要注意scala版本、spark版本一致即可。
```
<dependency>
    <groupId>com.datastax.spark</groupId>
    <artifactId>spark-cassandra-connector_2.10</artifactId>
    <version>1.6.11</version>
</dependency>
```

读结果
<div align=center><img width="550" height="150" src="https://github.com/caserwin/daily-learning/raw/master/spark16/pic/spark-cassandra-demo2.png"/></div>

写结果
<div align=center><img width="550" height="200" src="https://github.com/caserwin/daily-learning/raw/master/spark16/pic/spark-cassandra-demo3.png"/></div>

## 错误
[Not enough replica available for query at consistency ONE (1 required but only 0 alive)
](https://stackoverflow.com/questions/27974911/not-enough-replica-available-for-query-at-consistency-one-1-required-but-only-0?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa)
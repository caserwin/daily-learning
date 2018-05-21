## 参考
[Installing Cassandra on Mac OS X](https://gist.github.com/hkhamm/a9a2b45dd749e5d3b3ae) <br>
[Working with Apache Cassandra on Mac OS X](https://www.datastax.com/2012/01/working-with-apache-cassandra-on-mac-os-x) <br>
[Cassandra 命令](https://docs.datastax.com/en/cql/3.3/cql/cql_using/useAboutCQL.html) <br>
[Cassandra 中文介绍](https://teddyma.gitbooks.io/learncassandra_cn/content/about/about_cassandra.html) <br>

## Cassandra in Mac

Google 的BigTable与Amazon的Dynamo是非常成功的商业NoSQL实现。一些开源的 NoSQL 体系，如Facebook的Cassandra， Apache的HBase，也得到了广泛认同。
Cassandra类似于MongoDB，非关系型数据库。

// 安装
```
brew install cassandra
pip install cql
```
// mac 运行cassandra
```
cassandra
```
// 再开一个终端，进入客户端。
```
cqlsh localhost
```

// 基本命令使用
```
1. 创建一个keyspace
cqlsh> create keyspace dev with replication = {'class':'SimpleStrategy','replication_factor':1};

2. 使用这个keyspace,创建一张表，empid为主键
cqlsh> use dev;
cqlsh:dev> create table emp (empid int primary key, emp_first varchar, emp_last varchar, emp_dept varchar);

3.插入数据
cqlsh:dev> insert into emp (empid, emp_first, emp_last, emp_dept) values (1,'fred','smith','eng');

4. 更新数据
cqlsh:dev> update emp set emp_dept = 'fin' where empid = 1;

5. 查询数据
cqlsh:dev> select * from emp where empid = 1;

6. 创建二级索引，因为不能查除主键索引外的其他列。
cqlsh:dev> select * from emp where emp_dept = 'fin';
Bad Request: No indexed columns present in by-columns clause with Equal operator

cqlsh:dev> create index idx_dept on emp(emp_dept);
cqlsh:dev> select * from emp where emp_dept = 'fin’;
```

// 其他命令
```
查看所有表空间：describe keyspaces;
查看某个具体空间结构：describe keyspaces_name;
查看已有表：describe tables;
查看某个具体表结构：describe table keyspaces.table_name;
drop table table_name;
DROP KEYSPACE <identifier>
```

## Java 客户端
// maven pom.xml 文件。版本根据实际需求确定
```
<dependency>
    <groupId>com.datastax.cassandra</groupId>
    <artifactId>cassandra-driver-core</artifactId>
    <version>3.1.1</version>
</dependency>

<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>19.0</version>
</dependency
```
或
```
<dependency>
    <groupId>com.datastax.dse</groupId>
    <artifactId>dse-java-driver-core</artifactId>
    <version>1.2.4</version>
</dependency>
```
//

注意：
亲测：Cassandra sql insert 如果primary key有重复的话，insert的数据会直接覆盖旧数据。
[一个完整的标准的cassandra连接代码（java版）](https://blog.csdn.net/zhuwinmin/article/details/72683314) <br>
[JAVA操作cassandra数据库](https://www.cnblogs.com/zzd-zxj/p/6091123.html) <br>
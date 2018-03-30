# 说明

这个包都是通过scala运行系统的命令行。<br>
其中demo2是执行sqoop把oracle的数据写到Hbase的示例，并且隐去了用户名、密码、主机地址等信息。


这里两个demo中，demo2比demo1要好，推荐demo2的实现方式。
原因在于，执行下面一条语句，只能通过demo2实现

```
sqoop import -Dhbase.zookeeper.quorum=localhost --connect jdbc:oracle:thin:@localhost:1521:dbName --username xxx --password xxx --query "select col1, col2, col3 from table1" --fields-terminated-by "\t" --lines-terminated-by "\n" --hbase-table SITEINFO --column-family INFO --hbase-row-key SITEID -m 1 --hbase-create-table
```

要是通过demo1 的方式会报如下错误，原因在于 query 后面的参数是不可分的，但是demo1会根据空格切分，把那条select语句根据空格拆分为很多参数。
```
ERROR tool.BaseSqoopTool: Error parsing arguments for import:
ERROR tool.BaseSqoopTool: Unrecognized argument:
```

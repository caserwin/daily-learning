基本上参考翻译：https://ci.apache.org/projects/flink/flink-docs-release-1.4/dev/table/common.html

## flink SQL/Table API 代码结构
1. 创建环境
2. 注册表
3. 通过SQL或者Table API查询表
4. 保存查询结果
5. 执行: env.execute();

<pre><code>// for batch programs use ExecutionEnvironment instead of StreamExecutionEnvironment
StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

// create a TableEnvironment
// for batch programs use BatchTableEnvironment instead of StreamTableEnvironment
StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

// register a Table
tableEnv.registerTable("table1", ...)            // or
tableEnv.registerTableSource("table2", ...);     // or
tableEnv.registerExternalCatalog("extCat", ...);

// create a Table from a Table API query
Table tapiResult = tableEnv.scan("table1").select(...);
// create a Table from a SQL query
Table sqlResult  = tableEnv.sqlQuery("SELECT ... FROM table2 ... ");

// emit a Table API result Table to a TableSink, same for SQL result
tapiResult.writeToSink(...);

// execute
env.execute();</code></pre>

## 2 创建 TableEnvironment
batch和stream分别通过以下方式注册：
<pre><code>// ***************
// STREAMING QUERY
// ***************
StreamExecutionEnvironment sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
// create a TableEnvironment for streaming queries
StreamTableEnvironment sTableEnv = TableEnvironment.getTableEnvironment(sEnv);

// ***********
// BATCH QUERY
// ***********
ExecutionEnvironment bEnv = ExecutionEnvironment.getExecutionEnvironment();
// create a TableEnvironment for batch queries
BatchTableEnvironment bTableEnv = TableEnvironment.getTableEnvironment(bEnv);
</code></pre>

## 3 注册表
TableEnvironment可以认为是一个目录，这个目录维护已经注册了的表名。有两种类型的表，一种是input table，一种是output table。

input table 可以通过以下方式注册：<br>
   - 已经存在的 table 对象。通常是Table API或者SQL 查询的结果集。使用：<br>
   tableEnv.registerTable("table1", ...)
   - 通过外部数据得到的数据源。例如：文件，数据库，消息系统等得到的 TableSource。使用：<br>
   tableEnv.registerTableSource("table2", ...);
   - 通过DataStream 或 DataSet 注册表。使用：<br>
   tableEnv.registerExternalCatalog("extCat", ...);
   
output table 通过 TableSink 方式注册。

### 3.1 Register a TableSource
具体参考:<br>
[Table Sources & Sinks](https://ci.apache.org/projects/flink/flink-docs-release-1.4/dev/table/sourceSinks.html)


## 4 Table API/SQL/两者混合使用



## 5 保存查询结果(Emit a Table)
具体参考:<br>
[Table Sources & Sinks](https://ci.apache.org/projects/flink/flink-docs-release-1.4/dev/table/sourceSinks.html)

有两种方式保存结果:<br>
方式 1:<br>
Emit the result Table to the TableSink via the writeToSink() method<br>
方式 2:<br>
Register the TableSink with a specific schema<br>










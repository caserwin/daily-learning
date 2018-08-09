
## 备注下：这Demo有问题，跑不了的。
Spark 2.x 和 phoenix集成貌似没有spark1.6好。这里我只是记录下，期望以后能找到合适的解决方案。

```
<dependency>
    <groupId>org.apache.phoenix</groupId>
    <artifactId>phoenix-spark</artifactId>
    <version>4.13.1-HBase-1.2</version>
</dependency>

<dependency>
    <groupId>org.apache.phoenix</groupId>
    <artifactId>phoenix-core</artifactId>
    <version>4.13.1-HBase-1.2</version>
</dependency>
```

遇到两个异常，一个是：
```
Exception in thread "main" java.sql.SQLException: ERROR 726 (43M10): Inconsistent namespace mapping properties.. Cannot initiate connection as SYSTEM:CATALOG is found but client does not have phoenix.schema.isNamespaceMappingEnabled enabled
```
这个可以解决，参考：https://www.codetd.com/article/1543777

还有个是：
```
org.apache.hadoop.ipc.RemoteException(org.apache.hadoop.hbase.TableExistsException): SYSTEM:MUTEX
```
这个暂时没找到解决方案。
注意：ignite只能是spark 2.3才能使用。需要以下环境

1. pom.xml加载Jar包如下：

```
<dependency>
    <groupId>org.apache.ignite</groupId>
    <artifactId>ignite-spark</artifactId>
    <version>2.6.0</version>
    <!--<scope>provided</scope>-->
</dependency>
<dependency>
    <groupId>org.apache.ignite</groupId>
    <artifactId>ignite-spring</artifactId>
    <version>2.6.0</version>
    <!--<scope>provided</scope>-->
</dependency>
```

2. 需要配置example-ignite.xml 和 example-default.xml 文件
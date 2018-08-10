## pom.xml 配置
集群上phoenix 用cdh的phoenix版本是 4.13，所以这里使用配置：
```
    <dependency>
        <groupId>org.apache.phoenix</groupId>
        <artifactId>phoenix-core</artifactId>
        <version>4.13.1-HBase-1.3</version>
        <!--<scope>provided</scope>-->
    </dependency>

    <dependency>
        <groupId>org.scala-lang</groupId>
        <artifactId>scala-library</artifactId>
        <version>2.11.8</version>
        <!--<scope>provided</scope>-->
    </dependency>

    <dependency>
        <groupId>org.apache.hadoop</groupId>
        <artifactId>hadoop-common</artifactId>
        <version>2.7.1</version>
        <!--<scope>provided</scope>-->
    </dependency>

    <dependency>
        <groupId>org.apache.hbase</groupId>
        <artifactId>hbase-common</artifactId>
        <version>1.3.1</version>
        <!--<scope>provided</scope>-->
    </dependency>
    <dependency>
        <groupId>org.apache.hbase</groupId>
        <artifactId>hbase-server</artifactId>
        <version>1.3.1</version>
        <!--<scope>provided</scope>-->
    </dependency>
    <dependency>
        <groupId>org.apache.hbase</groupId>
        <artifactId>hbase-client</artifactId>
        <version>1.3.1</version>
        <!--<scope>provided</scope>-->
    </dependency>

    <dependency>
        <groupId>com.google.guava</groupId>
        <artifactId>guava</artifactId>
        <version>19.0</version>
        <!--<scope>provided</scope>-->
    </dependency>

    <dependency>
        <groupId>commons-lang</groupId>
        <artifactId>commons-lang</artifactId>
        <version>2.6</version>
        <!--<scope>provided</scope>-->
    </dependency>
```
## 运行命令
```
java -jar common-1.0-SNAPSHOT.jar phoenix_demo1
```
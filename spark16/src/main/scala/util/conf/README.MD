spark 设置并读取配置文件。<b>首先资源文件夹必须命名为 resources，其次必须被标记为资源文件夹才行。</b>

对于资源文件夹中的文件，有两种读取方式：<br>

#### 1 把文件打包到jar包中，作为内部配置读取<br>
--files /apps/conf/security.conf <br>
application.conf中引用该配置的写法为：include file("security")

#### 2 不把文件打包到jar包中，作为外部配置读取<br>
--conf spark.driver.extraJavaOptions="-Dconfig.resource=sap.conf"
application.conf中引用该配置的写法为：include "security"

对于示例Code，有以下输出：
<div align=center><img width="250" height="100" src="https://github.com/caserwin/daily-learning/raw/master/spark16/pic/spark_config_demo1.png"/></div>
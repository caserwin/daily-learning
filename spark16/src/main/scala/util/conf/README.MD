spark 设置并读取配置文件。有两种方式：<br>

#### 1 把文件打包到jar包中，作为内部配置读取<br>
--files /apps/conf/security.conf <br>
application.conf中引用该配置的写法为：include file("security")

#### 2 不把文件打包到jar包中，作为外部配置读取<br>
--conf spark.driver.extraJavaOptions="-Dconfig.resource=sap.conf"
application.conf中引用该配置的写法为：include "security"

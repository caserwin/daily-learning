// 写入Phoenix table 写法：<br>
DF.write.format("org.apache.phoenix.spark").mode(SaveMode.Overwrite).options(Map("table" -> tableName, "zkUrl" -> zkAddr)).save()

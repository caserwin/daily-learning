## 记录
1. 我常用的 spark-submit

```
spark-submit \
      --conf spark.driver.extraJavaOptions="-Dconfig.resource=xxx.conf" \
      --class app.Main \
      --master yarn-client \
      --executor-cores 4 \
      --driver-memory 2g \
      --executor-memory 8G \
      --num-executors 6 \
      --queue csparkpda \
      $1 $2 $3
```

2. spark中引用java bean 类。
```
implicit val SensorDataEncoder1: Encoder[DocumentBean] = Encoders.bean(classOf[bean.DocumentBean])
```
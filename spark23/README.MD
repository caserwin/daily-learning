我常用的 spark2-submit
```
spark2-submit \
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
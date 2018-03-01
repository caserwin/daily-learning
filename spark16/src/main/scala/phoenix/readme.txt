运行方式如下

spark-submit \
      --class phoenix.RowKeyByJDBCTest \
      --master yarn-client \
      --executor-cores 6 \
      --driver-memory 8g \
      --executor-memory 16G \
      --num-executors 6 \
      $1
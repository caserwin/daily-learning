package ignite;

import org.apache.ignite.spark.IgniteDataFrameSettings;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.col;

/**
 * Created by yidxue on 2018/9/21
 */
public class IgniteDFLoadTest2 {
    private static final String CONFIG = "spark23/src/main/resources/example-ignite.xml";

    public static void main(String[] args){
        SparkSession spark = SparkSession.builder()
                                 .appName("SomeAppName")
                                 .master("local[*]")
                                 .config("ignite.disableSparkSQLOptimization", "false")
                                 .getOrCreate();

        Dataset<Row> igniteDF = spark.read()
                                    .format(IgniteDataFrameSettings.FORMAT_IGNITE())
                                    .option(IgniteDataFrameSettings.OPTION_TABLE(), "person")
                                    .option(IgniteDataFrameSettings.OPTION_CONFIG_FILE(), CONFIG)
                                    .load()
                                    .filter(col("name").like("erw%"));

        igniteDF.explain();
        igniteDF.show();
        spark.stop();
    }
}
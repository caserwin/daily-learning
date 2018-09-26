package ignite;

import org.apache.ignite.spark.IgniteDataFrameSettings;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * Created by yidxue on 2018/9/21
 */
public class IgniteDFLoadTest1 {
    private static final String CONFIG = "spark23/src/main/resources/example-ignite.xml";
    private static final String CACHE_NAME = "SQL_PUBLIC_PERSON";

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
                                 .appName("SomeAppName")
                                 .master("local[*]")
                                 .config("ignite.disableSparkSQLOptimization", "true")
                                 .getOrCreate();

        Dataset<Row> df = spark.read()
                              .format(IgniteDataFrameSettings.FORMAT_IGNITE())
                              .option(IgniteDataFrameSettings.OPTION_TABLE(), "person")
                              .option(IgniteDataFrameSettings.OPTION_CONFIG_FILE(), CONFIG)
                              .load();
        df.createOrReplaceTempView("person");


        Dataset<Row> igniteDF = spark.sql("SELECT * FROM person WHERE name like 'erw%'");
        igniteDF.explain();
        igniteDF.show();
        df.explain();
        df.show();

        spark.stop();
    }
}

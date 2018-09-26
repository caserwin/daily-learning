package ignite;

import org.apache.ignite.spark.JavaIgniteContext;
import org.apache.ignite.spark.JavaIgniteRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * Created by yidxue on 2018/9/21
 */
public class IgniteRDDLoadTest {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
                                 .appName("SomeAppName")
                                 .master("local[*]")
                                 .config("ignite.disableSparkSQLOptimization", "true")
                                 .getOrCreate();

        JavaSparkContext sparkContext = new JavaSparkContext(spark.sparkContext());
        JavaIgniteContext<Integer, Integer> igniteContext = new JavaIgniteContext<>(sparkContext, "spark23/src/main/resources/example-ignite.xml", true);
        JavaIgniteRDD<Integer, Integer> igniteRdd1 = igniteContext.<Integer, Integer>fromCache("SQL_PUBLIC_PERSON");

        //here Ignite sql processor will be used because inside SqlFieldsQuery
        Dataset<Row> ds1 = igniteRdd1.sql("select * from person where name like 'erw%'");
        Dataset<Row> ds2 = igniteRdd1.sql("select * from person ");

        ds1.show();
        ds2.show();
        ds1.join(ds2, ds1.col("ID").equalTo(ds2.col("ID")), "left_outer").show();

        spark.stop();
    }
}
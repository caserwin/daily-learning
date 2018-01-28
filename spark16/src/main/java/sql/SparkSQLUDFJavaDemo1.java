package sql;

import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.*;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;

/**
 * @author yidxue
 */
public class SparkSQLUDFJavaDemo1 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Java UDF Example").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new org.apache.spark.sql.SQLContext(sc);

        DataFrame df = sqlContext.read().json("data/temperatures.json");
        df.registerTempTable("citytemps");

        // Register the UDF with our SQLContext
        sqlContext.udf().register("CTOF", (UDF1<Double, Double>) degreesCelcius -> ((degreesCelcius * 9.0 / 5.0) + 32.0), DataTypes.DoubleType);

        sqlContext.sql("SELECT city, CTOF(avgLow) AS avgLowF, CTOF(avgHigh) AS avgHighF FROM citytemps").show();
    }
}

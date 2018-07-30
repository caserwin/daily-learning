package util;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author yiding
 */
public class WordCount {

    public static void main(String[] args) {
//        String intputDir=args[0];
//        String outputDir=args[1];
        String intputDir="data/data";
        String outputDir="res";

        SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCountTest2");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> rddlist = sc.textFile(intputDir);
        JavaRDD<String> flatMapRdd = rddlist.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String str) {
                ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(str.split(" ")));
                return arrayList.iterator();
            }
        });

        //再转化为键值对
        JavaPairRDD<String, Integer> pairRdd = flatMapRdd.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });
        //对每个词语进行计数
        JavaPairRDD<String, Integer> countRdd = pairRdd.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer i1, Integer i2) {
                return i1 + i2;
            }
        });
        System.out.println("结果："+countRdd.collect());
//        countRdd.saveAsTextFile(outputDir);
        sc.close();
    }
}

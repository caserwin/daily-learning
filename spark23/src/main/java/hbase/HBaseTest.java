package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Base64;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yidxue
 */
public class HBaseTest {

    public static Configuration conf;
    public HTable table = null;


    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "master,worker");
        conf.set("hbase.master", "10.14.66.215:60000");
        System.out.println(conf.get("hbase.zookeeper.quorum"));
    }

    /**
     * create table
     */
    public static void creatTable(String tableName, String[] familys)
        throws Exception {
        HBaseAdmin admin = new HBaseAdmin(conf);
        if (admin.tableExists(tableName)) {
            System.out.println("table already exists!");
        } else {
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            for (int i = 0; i < familys.length; i++) {
                tableDesc.addFamily(new HColumnDescriptor(familys[i]));
            }
            admin.createTable(tableDesc);
            System.out.println("create table " + tableName + " ok.");
        }
    }

    /**
     * delete table
     */
    public static void deleteTable(String tableName) throws Exception {
        try {
            HBaseAdmin admin = new HBaseAdmin(conf);
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println("delete table " + tableName + " ok.");
        } catch (MasterNotRunningException e) {
            e.printStackTrace();
        } catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        }
    }

    /**
     * insert data
     */
    public static void addRecord(String tableName, String rowKey,
                                 String family, String qualifier, String value) throws Exception {
        try {
            HTable table = new HTable(conf, tableName);
            Put put = new Put(Bytes.toBytes(rowKey));
            put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier),
                Bytes.toBytes(value));
            table.put(put);
            System.out.println("insert recored " + rowKey + " to table "
                                   + tableName + " ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete record
     */
    public static void delRecord(String tableName, String rowKey)
        throws IOException {
        HTable table = new HTable(conf, tableName);
        List list = new ArrayList();
        Delete del = new Delete(rowKey.getBytes());
        list.add(del);
        table.delete(list);
        System.out.println("del recored " + rowKey + " ok.");
    }

    /**
     * query record
     */
    public static void getOneRecord(String tableName, String rowKey)
        throws IOException {
        HTable table = new HTable(conf, tableName);
        Get get = new Get(rowKey.getBytes());
        Result rs = table.get(get);
        for (KeyValue kv : rs.raw()) {
            System.out.print(new String(kv.getRow()) + " ");
            System.out.print(new String(kv.getFamily()) + ":");
            System.out.print(new String(kv.getQualifier()) + " ");
            System.out.print(kv.getTimestamp() + " ");
            System.out.println(new String(kv.getValue()));
        }
    }

    /**
     * show data
     */
    public static void getAllRecord(String tableName) {
        try {
            HTable table = new HTable(conf, tableName);
            Scan s = new Scan();
            ResultScanner ss = table.getScanner(s);
            for (Result r : ss) {
                for (KeyValue kv : r.raw()) {
                    System.out.print(new String(kv.getRow()) + " ");
                    System.out.print(new String(kv.getFamily()) + ":");
                    System.out.print(new String(kv.getQualifier()) + " ");
                    System.out.print(kv.getTimestamp() + " ");
                    System.out.println(new String(kv.getValue()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String convertScanToString(Scan scan) throws IOException {
        ClientProtos.Scan proto = ProtobufUtil.toScan(scan);
        return Base64.encodeBytes(proto.toByteArray());
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub

        SparkConf conf1 = new SparkConf().setAppName(
            "DrCleaner_Retention_Rate_Geo_2_to_14").set("spark.executor.memory", "2000m").setMaster(
            "spark://10.14.66.215:7077").set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                              .setJars(new String[]{"/D:/hbase/build/libs/hbase.jar"});
        conf1.set("spark.cores.max", "4");
        SparkSession spark = SparkSession
                                 .builder()
                                 .appName("Java Spark SQL basic example")
                                 .config(conf1)
                                 .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());


        try {
            String tablename = "scores";
            String[] familys = {"family1", "family2"};
            HBaseTest.creatTable(tablename, familys);

            // add record row and row2
            HBaseTest.addRecord(tablename, "row1", "family1", "q1", "5");
            HBaseTest.addRecord(tablename, "row1", "family1", "q2", "90");
            HBaseTest.addRecord(tablename, "row2", "family2", "q1", "97");
            HBaseTest.addRecord(tablename, "row2", "family2", "q2", "87");

            System.out.println("===========get one record========");
            HBaseTest.getOneRecord(tablename, "scores");

            System.out.println("===========show all record========");
            HBaseTest.getAllRecord(tablename);

            System.out.println("===========del one record========");
            HBaseTest.delRecord(tablename, "row");
            HBaseTest.getAllRecord(tablename);

            System.out.println("===========show all record========");
            HBaseTest.getAllRecord(tablename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            Scan scan = new Scan();
            //  scan.setStartRow(Bytes.toBytes("195861-1035177490"));
            //  scan.setStopRow(Bytes.toBytes("195861-1072173147"));

            scan.addColumn(Bytes.toBytes("family1"), Bytes.toBytes("q1"));
            scan.addColumn(Bytes.toBytes("family2"), Bytes.toBytes("q1"));
            List<Filter> filters = new ArrayList<Filter>();
            // RegexStringComparator comp = new RegexStringComparator("87"); // 以 you 开头的字符串
            //  SingleColumnValueFilter filter2 = new SingleColumnValueFilter(Bytes.toBytes("family1"), Bytes.toBytes("q1"), CompareFilter.CompareOp.EQUAL, comp);
            SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("family1"),
                Bytes.toBytes("q1"),
                CompareFilter.CompareOp.GREATER, Bytes.toBytes("88"));
            //if set true will skip row which column doesn't exist
            filter.setFilterIfMissing(true);
            SingleColumnValueFilter filter2 = new SingleColumnValueFilter(Bytes.toBytes("family2"),
                Bytes.toBytes("q2"),
                CompareFilter.CompareOp.LESS, Bytes.toBytes("111"));
            filter2.setFilterIfMissing(true);

            PageFilter filter3 = new PageFilter(10);
            filters.add(filter);
            filters.add(filter2);
            filters.add(filter3);
            FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);
            scan.setFilter(filterList);

            conf.set(TableInputFormat.INPUT_TABLE, "scores");
            conf.set(TableInputFormat.SCAN, convertScanToString(scan));

            //read data from hbase
            JavaPairRDD<ImmutableBytesWritable, Result> hBaseRDD = sc.newAPIHadoopRDD(conf,
                TableInputFormat.class, ImmutableBytesWritable.class,
                Result.class);
            long count = hBaseRDD.count();
            System.out.println("count: " + count);

            JavaRDD<Person> datas2 = hBaseRDD.map(new Function<Tuple2<ImmutableBytesWritable, Result>, Person>() {
                @Override
                public Person call(Tuple2<ImmutableBytesWritable, Result> immutableBytesWritableResultTuple2) throws Exception {
                    Result result = immutableBytesWritableResultTuple2._2();
                    byte[] o = result.getValue(Bytes.toBytes("course"), Bytes.toBytes("art"));
                    if (o != null) {
                        Person person = new Person();
                        person.setAge(Integer.parseInt(Bytes.toString(o)));
                        person.setName(Bytes.toString(result.getRow()));
                        return person;
                    }
                    return null;
                }
            });


            Dataset<Row> data = spark.createDataFrame(datas2, Person.class);
            data.show();
            //write data to hbase
            Job newAPIJobConfiguration1 = Job.getInstance(conf);
            newAPIJobConfiguration1.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, "scores");
            newAPIJobConfiguration1.setOutputFormatClass(org.apache.hadoop.hbase.mapreduce.TableOutputFormat.class);

            // create Key, Value pair to store in HBase
            JavaPairRDD<ImmutableBytesWritable, Put> hbasePuts = data.javaRDD().mapToPair(
                new PairFunction<Row, ImmutableBytesWritable, Put>() {
                    @Override
                    public Tuple2<ImmutableBytesWritable, Put> call(Row row) throws Exception {
                        //row key
                        Put put = new Put(Bytes.toBytes(row.<String>getAs("name") + "test"));
                        put.add(Bytes.toBytes("family1"), Bytes.toBytes("q1"), Bytes.toBytes(String.valueOf(row.<Long>getAs("age"))));

                        return new Tuple2<>(new ImmutableBytesWritable(), put);
                    }
                });

            // save to HBase- Spark built-in API method
            hbasePuts.saveAsNewAPIHadoopDataset(newAPIJobConfiguration1.getConfiguration());
            spark.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


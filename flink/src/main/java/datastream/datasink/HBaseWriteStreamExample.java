package datastream.datasink;

import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * This is an example how to write streams into HBase. In this example the
 * stream will be written into a local Hbase but it is possible to adapt this
 * example for an HBase running in a cloud. You need a running local HBase with a
 * table "flinkExample" and a column "entry". If your HBase configuration does
 * not fit the hbase-site.xml in the resource folder then you gave to delete temporary this
 * hbase-site.xml to execute the example properly.
 */
public class HBaseWriteStreamExample {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setRestartStrategy(org.apache.flink.api.common.restartstrategy.RestartStrategies.fixedDelayRestart(Integer.MAX_VALUE, org.apache.flink.api.common.time.Time.of(10000, java.util.concurrent.TimeUnit.MILLISECONDS)));
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        // data stream with random numbers
        DataStream<String> dataStream = env.addSource(new SourceFunction<String>() {
            private static final long serialVersionUID = 1L;
            private volatile boolean isRunning = true;

            @Override
            public void run(SourceContext<String> out) throws Exception {
                while (isRunning) {
                    out.collect(String.valueOf(Math.floor(Math.random() * 100)));
                }
            }

            @Override
            public void cancel() {
                isRunning = false;
            }
        });

        dataStream.writeUsingOutputFormat(new HBaseOutputFormat());

        env.execute();
    }

    /**
     * This class implements an OutputFormat for HBase.
     */
    private static class HBaseOutputFormat implements OutputFormat<String> {

        private org.apache.hadoop.conf.Configuration conf = null;
        private Table table = null;
        private String taskNumber = null;
        private int rowNumber = 0;
        private Connection connection = null;
        private static final long serialVersionUID = 1L;

        @Override
        public void configure(Configuration parameters) {
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "xxx");
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            conf.set("zookeeper.znode.parent", "xxx");
            conf.setInt("hbase.client.retries.number", 1);
            conf.setInt("zookeeper.recovery.retry", 0);

            try {
                connection = ConnectionFactory.createConnection(conf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void open(int taskNumber, int numTasks) throws IOException {
            this.table = this.connection.getTable(TableName.valueOf("xxx:xxx"));
            this.taskNumber = String.valueOf(taskNumber);
        }

        @Override
        public void writeRecord(String record) throws IOException {
            Put put = new Put(Bytes.toBytes("rowkey_" + String.valueOf(taskNumber + rowNumber)));
            put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("entry"), Bytes.toBytes(rowNumber));
            rowNumber++;
            this.table.put(put);
        }

        @Override
        public void close() throws IOException {
            this.table.close();
            this.connection.close();
        }
    }
}


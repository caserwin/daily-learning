package datastream.activeuser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import datastream.activeuser.service.ExtractJsonField;
import datastream.activeuser.service.UserFlatMapFunction;
import datastream.activeuser.service.UserMapFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.JSONDeserializationSchema;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Properties;


/**
 * @author yiding
 */
public class ActiveUsersApp {

    public void run(Properties props, int parallelism) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(parallelism);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.disableOperatorChaining();

        final StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
        props.setProperty("bootstrap.servers", props.getProperty("input.bootstrap.servers"));

//        new FlinkKafkaConsumer010<>("dsdd", new JSONDeserializationSchema(), props);

        FlinkKafkaConsumer010<ObjectNode> consumer010 = new FlinkKafkaConsumer010(props.getProperty("input-topic"), new JSONDeserializationSchema(), props);
        consumer010.setStartFromEarliest();

        DataStream<ObjectNode> inputStream = env.addSource(consumer010).name("Kafka 0.10 Source")
                                                 .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<ObjectNode>(Time.seconds(1)) {
                                                     @Override
                                                     public long extractTimestamp(ObjectNode jsonNodes) {
                                                         DateTime dateTime = DateTime.parse(jsonNodes.get("@timestamp").asText(), ISODateTimeFormat.dateTime());
                                                         return dateTime.getMillis();
                                                     }
                                                 }).name("Timestamp extractor");

        DataStream<Tuple1<ObjectNode>> transformedSource = inputStream.flatMap(new FlatMapFunction<ObjectNode, Tuple1<ObjectNode>>() {
            @Override
            public void flatMap(ObjectNode value, Collector<Tuple1<ObjectNode>> out) throws Exception {
                String message = value.get("@message").asText();
                ObjectMapper mapper = new ObjectMapper();
                try {
                    JsonNode metric = mapper.readTree(message.split(":", 2)[1].trim());
                    value.set("SM", metric);
                } catch (JsonParseException e) {
                    System.err.println(message);
                }
                out.collect(Tuple1.of(value));
            }
        });

        tableEnv.registerFunction("extract_field", new ExtractJsonField());
        tableEnv.registerDataStream("kafka_raw_table", transformedSource, "node, rowtime.rowtime");

        String query1 = "SELECT node, rowtime FROM kafka_raw_table WHERE (extract_field(node, '_appname')='conv' " +
                            "AND extract_field(node,'SM.uaType')<>'Messenger' AND extract_field(node,'SM.uaType')<>'Hydra' " +
                            "AND extract_field(node,'SM.uaType')<>'UCConnector' AND ((extract_field(node,'SM.verb')='acknowledge' " +
                            "AND extract_field(node,'SM.object.objectType')='activity') OR (extract_field(node,'SM.verb')='post' " +
                            "AND extract_field(node,'SM.object.objectType')='comment') OR (extract_field(node,'SM.verb')='share' " +
                            "AND extract_field(node,'SM.object.objectType')='content') OR (extract_field(node,'SM.verb')='create' " +
                            "AND (extract_field(node,'SM.object.objectType')='conversation' " +
                            "OR extract_field(node,'SM.object.objectType')='team')) OR (extract_field(node,'SM.verb')='add' " +
                            "AND extract_field(node,'SM.object.objectType')='person' " +
                            "AND (extract_field(node,'SM.target.objectType')='conversation' " +
                            "OR extract_field(node,'SM.object.objectType')='team')))) " +
                            "OR (extract_field(node, '_appname')='locus' AND (extract_field(node,'SM.metricType')='PARTICIPANT' " +
                            "AND extract_field(node,'SM.callType')='TERMINATED' AND extract_field(node,'SM.squared')='true' " +
                            "AND extract_field(node,'SM.locusType')<>'JABBER'))";

        Table table1 = tableEnv.sql(query1);
        tableEnv.registerTable("filtered_table", table1);

        String query2 = "SELECT coalesce(extract_field(node,'userId'), extract_field(node,'@fields.USER_ID'), " +
                            "extract_field(node,'SM.actor.id'), extract_field(node,'SM.participant.userId'), " +
                            "extract_field(node,'SM.userId'), extract_field(node,'SM.uid'), extract_field(node,'SM_C.userId'), " +
                            "extract_field(node,'SM.onBoardedUser'), 'unknown') as userId, " +
                            "extract_field(node,'@timestamp') as time_stamp FROM filtered_table";

        Table table2 = tableEnv.sql(query2);

        // table source to DataStream
        DataStream<Row> rowStream = tableEnv.toAppendStream(table2, Row.class);

        // count active users
        DataStream<Tuple2<String, Integer>> dailyStream = rowStream
                                                              .map(new UserMapFunction.DailyMap())
                                                              .keyBy(0)
                                                              .flatMap(new UserFlatMapFunction())
                                                              .keyBy(0)
                                                              .sum(1);

        DataStream<Tuple2<String, Integer>> weeklyStream = rowStream
                                                               .map(new UserMapFunction.WeeklyMap())
                                                               .keyBy(0)
                                                               .flatMap(new UserFlatMapFunction())
                                                               .keyBy(0)
                                                               .sum(1);

        DataStream<Tuple2<String, Integer>> monthlyStream = rowStream
                                                                .map(new UserMapFunction.MonthlyMap())
                                                                .keyBy(0)
                                                                .flatMap(new UserFlatMapFunction())
                                                                .keyBy(0)
                                                                .sum(1);

        // output
        dailyStream.map(new UserMapFunction.FormatFieldMap()).print();
        weeklyStream.map(new UserMapFunction.FormatFieldMap()).print();
        monthlyStream.map(new UserMapFunction.FormatFieldMap()).print();

        // start run
        env.execute("Active User Count");
    }
}


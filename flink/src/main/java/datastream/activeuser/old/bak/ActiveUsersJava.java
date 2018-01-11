package datastream.activeuser.old.bak;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
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
import org.apache.flink.table.api.java.Tumble;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

public class ActiveUsersJava {

//    final static Logger logger = Logger.getLogger(ActiveUsers.class);

    public static void main(String[] args) throws Exception {
        System.out.println("this java active user");
        // parse input arguments
        final ParameterTool parameterTool = ParameterTool.fromArgs(new String[]{"-input-topic","aggr_splunk","-output-topic","out_topic","-bootstrap.servers","rpbt1hsn009.webex.com:9092","-group.id","cisco.test_java"});

        if (parameterTool.getNumberOfParameters() < 4) {
            System.out.println("Missing parameters!\n" +
                    "Usage: Kafka --input-topic <topic> --output-topic <topic> " +
                    "--bootstrap.servers <kafka brokers> " +
                    "--group.id <some id>");
            return;
        }

        // create streaming environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // enable event time processing
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // enable fault-tolerance
        //env.enableCheckpointing(1000);

        // enable restarts
        //env.setRestartStrategy(RestartStrategies.fixedDelayRestart(50, 500L));

        env.disableOperatorChaining();

        final StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);

        DataStream<ObjectNode> inputStream = env.addSource(new FlinkKafkaConsumer010(parameterTool.getRequired("input-topic"), new JSONDeserializationSchema(), parameterTool.getProperties()).setStartFromEarliest()).name("Kafka 0.10 Source")
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<ObjectNode>(Time.seconds(1)) {
                    @Override
                    public long extractTimestamp(ObjectNode jsonNodes) {
                        DateTime dateTime = DateTime.parse(jsonNodes.get("@timestamp").asText(), ISODateTimeFormat.dateTime());
                        return dateTime.getMillis();
                    }
                }).name("Timestamp extractor");

        DataStream<Tuple1<ObjectNode>> transformedSource = inputStream.flatMap(new FlatMapFunction<ObjectNode, Tuple1<ObjectNode>>() {
            public void flatMap(ObjectNode value, Collector<Tuple1<ObjectNode>> out) throws Exception {
                String message = value.get("@message").asText();
                ObjectMapper mapper = new ObjectMapper();
                try{
                    JsonNode metric = mapper.readTree(message.split(":", 2)[1].trim());
                    value.set("SM", metric);
                } catch (JsonParseException e){
//                    logger.warn("Invalid json string, skip @message: " + message);
                }
                out.collect(Tuple1.of(value));
            }
        });


        tableEnv.registerFunction("g", new ExtractJsonField());
        tableEnv.registerDataStream("raw", transformedSource, "node, rowtime.rowtime");

//        String query1 = "SELECT node, rowtime FROM raw WHERE g(node, '_appname')='conv' AND g(node,'SM.uaType')<>'Messenger' AND g(node,'SM.uaType')<>'Hydra' AND g(node,'SM.uaType')<>'UCConnector' AND ((g(node,'SM.verb')='acknowledge' AND g(node,'SM.object.objectType')='activity') OR (g(node,'SM.verb')='post' AND g(node,'SM.object.objectType')='comment') OR (g(node,'SM.verb')='share' AND g(node,'SM.object.objectType')='content') OR (g(node,'SM.verb')='create' AND (g(node,'SM.object.objectType')='conversation' OR g(node,'SM.object.objectType')='team')) OR (g(node,'SM.verb')='add' AND g(node,'SM.object.objectType')='person' AND (g(node,'SM.target.objectType')='conversation' OR g(node,'SM.object.objectType')='team')))";
        String query1 = "SELECT node, rowtime FROM raw WHERE (g(node, '_appname')='conv' AND g(node,'SM.uaType')<>'Messenger' AND g(node,'SM.uaType')<>'Hydra' AND g(node,'SM.uaType')<>'UCConnector' AND ((g(node,'SM.verb')='acknowledge' AND g(node,'SM.object.objectType')='activity') OR (g(node,'SM.verb')='post' AND g(node,'SM.object.objectType')='comment') OR (g(node,'SM.verb')='share' AND g(node,'SM.object.objectType')='content') OR (g(node,'SM.verb')='create' AND (g(node,'SM.object.objectType')='conversation' OR g(node,'SM.object.objectType')='team')) OR (g(node,'SM.verb')='add' AND g(node,'SM.object.objectType')='person' AND (g(node,'SM.target.objectType')='conversation' OR g(node,'SM.object.objectType')='team')))) OR (g(node, '_appname')='locus' AND (g(node,'SM.metricType')='PARTICIPANT' AND g(node,'SM.callType')='TERMINATED' AND g(node,'SM.squared')='true' AND g(node,'SM.locusType')<>'JABBER'))";

        Table table1 = tableEnv.sql(query1);
        tableEnv.registerTable("filtered", table1);

        String query2 = "SELECT coalesce(g(node,'orgId'), g(node,'SM.actor.orgId'), g(node,'SM.participant.orgId'), g(node,'SM.orgId'), 'unknown') as orgId, coalesce(g(node,'userId'), g(node,'@fields.USER_ID'), g(node,'SM.actor.id'), g(node,'SM.participant.userId'), g(node,'SM.userId'), g(node,'SM.uid'), g(node,'SM_C.userId'), g(node,'SM.onBoardedUser'), 'unknown') as userId, g(node,'@timestamp') as time_stamp, rowtime FROM filtered";
        Table table2 = tableEnv.sql(query2);
        tableEnv.registerTable("raw_table", table2);

        Table table3 = tableEnv.scan("raw_table")
                .window(Tumble.over("10.second").on("rowtime").as("userActionWindow"))
                .groupBy("userActionWindow, orgId")
                .select("userId.count as user_count, orgId, userActionWindow.start as start, userActionWindow.end as end");

//        tableEnv.toAppendStream(table1, Row.class).print();
//        tableEnv.toAppendStream(table2, Row.class).print();
//        tableEnv.toAppendStream(table3, Row.class).print();

        DataStream<Tuple2<Boolean, Row>> retractStream  = tableEnv.toRetractStream(table3, Row.class);

        retractStream.print();

//        Map<String, String> config = new HashMap<>();
//        config.put("bootstrap.servers", "localhost:9092");
//        Properties props = new Properties();
//        props.putAll(config);
//
//        FlinkKafkaPartitioner<Row> row = new FlinkFixedPartitioner<>();
//        KafkaJsonTableSink sink = new Kafka09JsonTableSink(
//                parameterTool.getRequired("output-topic"),
//                props,
//                row
//        );

//        table3.writeToSink(sink);
        env.execute("Spark Active User Aggregation");
    }

    public static class ExtractJsonField extends ScalarFunction {
        public String eval(ObjectNode node, String fields) {
            JsonNode jsonNode = node;
            for(String s: fields.split("\\.")){
                jsonNode = jsonNode.get(s);
                if(jsonNode == null) {
                    return null;
                }
            }
            return jsonNode.asText();
        }
    }

    public static class InputMap implements MapFunction<Tuple2<Boolean, Row>, Row> {
        private static final long serialVersionUID = 1L;

        @Override
        public Row map(Tuple2<Boolean, Row> in) throws Exception {
            if (in.getField(0)) {
                return in.getField(1);
            }
            return null;
        }
    }

}


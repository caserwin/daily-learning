//package datastream.transformations;
//
//import org.apache.flink.api.common.functions.AggregateFunction;
//import org.apache.flink.api.java.tuple.Tuple2;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//
///**
// * Created by yidxue on 2018/5/14
// * @author yidxue
// */
//public class FlinkAggregateFunctionDemo {
//
//    public static class CusAggregate implements AggregateFunction<> {
//
//        @Override
//        public String createAccumulator() {
//            return null;
//        }
//
//        @Override
//        public String merge(Object a, Object b) {
//            return null;
//        }
//
//        @Override
//        public Object getResult(Object accumulator) {
//            return null;
//        }
//
//        @Override
//        public String add(Object value, Object accumulator) {
//            return null;
//        }
//    }
//
//    public static void main(String[] args){
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//
//        DataStream<Tuple2<Integer, Integer>> dStream = env.fromElements(
//            Tuple2.of(2, 6),
//            Tuple2.of(2, 4),
//            Tuple2.of(1, 3),
//            Tuple2.of(1, 5),
//            Tuple2.of(6, 7),
//            Tuple2.of(6, 7),
//            Tuple2.of(1, 17),
//            Tuple2.of(1, 2));
//
//
//        DataStream<String> reStream = dStream.keyBy(0);
//        reStream.print();
//
//
//
//    }
//}

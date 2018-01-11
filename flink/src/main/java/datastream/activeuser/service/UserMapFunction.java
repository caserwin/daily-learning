package datastream.activeuser.service;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.types.Row;

/**
 * @author yiding
 */
public class UserMapFunction {

    public static class DailyMap implements MapFunction<Row, Tuple3<String, String, String>> {
        @Override
        public Tuple3<String, String, String> map(Row row) throws Exception {
            String date = String.valueOf(row.getField(1)).split("T")[0];

            String uid= row.getField(0).toString();
            return new Tuple3<>(date, uid, "day");
        }
    }


    public static class WeeklyMap implements MapFunction<Row, Tuple3<String, String, String>> {
        @Override
        public Tuple3<String, String, String> map(Row row) throws Exception {
            String[] times = new DateService().getSpecificWeekRange(String.valueOf(row.getField(1)));
            String date = times[0].split("T")[0]+" "+times[1].split("T")[0];

            String uid= row.getField(0).toString();
            return new Tuple3<>(date, uid, "week");
        }
    }


    public static class MonthlyMap implements MapFunction<Row, Tuple3<String, String, String>> {
        @Override
        public Tuple3<String, String, String> map(Row row) throws Exception {
            String date = row.getField(1).toString().substring(0, 7);

            String uid= row.getField(0).toString();
            return new Tuple3<>(date, uid, "month");
        }
    }


    public static class FormatFieldMap implements MapFunction<Tuple2<String, Integer>, String > {
        @Override
        public String map(Tuple2<String, Integer> inValue) throws Exception {
            return inValue.getField(0).toString()+"\t\t"+inValue.getField(1).toString();
        }
    }

}

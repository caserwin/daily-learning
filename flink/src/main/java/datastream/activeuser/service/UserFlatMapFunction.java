package datastream.activeuser.service;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

import java.util.HashSet;

/**
 * @author yiding
 */
public class UserFlatMapFunction extends RichFlatMapFunction<Tuple3<String, String, String>, Tuple2<String, Integer>> {

    /**
     * key is date, such as: day type(2017-10-10)、month type(2017-10)、week type(2017-10-08 2017-10-14)
     * value is uid set
     */

    private transient MapState<String, HashSet<String>> uidSetState;

    @Override
    public void flatMap(Tuple3<String, String, String> in, Collector<Tuple2<String, Integer>> out) throws Exception {
        String date = in.getField(0);
        String uid = in.getField(1);
        String mode = in.getField(2);

        HashSet<String> uidSet;

        // get or create object
        if (uidSetState.contains(date)) {
            uidSet = uidSetState.get(date);
        } else {
            uidSet = new HashSet<>();
        }

        // delete two day/week/month ago uid set
        String offsetDate;
        if (Constants.DAY.equals(mode)) {
            offsetDate = new DateService().getSpecificDay(date, -2);
        } else if (Constants.WEEK.equals(mode)) {
            offsetDate = new DateService().getSpecificWeek(date, -2);
        } else if (Constants.MONTH.equals(mode)){
            offsetDate = new DateService().getSpecificMonth(date, -2);
        }else {
            return;
        }

        if (uidSetState.contains(offsetDate)) {
            uidSetState.remove(offsetDate);
        }

        // update uid set
        if (!uidSet.contains(uid)) {
            uidSet.add(uid);
            out.collect(new Tuple2<>(date, 1));
        } else {
            System.out.println(mode+" duplicate uid " + uid);
        }

        // put object to state
        uidSetState.put(date, uidSet);
    }

    @Override
    public void open(Configuration config) {
        uidSetState = getRuntimeContext().getMapState(new MapStateDescriptor<>("uid hashset state", TypeInformation.of(new TypeHint<String>() {
        }), TypeInformation.of(new TypeHint<HashSet<String>>() {
        })));
    }
}
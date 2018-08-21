package datastream.window.windowfunctions;

import bean.MyEvent;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * Created by yidxue on 2018/8/21
 * 用于求和
 */
public class MyReduceFunction implements ReduceFunction<MyEvent> {
    @Override
    public MyEvent reduce(MyEvent e1, MyEvent e2) throws Exception {
        return new MyEvent(e1.value + e2.value, "message", (int) System.currentTimeMillis());
    }
}

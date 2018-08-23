package datastream.window.windowfunctions;

import bean.MyEvent;
import org.apache.flink.api.common.functions.FoldFunction;

/**
 * Created by yidxue on 2018/8/22
 * @author yidxue
 */
public class MyFoldFunction implements FoldFunction<MyEvent, String> {
    @Override
    public String fold(String accumulator, MyEvent event) throws Exception {
        return accumulator + "\t" + event.message;
    }
}

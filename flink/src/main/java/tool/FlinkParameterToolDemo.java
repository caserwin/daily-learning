package tool;

import org.apache.flink.api.java.utils.ParameterTool;

/**
 * Created by yidxue on 2018/9/8
 * Flink 中参数配置和读取方法
 */
public class FlinkParameterToolDemo {
    public static void main(String[] args) {
        final ParameterTool parameterTool = ParameterTool.fromArgs(new String[]{
            "--input-topic", "aggr_splunk",
            "--output-topic", "activeUser",
            "--bootstrap.servers", "rpbt1hsn009.webex.com:9092",
            "--group.id", "active.user.java",
            // you should modify the start.time and end.time to correspond with kafka consumer offset
            "--start.time", "2017-09-24T00:00:00.000Z",
            "--end.time", "2017-09-25T00:00:00.000Z"
        });

        System.out.println(parameterTool.getRequired("start.time"));
        System.out.println(parameterTool.getRequired("end.time"));
    }
}

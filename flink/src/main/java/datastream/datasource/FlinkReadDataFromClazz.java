package datastream.datasource;

import bean.WCBean;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by yidxue on 2018/2/20
 */
public class FlinkReadDataFromClazz {
     public static void main(String[] args) throws Exception {
         StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

         DataStream<WCBean> input = env.fromElements(
             new WCBean("Hello", 1),
             new WCBean("Ciao", 1),
             new WCBean("Hello", 1));

         input.print();
         env.execute("run");
     }
}

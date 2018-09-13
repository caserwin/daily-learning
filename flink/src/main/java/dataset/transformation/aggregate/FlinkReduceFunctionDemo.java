package dataset.transformation.aggregate;

import util.bean.WCBean;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;

/**
 * @author yidxue
 */
public class FlinkReduceFunctionDemo {

    public static class WordCounterReduce implements ReduceFunction<WCBean> {
        @Override
        public WCBean reduce(WCBean in1, WCBean in2) {
            return new WCBean(in1.word, in1.frequency + in2.frequency);
        }
    }

    public static class SelectWord implements KeySelector<WCBean, String> {
        @Override
        public String getKey(WCBean w) {
            return w.word;
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        WCBean[] wcs = new WCBean[]{new WCBean("erwin", 5), new WCBean("erwin", 5), new WCBean("yuyi", 3)};
        DataSet<WCBean> inData = env.fromElements(wcs);

        DataSet<WCBean> wordCounts = inData.groupBy(new SelectWord()).reduce(new WordCounterReduce());

        wordCounts.print();
    }
}
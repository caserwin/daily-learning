package util.source;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

/**
 * Created by yidxue on 2018/9/12
 */
public class StreamDataSource1 extends RichParallelSourceFunction<Tuple3<String, String, Long>> {
    private volatile boolean running = true;

    @Override
    public void run(SourceContext<Tuple3<String, String, Long>> ctx) throws InterruptedException {

        Tuple3[] elements = new Tuple3[]{
                Tuple3.of("a", "hangzhou", 1000000052000L),
                Tuple3.of("c", "hangzhou", 1000000052000L),
                Tuple3.of("a", "hangzhou1", 1000000052001L),
                Tuple3.of("b", "beijing", 1000000104000L)
        };

        int count = 0;
        while (running && count < elements.length) {
            ctx.collect(new Tuple3<>((String) elements[count].f0, (String) elements[count].f1, (long) elements[count].f2));
            count++;
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}
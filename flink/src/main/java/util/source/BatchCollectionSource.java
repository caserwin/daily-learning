package util.source;

import util.bean.WCBean;
import java.util.ArrayList;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * Created by yidxue on 2018/9/14
 */
public class BatchCollectionSource {
    public static ArrayList<Tuple3<String, String, Long>> getTupleSource() {
        ArrayList<Tuple3<String, String, Long>> arrayls = new ArrayList<>();
        arrayls.add(Tuple3.of("a", "1", 1000000050000L));
        arrayls.add(Tuple3.of("a", "2", 1000000054000L));
        arrayls.add(Tuple3.of("a", "3", 1000000079900L));
        arrayls.add(Tuple3.of("a", "4", 1000000115000L));
        arrayls.add(Tuple3.of("b", "5", 1000000100000L));
        arrayls.add(Tuple3.of("b", "6", 1000000108000L));

        return arrayls;
    }

    public static ArrayList<WCBean> getBeanSource() {
        ArrayList<WCBean> arrayls = new ArrayList<>();
        arrayls.add(new WCBean("Hello", 2));
        arrayls.add(new WCBean("Ciao", 1));
        arrayls.add(new WCBean("Hello", 1));

        return arrayls;
    }
}

package dataset.transformation.join;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * Created by yidxue on 2018/5/11
 * @author yidxue
 */
public class FlinkInnerJoinDemo1 {

    public class UserMap implements MapFunction<Tuple2<User, Store>, Tuple3<String, String, Integer>> {
        @Override
        public Tuple3<String, String, Integer> map(Tuple2<User, Store> arr) {
            return Tuple3.of(arr.f0.name, arr.f1.mgr, arr.f0.zip);
        }
    }

    public static class User {
        public String name;
        public int zip;

        public User() {
        }

        public User(String name, int zip) {
            this.name = name;
            this.zip = zip;
        }
    }

    public static class Store {
        public String mgr;
        public int zip;

        public Store() {
        }

        public Store(String mgr, int zip) {
            this.mgr = mgr;
            this.zip = zip;
        }
    }

    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<User> input1 = env.fromElements(
            new User("user1", 1),
            new User("user2", 2),
            new User("user3", 3),
            new User("user4", 4));
        DataSet<Store> input2 = env.fromElements(
            new Store("store1", 2),
            new Store("store2", 3),
            new Store("store3", 4),
            new Store("store4", 5));
        // result dataset is typed as Tuple2
        DataSet<Tuple2<User, Store>> result = input1.join(input2).where("zip").equalTo("zip");
        result.map(new FlinkInnerJoinDemo1().new UserMap()).print();
    }
}

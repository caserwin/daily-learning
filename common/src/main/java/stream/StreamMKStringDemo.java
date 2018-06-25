package stream;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yidxue on 2018/6/21
 * @author yidxue
 */
public class StreamMKStringDemo {
    public static void main(String[] args) {
        Stream<String> stream1 = Stream.of("chaimm", "peter", "john");
        System.out.println(stream1.collect(Collectors.joining(",")));

        Stream<Integer> stream2 = Stream.of(1, 2, 3);
        System.out.println(stream2.map(String::valueOf).collect(Collectors.joining(",")));
    }
}

package stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

/**
 * Created by erwin on 2018/6/21
 */
public class CreateStreamDemo {
    public static void main(String[] args) {
        // 通过数组得到 Stream
        String[] names = {"chaimm", "peter", "john"};
        Stream<String> stream1 = Arrays.stream(names);

        // 单个值，构造生成
        Stream<String> stream2 = Stream.of("chaimm", "peter", "john");

        // 通过集合得到 Stream
        List<String> list = Arrays.asList(names);
        Stream<String> stream3 = list.stream();

        List<String> result1 = stream1.map(String::toUpperCase).collect(toList());
        List<String> result2 = stream2.map(str -> str.toUpperCase()).collect(toList());
        List<String> result3 = stream3.map(str -> str.toUpperCase()).collect(toList());

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }
}

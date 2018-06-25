package stream;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yidxue on 2018/6/21
 * @author yidxue
 */
public class ListStreamMKStringDemo {
    public static void main(String[] args) {
        Stream<String> stream4 = Stream.of("chaimm", "peter", "john");
        System.out.println(stream4.collect(Collectors.joining(",")));
    }
}

package stream;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yidxue on 2018/6/25
 */
public class StreamDistinctDemo {
    public static void main(String[] args) {
        List<Integer> num = Lists.newArrayList(1, 1, 2, 2, 3, 6);
        List<Integer> res = num.stream().distinct().collect(Collectors.toList());

        res.forEach(System.out::println);
    }
}

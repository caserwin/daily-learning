package stream;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.google.common.collect.Lists;

/**
 * Created by yidxue on 2018/6/25
 */
public class StreamSortedDemo {
    public static void main(String[] args) {
        List<Integer> num = Lists.newArrayList(1, 2, 3, 2, 6, 4);
        List<Integer> res1 = num.stream().sorted().collect(Collectors.toList());
        List<Integer> res2 = num.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        res1.forEach(x -> System.out.print(x + ","));
        System.out.println();
        res2.forEach(x -> System.out.print(x + ","));
    }
}
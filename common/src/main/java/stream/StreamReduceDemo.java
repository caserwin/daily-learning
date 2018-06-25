package stream;

import java.util.HashSet;

/**
 * @author yidxue
 */
public class StreamReduceDemo {
    public static void main(String[] args){
        HashSet<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(-4);

        int sum = set.stream().reduce((a, b) -> a + b).get();
        System.out.println(sum);
    }
}

package clone;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by yidxue on 2018/8/2
 */
public class JavaCloneDemo {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        ArrayList<Integer> b = a;
        ArrayList<Integer> c = (ArrayList<Integer>) a.clone();
        a.add(4);

        System.out.println(a.stream().map(String::valueOf).collect(Collectors.joining(",")));
        System.out.println(b.stream().map(String::valueOf).collect(Collectors.joining(",")));
        System.out.println(c.stream().map(String::valueOf).collect(Collectors.joining(",")));

    }
}

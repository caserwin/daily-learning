package collection;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: caserwin
 * Date: 2020-06-14 16:40
 * Description:
 */
public class ListSortByValueDemo {
    @Data
    static class A {
        double a;

        public A(double a) {
            this.a = a;
        }
    }

    public static void main(String[] args) {
        A a1 = new A(0.00003);
        A a2 = new A(0.00001);
        A a3 = new A(0.00002);

        List<A> ls = new ArrayList<>();
        ls.add(a1);
        ls.add(a2);
        ls.add(a3);

        ls.sort(new ListComparatorDesc());
        System.out.println(ls.stream().limit(2).collect(Collectors.toList()));
    }


    public static class ListComparatorDesc implements Comparator<A> {
        @Override
        public int compare(A bc1, A bc2) {
            double a = bc2.getA() * 100000;
            double b = bc1.getA() * 100000;
            return (int) a - (int) b;
        }
    }
}

package core;

import java.util.ArrayList;

/**
 * Created by yidxue on 2019/4/30
 */
public class JavaListDemo {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(4);
        list.add(3);

        System.out.println(list.get(1));
        System.out.println(list.getClass());
    }
}

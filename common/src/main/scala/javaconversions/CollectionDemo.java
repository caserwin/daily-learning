package javaconversions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yidxue on 2018/3/8
 */
public class CollectionDemo {

    public static HashMap<String, String> getHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        return map;
    }

    public static ArrayList<String> getArrayList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        return list;
    }
}

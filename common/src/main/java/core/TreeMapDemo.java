package core;

import java.util.HashSet;
import java.util.TreeMap;

/**
 * @author yidxue
 */
public class TreeMapDemo {

    public static void main(String[] args) {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "a");
        treeMap.put(3, "b");
        treeMap.put(2, "c");
        treeMap.put(-2, "c");
        treeMap.put(20, "c");

        System.out.println(treeMap.toString());

        for (int key : treeMap.keySet()) {
            System.out.println(key + "->" + treeMap.get(key));
        }
    }
}

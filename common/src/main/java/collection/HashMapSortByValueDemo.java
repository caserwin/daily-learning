package collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yidxue
 */
public class HashMapSortByValueDemo {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 3);
        map.put("c", 1);
        map.put("b", 2);

        List<Map.Entry<String, Integer>> listData = new ArrayList<>(map.entrySet());

        listData.sort((o1, o2) -> {
            //o1 to o2升序   o2 to o1降序
            return o2.getValue().compareTo(o1.getValue());
        });

        for (Map.Entry<String, Integer> en : listData) {
            System.out.println(en);
        }
    }
}

package stream;

import java.util.HashMap;

/**
 * @author yidxue
 */
public class MapStreamFilter {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        map.entrySet().stream().forEach(e -> System.out.println(e.getKey() + "\t" + e.getValue()));
        map.forEach((key, value) -> System.out.println(key + "\t" + value));
    }
}

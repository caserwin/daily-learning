package stream.lambda;

import java.util.HashMap;

/**
 * @author yidxue
 */
public class MapLambdaDemo {
    public static void main(String[] args) {
        HashMap<String, String> hmap = new HashMap<>();
        hmap.put("a", "1");
        hmap.put("b", "2");

        hmap.forEach((key, value) -> System.out.println(key + "," + value));
    }
}

package core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: Erwin
 * Date: 17/12/30 下午3:22
 * Conclusion:
 * 1 hashmap可以get不存在的key，返回null.
 * 2 Hashmap new的同时，添加key-value写法
 */
public class JavaHashMapDemo {

    public static void main(String[] args) {
        HashMap<String, Integer> hashMap = new LinkedHashMap<>();
        // get None Key
        hashMap.put("a", 1);
        System.out.println(hashMap.get("b"));

        //初始化Map
        Map<String, String> map = new HashMap<String, String>() {{
            put("key1", "value1");
            put("key2", "value2");
            put("keyN", "valueN");
        }};

        System.out.println(map.get("keyN"));

        HashMap<String, Integer> hm = new HashMap<>();
        HashMap<String, Integer> hm1 = new HashMap<>();
        hm1.put("key1", hm.get("aa"));
        hm1.put("key2", hm.get("bb"));
        hm1.put("key3", hm.get("cc"));

        for (String key : hm1.keySet()) {
            System.out.println(key + "-->" + hm1.get(key));
        }
    }
}

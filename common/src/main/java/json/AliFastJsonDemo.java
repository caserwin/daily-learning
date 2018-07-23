package json;

import com.alibaba.fastjson.JSON;
import json.bean.BuildBean;
import json.bean.Group;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yidxue
 */
public class AliFastJsonDemo {
    public static void main(String[] args) {

        // class to json
        String jsonString = JSON.toJSONString(BuildBean.basicFunc());
        System.out.println("class to json:");
        System.out.println(jsonString);
        System.out.println("=============================");

        // json to class
        Group group = JSON.parseObject(jsonString, Group.class);
        System.out.println("json to class:");
        System.out.println(group.toString());
        System.out.println("=============================");

        // get json specified key
        System.out.println("get json value:");
        System.out.println(JSON.parseObject(jsonString).get("user"));
        System.out.println("=============================");

        // map to json
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("name", "erwin");
        dataMap.put("gender", "male");
        String str = JSON.toJSONString(dataMap);
        System.out.println("map to json:");
        System.out.println(str);
        System.out.println("=============================");

        // json to map
        Map map = JSON.parseObject(jsonString, Map.class);
        System.out.println("json to map:");
        System.out.println(map);
        System.out.println(map instanceof HashMap);
        System.out.println("=============================");

        // multi json, 获得指定的多层嵌套key
        String json = "{ \"message\": {\"this\": 174065352, \"that\":\"ddd\"} }";
        System.out.println("message.this:" + JSON.parseObject(JSON.parseObject(json).get("message").toString()).get("this"));
        System.out.println("message.that:" + JSON.parseObject(JSON.parseObject(json).get("message").toString()).get("that"));

    }
}

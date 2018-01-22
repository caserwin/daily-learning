package json;

import com.alibaba.fastjson.JSON;
import json.bean.Group;
import json.bean.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yidxue
 * very good , this code can meet my json task need
 */
public class AliFastJsonDemo {
    public static void main(String[] args) {

        // class to json
        String jsonString = basicFunc();
        System.out.println(jsonString);

        // json to class
        Group group = JSON.parseObject(jsonString, Group.class);
        System.out.println(group.getId() + "->" + group.getName() + "->" + group.getUser());

        // get json specified key
        System.out.println(JSON.parseObject(jsonString).get("user"));

        // map to json
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("name", "erwin");
        dataMap.put("gender", "male");
        String str = JSON.toJSONString(dataMap);
        System.out.println(str);

        // json to map
        Map map = JSON.parseObject(jsonString, Map.class);
        System.out.println(map);

        // multi json
        String json = "{ \"message\": {\"this\": 174065352} }";
        System.out.println(JSON.parseObject(JSON.parseObject(json).get("message").toString()).get("this"));

    }

    private static String basicFunc() {
        Group group = new Group();
        group.setId(0L);
        group.setName("admin");

        User guestUser = new User();
        guestUser.setId(2L);
        guestUser.setName("guest");

        User rootUser = new User();
        rootUser.setId(3L);
        rootUser.setName("root");

        group.addUser(guestUser);
        group.addUser(rootUser);

        return JSON.toJSONString(group);
    }
}

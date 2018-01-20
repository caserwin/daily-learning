package json;

import com.alibaba.fastjson.JSON;
import json.bean.Group;
import json.bean.User;

/**
 * @author yidxue
 */
public class AliFastJsonDemo {
    public static void main(String[] args) {
        String jsonString = basicFunc();

        System.out.println(jsonString);
        // jsonString 转类
        Group group = JSON.parseObject(jsonString, Group.class);

        System.out.println(group.getId() + "->" + group.getName() + "->" + group.getUser());
        // 获得jsonString 指定key
        System.out.println(JSON.parseObject(jsonString).get("name"));
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

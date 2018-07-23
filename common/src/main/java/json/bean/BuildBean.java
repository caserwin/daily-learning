package json.bean;

/**
 * Created by yidxue on 2018/7/22
 */
public class BuildBean {

    public static Group basicFunc() {
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

        return group;
    }
}

package serializable;

import java.io.Serializable;

/**
 * Created by yidxue on 2018/6/26
 */
public class Person implements Serializable {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private int id;

    public static String uid;

    public Person(int id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id + "\t" + this.name + "\t" + uid;
    }
}

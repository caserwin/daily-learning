package jdbc;

/**
 * Created by yidxue on 2018/6/28
 */
public class PersonRecord {
    /**
     * id号
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private String age;
    /**
     * 性别
     */
    private String gender;

    public PersonRecord(String id, String name, String age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public static String[] getAttributes() {
        return new String[]{"id", "name", "age", "gender"};
    }

    @Override
    public String toString() {
        return this.id + "\t" + this.name + "\t" + this.age + "\t" + this.gender;
    }
}

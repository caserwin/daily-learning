package jdbc.bean;

/**
 * Created by yidxue on 2018/6/30
 */
public class PersonRecord {
    /**
     * id号
     */
    private int id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
    /**
     * 性别
     */
    private String gender;

    public PersonRecord(int id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public static String[] getAttributes() {
        return new String[]{"id int", "name string", "age string", "gender string"};
    }

    public static String[] getPrimaryKey() {
        return new String[]{"id", "name"};
    }

    @Override
    public String toString() {
        return this.id + "\t" + this.name + "\t" + this.age + "\t" + this.gender;
    }
}

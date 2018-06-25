package core.hashandequals;

/**
 * User: Erwin
 * Date: 17/12/30 下午12:55
 * Description:
 */
public class Person1 {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private int id;

    public Person1(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public static void main(String[] args) {
        Person1 p1 = new Person1(12345, "张三");
        Person1 p2 = new Person1(12345, "张三三");

        System.out.println(p1.equals(p2));
    }
}

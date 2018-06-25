package core.hashandequals;

/**
 * User: Erwin
 * Date: 17/12/30 下午12:55
 * Description:
 */
public class Person2 {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private int id;

    public Person2(int id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person2) {
            Person2 p = (Person2) obj;
            if (this.id == p.id) {
                return true;
            }
        }
        return super.equals(obj);
    }

    public static void main(String[] args) {
        Person2 p1 = new Person2(12345, "张三");
        Person2 p2 = new Person2(12345, "张三三");

        System.out.println(p1.equals(p2));
    }
}
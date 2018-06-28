package hashandequals;

/**
 * User: Erwin
 * Date: 17/12/30 下午12:55
 * Description:
 */
public class Person3 {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private int id;

    public Person3(int id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person3) {
            Person3 p = (Person3) obj;
            if (this.id == p.id) {
                return true;
            }
        }
        return super.equals(obj);
    }

    public static void main(String[] args) {
        Person3 p1 = new Person3(12345, "张三");
        Person3 p2 = new Person3(12345, "张三三");

        System.out.println(p1.equals(p2));

        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
    }
}

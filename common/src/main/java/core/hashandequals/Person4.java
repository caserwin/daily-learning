package core.hashandequals;

import java.util.HashSet;

/**
 * User: Erwin
 * Date: 17/12/30 下午12:55
 * Description:
 */
public class Person4 {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private int id;

    public Person4(int id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person4) {
            Person4 p = (Person4) obj;
            if (this.id == p.id) {
                return true;
            }
        }
        return super.equals(obj);
    }

    public static void main(String[] args) {
        Person4 p1 = new Person4(12345, "张三");
        Person4 p2 = new Person4(12345, "张三三");

        HashSet<Person4> pSet = new HashSet<>();
        pSet.add(p1);
        System.out.println(pSet.contains(p2));
    }
}
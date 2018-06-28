package hashandequals;

import java.util.HashSet;

/**
 * User: Erwin
 * Date: 17/12/30 下午12:55
 * Description:
 */
public class Person5 {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private int id;

    public Person5(int id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person5) {
            Person5 p = (Person5) obj;
            if (this.id == p.id) {
                return true;
            }
        }
        return super.equals(obj);
    }

    public static void main(String[] args) {
        Person5 p1 = new Person5(12345, "张三");
        Person5 p2 = new Person5(12345, "张三三");

        HashSet<Person5> pSet = new HashSet<>();
        pSet.add(p1);
        System.out.println(pSet.contains(p2));
        System.out.println(p1.equals(p2));

//        System.out.println(Double.doubleToLongBits(11));
//        System.out.println(Double.doubleToLongBits(10));
//        System.out.println(Double.doubleToLongBits(123.456));
//        System.out.println(Double.doubleToLongBits(123.45));
//
//
//        System.out.println(("str" + "ss").hashCode());
//        System.out.println("ssstr".hashCode());
    }
}

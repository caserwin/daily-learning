package reflection;

/**
 * Created by yidxue on 2018/7/2
 * 通过反射获取类的属性。
 * 注意： 没有权限访问的属性是获取不到的。
 */
public class Demo5 {
    public static void main(String[] args){
        try {
            // 通过实例对象获得非静态属性的值。
            Service1 s1 =new Service1();
            System.out.println(Service1.class.getField("col1").get(s1));

            // 读取静态属性值
            System.out.println(Service1.class.getField("col2").get(Service1.class));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

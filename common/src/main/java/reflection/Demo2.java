package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author yidxue
 */
public class Demo2 {
    public static void main(String[] args) throws Exception {
        String className = "reflection.Service1";
        String methodName = "doService1";

        // 根据类名创建类对象
        Class<?> cls = Class.forName(className);

        System.out.println("==============一般无参方法==============");
        // 根据方法名获取方法
        Method m = cls.getMethod(methodName, int.class);
        Constructor<?> c = cls.getConstructor();
        Object obj = c.newInstance();
        m.invoke(obj, 1);

        System.out.println("==============static有参方法==============");
        Method m2 = cls.getMethod("doService2", Integer.class);
        m2.invoke(null, 100);
    }
}

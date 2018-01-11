package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author yidxue
 */
public class Demo1 {
    public static void main(String[] args) throws Exception {

        String className = "reflection.Service1";
        String methodName ="doService1";

        // 根据类名创建类对象
        Class<?> cls = Class.forName(className);
        // 根据方法名获取方法
        Method m =cls.getMethod(methodName, int.class);
        // 获取构造器
        Constructor<?> c = cls.getConstructor();
        // 根据构造器，实例化出对象
        Object obj = c.newInstance();
        // 调用对象的指定方法
        m.invoke(obj, 1);
    }
}

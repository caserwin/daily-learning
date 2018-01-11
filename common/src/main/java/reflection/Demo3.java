package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author yidxue
 */
public class Demo3 {
    public static void main(String[] args) throws Exception {
        String className = "reflection.Service3";
        String methodName ="doService3";

        Class<?> cls = Class.forName(className);
        Constructor<?> cons = cls.getConstructor(int.class);
        Object obj = cons.newInstance(11);

        Method m =cls.getMethod(methodName);

        m.invoke(obj);
    }
}

package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author yidxue
 */
public class Demo4 {
    public static void main(String[] args) throws Exception {
        String className = "reflection.Service2";
        String methodName ="doService2";

        Class<?> cls = Class.forName(className);
        Constructor<?> cons = cls.getConstructor(int.class);
        Object obj = cons.newInstance(11);

        Method m =cls.getMethod(methodName);

        m.invoke(obj);
    }
}

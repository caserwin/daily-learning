package reflection;

import java.lang.reflect.Method;

/**
 * @author yidxue
 */
public class Demo3 {

    public static void main(String[] args) throws Exception {
        String methodName = "doService1";
        Method testMethod = reflection.Service1.class.getMethod(methodName, int.class);
        testMethod.invoke(new Service1(), 100);
    }
}

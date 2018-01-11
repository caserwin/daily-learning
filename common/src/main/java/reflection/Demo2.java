package reflection;

import java.lang.reflect.Method;

/**
 * @author yidxue
 */
public class Demo2 {


    public static void main(String[] args) throws Exception {
        String methodName = "dealService1";
        Method testMethod = reflection.Service1.class.getMethod(methodName, int.class);
        testMethod.invoke(new Service1(), 100);
    }
}

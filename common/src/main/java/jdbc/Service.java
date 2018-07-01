package jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yidxue on 2018/7/1
 */
public class Service {

    public static String[] getCols(String className) {
        String[] cols = null;
        try {
            Class<?> cls = Class.forName("jdbc.bean."+className);
            Method m2 = cls.getMethod("getAttributes");
            cols = (String[]) m2.invoke(null);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return cols;
    }
}

package generics;

import generics.bean.Group;
import java.lang.reflect.Method;

/**
 * Created by yidxue on 2018/7/1
 */
public class GenericsTypeDemo2 {

    private static <T> void parser(Class<T> clazz) throws Exception {
//        Class<?> cls = Class.forName(clazz.getTypeName());
        Method m2 = clazz.getMethod("setDepart");
        System.out.println(m2.invoke(null));
    }

    public static void main(String[] args) throws Exception {
        parser(Group.class);
    }
}

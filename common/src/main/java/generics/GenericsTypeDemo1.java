package generics;

import generics.bean.BaseGroup;
import generics.bean.Group;
import java.lang.reflect.Constructor;

/**
 * @author yidxue
 */
public class GenericsTypeDemo1 {

    private static <T> T parser(Class<T> clazz) throws Exception {
//        Class<?> cls = Class.forName(clazz.getTypeName());
        // 获取构造器
        Constructor<?> c = clazz.getConstructor();
        // 根据构造器，实例化出对象
        return (T) c.newInstance();
    }

    public static void main(String[] args) throws Exception {
        // 父类用子类实例化
        BaseGroup basegroup = parser(Group.class);
        System.out.println(basegroup.toString());
    }
}

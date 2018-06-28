package reflection;

import java.util.Objects;

/**
 * @author yidxue
 */
public class GetResourceDemo {
    public static void main(String[] args){
        System.out.println(GetResourceDemo.class.getResource(".").getPath());
        System.out.println(Objects.requireNonNull(GetResourceDemo.class.getClassLoader().getResource(".")).getPath());
    }
}

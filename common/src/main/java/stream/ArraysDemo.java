package stream;

import java.util.Arrays;

/**
 * Created by yidxue on 2018/2/4
 *
 *  Java Array Stream Demo
 */
public class ArraysDemo {
    public static void main(String[] args){

        String[] dateParts = new String[]{"year", "month", "day", "hour", "minute", "second"};

        // 字符串数组中，判断是否包含某个字符串
        System.out.println(Arrays.stream(dateParts).anyMatch("Year".toLowerCase()::equals));

        // 字符串数组中，判断是否不包含某个字符串
        System.out.println(Arrays.stream(dateParts).noneMatch("Year".toLowerCase()::equals));
    }
}

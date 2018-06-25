package stream;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * java lambda 表达式：http://orchome.com/259
 * @author yidxue
 */
public class StreamFilterDemo {
    public static void main(String[] args) {
        List<Integer> nums1 = Lists.newArrayList(1, null, 3, 4, null, 6);
        System.out.println(nums1.stream().filter(Objects::nonNull).count());

        List<Integer> nums2 = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        System.out.println(nums2.stream().filter(num -> num != 1).count());
    }
}

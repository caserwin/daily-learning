package stream;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * java lambda 表达式：http://orchome.com/259
 * @author yidxue
 */
public class StreamFilter {
    public static void main(String[] args){
        List<Integer> nums = Lists.newArrayList(1,null,3,4,null,6);
        System.out.println(nums.stream().filter(num -> num != null).count());
    }
}

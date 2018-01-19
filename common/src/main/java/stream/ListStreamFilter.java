package stream;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * java lambda 表达式：http://orchome.com/259
 * @author yidxue
 */
public class ListStreamFilter {
    public static void main(String[] args){
        List<Integer> nums = Lists.newArrayList(1,null,3,4,null,6);
        System.out.println(nums.stream().filter(num -> num != null).count());

        List<Integer> lsInt = new ArrayList<>();
        lsInt.add(1);
        lsInt.add(2);
        lsInt.add(3);
        System.out.println(lsInt.stream().filter(num -> num != null).count());
    }
}

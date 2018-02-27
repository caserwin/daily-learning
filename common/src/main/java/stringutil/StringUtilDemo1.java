package stringutil;

import org.apache.commons.lang.StringUtils;

/**
 * Created by yidxue on 2018/2/23
 */
public class StringUtilDemo1 {
    public static void main(String[] args){

        System.out.println(StringUtils.equals("user", "user"));
        System.out.println(StringUtils.equals("user1", "user"));

        System.out.println("===============================");
        System.out.println(StringUtils.isBlank(null));
        System.out.println(StringUtils.isBlank(" "));
        System.out.println(StringUtils.isBlank(""));
        System.out.println(StringUtils.isBlank("1"));
    }
}

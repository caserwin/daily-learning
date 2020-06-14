package leetcode;

import java.util.HashSet;

/**
 * User: caserwin
 * Date: 2020-06-04 20:17
 * Description:
 */
public class Issue {

    static HashSet<String> set = new HashSet<>();

    public static void main(String[] args) {
        getArr("abcde");
        for (String s : set) {
            System.out.println(s);
        }
    }

    public static String getArr(String str) {
        if (str == null || str.length() == 1) {
            return str;
        }
        char[] cs = str.toCharArray();
        for (int i = 0; i < cs.length-1; i++) {
            set.add(cs[i] + getArr(str.substring(i+1)));
        }
        return str;
    }
}

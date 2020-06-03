package leetcode;

/**
 * User: caserwin
 * Date: 2020-05-31 20:49
 * Description: 实现 strStr()
 */
public class Issue28 {

    public static void main(String[] args) {
        System.out.println(strStr("hello","ll"));
        System.out.println(strStr("aaaaa","bba"));
    }

    private static int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }
        return haystack.indexOf(needle);
    }
}

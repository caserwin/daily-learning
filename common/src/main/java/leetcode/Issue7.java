package leetcode;

/**
 * User: caserwin
 * Date: 2020-05-31 22:05
 * Description: https://leetcode-cn.com/problems/reverse-integer/
 */
public class Issue7 {
    public static void main(String[] args) {
        System.out.println(reverse(123));
        System.out.println(reverse(-123));
        System.out.println(reverse(120));

        System.out.println(reverse(1463847412));
        System.out.println(reverse(-2147483648));

    }


    public static int reverse(int x) {
        if (x == 0) {
            return x;
        }
        try {
            if (x < 0) {
                return Integer.parseInt(reverseStr(String.valueOf(x * -1))) * -1;
            } else {
                return Integer.parseInt(reverseStr(String.valueOf(x)));
            }
        } catch (Exception e) {
            return 0;
        }
    }


    private static String reverseStr(String s) {
        if (s == null || "".equals(s.trim()) || s.length() == 1) {
            return s;
        }
        return s.substring(s.length() - 1) + reverseStr(s.substring(0, s.length() - 1));
    }
}

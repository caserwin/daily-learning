package leetcode;

/**
 * User: caserwin
 * Date: 2020-05-31 17:03
 * Description:
 */
public class Issue9 {
    public static void main(String[] args) {
        System.out.println(isPalindrome("123"));
        System.out.println(isPalindrome("121"));
        System.out.println(isPalindrome("11"));
        System.out.println(isPalindrome("1"));
        System.out.println(isPalindrome(""));
    }

    public static boolean isPalindrome(String str) {
        boolean is = true;
        if (str == null || str.length() == 0) {
            return is;
        }

        int start = 0;
        int end = str.length() - 1;

        char[] cs = str.toCharArray();
        while (end > start) {
            if (cs[end] != cs[start]) {
                is = false;
                break;
            }
            end--;
            start++;
        }
        return is;
    }
}

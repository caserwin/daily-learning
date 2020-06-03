package leetcode;

/**
 * 最长公共前缀
 */
public class Issue14 {
    public static void main(String[] args) {

        String[] strs = {"dog","racecar","car"};
        System.out.println(longestCommonPrefix(strs));
    }

    private static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String prefix = "";

        for (int i = 0; i <= strs[0].length(); i++) {
            prefix = strs[0].substring(0, i);
            boolean is = ifIsCommonPrefix(strs, prefix);
            if (!is) {
                prefix = prefix.substring(0, prefix.length() - 1);
                break;
            }
        }
        return prefix;
    }

    private static boolean ifIsCommonPrefix(String[] strs, String prefix) {
        boolean is = true;
        for (String str : strs) {
            if (!str.startsWith(prefix)) {
                is = false;
            }
        }
        return is;
    }
}

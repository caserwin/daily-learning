package leetcode;

/**
 * User: caserwin
 * Date: 2020-05-31 16:42
 * Description: 无重复字符的最长子串
 */
public class Issue3 {
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(lengthOfLongestSubstring("bbbbb"));
        System.out.println(lengthOfLongestSubstring("pwwkew"));
        System.out.println(lengthOfLongestSubstring("ab"));
        System.out.println(lengthOfLongestSubstring("a"));
        System.out.println(lengthOfLongestSubstring("aab"));
    }

    public static int lengthOfLongestSubstring(String s) {
        int start = 0;
        int end = 0;
        int maxLength = Integer.MIN_VALUE;
        char[] cs = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            String subStr = s.substring(start, end);
            if (!subStr.contains(String.valueOf(cs[i]))) {
                end++;
            } else {
                maxLength = end - start > maxLength ? end - start : maxLength;
                start++;
            }
        }
        maxLength = end - start > maxLength ? end - start : maxLength;
        return maxLength;
    }
}

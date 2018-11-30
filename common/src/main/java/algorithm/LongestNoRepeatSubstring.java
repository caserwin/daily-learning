package algorithm;

import java.util.HashMap;

/**
 * Created by yidxue on 2018/11/29
 * 即给定一个字符串，返回最长的没有重复字符的子串的长度
 */
public class LongestNoRepeatSubstring {

    public static void main(String[] args) {
        String str = "abcstrestr";
        System.out.println(lengthOfLongestSubstring(str));
        System.out.println(lengthOfLongestSubstring2(str));
    }

    /**
     * O(n)复杂度
     * 用一个hashmap把 O(n^2)复杂度降低到 O(n)复杂度
     */
    public static int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0;
        for (int i = 0, j = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                j = Math.max(j, map.get(s.charAt(i)) + 1);
//                j = map.get(s.charAt(i)) + 1;
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - j + 1);
        }

        return max;
    }

    /**
     * O(n^2)复杂度
     * 子串的开头可能是字符串中任一子串的位置
     */
    public static int lengthOfLongestSubstring2(String s) {
        int max = 0;
        String substr;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                substr = s.substring(i, j);
                if (substr.contains(String.valueOf(s.charAt(j)))) {
                    max = substr.length() > max ? substr.length() : max;
                    break;
                }
            }
        }
        return max;
    }
}

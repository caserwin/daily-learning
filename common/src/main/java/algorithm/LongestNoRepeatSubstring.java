package algorithm;

import java.util.HashMap;

/**
 * Created by yidxue on 2018/11/29
 * 即给定一个字符串，返回最长的没有重复字符的子串的长度
 */
public class LongestNoRepeatSubstring {

    public static void main(String[] args) {
        String str = "abcstrrestr";
        System.out.println(lengthOfLongestSubstring(str));
        System.out.println(lengthOfLongestSubstring1(str));
        System.out.println(lengthOfLongestSubstring2(str));
    }

    /**
     * O(n)复杂度，用一个hashmap把 O(n^2)复杂度降低到 O(n)复杂度
     * https://www.jianshu.com/p/c43f2c9eaf16
     */
    private static int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0;
        for (int i = 0, j = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                j = Math.max(j, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - j + 1);
        }

        return max;
    }

    /**
     * O(n)复杂度，用一个hashmap把 O(n^2)复杂度降低到 O(n)复杂度
     */
    private static int lengthOfLongestSubstring1(String str) {
        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0;
        int cmax = 0;
        String csubstr;
        for (int i = 0, j = 0; i < str.length(); i++) {
            csubstr = str.substring(j, i);
            if (!csubstr.contains(String.valueOf(str.charAt(i)))) {
                cmax += 1;
                map.put(str.charAt(i), i);
            } else {
                j = map.get(str.charAt(i)) + 1;
                max = Math.max(max, cmax);
                cmax = i - j + 1;
                map.put(str.charAt(i), i);
            }
        }
        return max;
    }


    /**
     * O(n^2)复杂度，子串的开头可能是字符串中任一位置的字符，遍历所有可能
     */
    private static int lengthOfLongestSubstring2(String s) {
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

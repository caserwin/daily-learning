package algorithm;

/**
 * Created by yidxue on 2018/11/26
 * https://caserwin.gitbooks.io/data_development/content/317.html
 */
public class LongestCommonSubstring {
    public static void main(String[] args) {
        String str1 = "werwinww";
        String str2 = "er1win11";
        System.out.println("子串长度：" + getLongestCommonSubstring(str1, str2));
    }

    public static int getLongestCommonSubstring(String str1, String str2) {
        if (str1.length() == 0 || str2.length() == 0) {
            return -1;
        }

        int maxLen = 0;
        int x = 0;
        char[] array1 = str1.toCharArray();
        char[] array2 = str2.toCharArray();
        int[][] matrix = new int[array1.length][array2.length];

        for (int i = 0; i < array1.length; i++) {
            for (int j = 0; j < array2.length; j++) {
                if (array1[i] == array2[j]) {
                    if (i == 0 || j == 0) {
                        matrix[i][j] = 1;
                    } else {
                        matrix[i][j] = matrix[i - 1][j - 1] + 1;
                    }
                }

                if (matrix[i][j] > maxLen) {
                    maxLen = matrix[i][j];
                    x = i;
                }
            }
        }

        System.out.println("Common Substring is: " + str1.substring(x - maxLen + 1, x + 1));
        return maxLen;
    }
}

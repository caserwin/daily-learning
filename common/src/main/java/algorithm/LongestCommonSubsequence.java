package algorithm;

/**
 * Created by yidxue on 2018/11/26
 * https://caserwin.gitbooks.io/data_development/content/318.html
 */
public class LongestCommonSubsequence {
    public static void main(String[] args) {
        String str1 = "werwinw1w";
        String str2 = "er1win11";
        System.out.println("子序列长度：" + getLongestCommonSubsequence(str1, str2));
    }

    public static int getLongestCommonSubsequence(String str1, String str2) {
        if (str1.length() == 0 || str2.length() == 0) {
            return -1;
        }

        char[] array1 = str1.toCharArray();
        char[] array2 = str2.toCharArray();
        int[][] matrix = new int[array1.length + 1][array2.length + 1];

        for (int i = 1; i < array1.length + 1; i++) {
            for (int j = 1; j < array2.length + 1; j++) {
                if (array1[i - 1] == array2[j - 1]) {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                } else {
                    matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
                }
            }
        }

        return matrix[array1.length][array2.length];
    }
}
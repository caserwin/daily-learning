package algorithm;

import algorithm.util.Tool;

/**
 * Created by yidxue on 2018/11/25
 * https://caserwin.gitbooks.io/data_development/content/315.html
 */
public class LevenshteinDistance {
    public static void main(String[] args) {
        String str1 = "faili";
        String str2 = "sai";
        System.out.println("编辑距离是：" + editDistance(str1, str2));
    }

    private static int editDistance(String str1, String str2) {
        if (str1.length() == 0 || str2.length() == 0) {
            return -1;
        }

        char[] array1 = str1.toCharArray();
        char[] array2 = str2.toCharArray();
        int[][] matrix = new int[array1.length + 1][array2.length + 1];

        for (int i = 0; i < array1.length + 1; i++) {
            for (int j = 0; j < array2.length + 1; j++) {
                if (i == 0) {
                    matrix[i][j] = j;
                    continue;
                }

                if (j == 0) {
                    matrix[i][j] = i;
                    continue;
                }

                if (array1[i - 1] != array2[j - 1]) {
                    matrix[i][j] = Math.min(Math.min(matrix[i - 1][j], matrix[i][j - 1]), matrix[i - 1][j - 1]) + 1;
                } else {
                    matrix[i][j] = matrix[i - 1][j - 1];
                }
            }
        }

//        Tool.printMatrix(matrix);
        return matrix[array1.length - 1][array2.length - 1];
    }
}

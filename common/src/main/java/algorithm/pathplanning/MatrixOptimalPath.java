package algorithm.pathplanning;

/**
 * Created by yidxue on 2018/11/30
 */
public class MatrixOptimalPath {
    private final static String MAX = "max";
    private final static String MIN = "min";

    public static void main(String[] args) {
        int[][] dist = new int[][]{
            {3, 1, 3, 0},
            {0, 1, 0, 2},
            {4, 0, 3, 1},
            {0, 1, 2, 2}
        };

        System.out.println(getOptimalPath(dist, "max"));
        System.out.println(getOptimalPath(dist, "min"));
    }

    private static int getOptimalPath(int[][] dist, String type) {
        if (dist == null || dist.length == 0 || dist[0].length == 0) {
            return 0;
        }

        int rows = dist.length;
        int cols = dist[0].length;
        int[][] dp = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 && j == 0) {
                    dp[0][0] = dist[0][0];
                    continue;
                }

                if (i == 0) {
                    dp[i][j] = dp[i][j - 1] + dist[i][j];
                    continue;
                }

                if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + dist[i][j];
                    continue;
                }

                dp[i][j] = type.equals(MAX) ? Math.max(dp[i][j - 1], dp[i - 1][j]) + dist[i][j] : Math.min(dp[i][j - 1], dp[i - 1][j]) + dist[i][j];
            }
        }
        return dp[rows - 1][cols - 1];
    }
}

package algorithm.pathplanning;

import algorithm.util.Tool;

/**
 * Created by yidxue on 2018/11/29
 */
public class MinRoutePlanPlus {

    private final static String MAX = "max";
    private final static String MIN = "min";

    public static void main(String[] args) {
        int[][] dist = new int[][]{
            {0, 3, 1, 3, 0},
            {0, 0, 1, 0, 2},
            {0, 0, 0, 1, 4},
            {0, 0, 1, 0, 2},
            {0, 0, 0, 0, 0}
        };
        MinRoutePlanPlus mrp = new MinRoutePlanPlus();

        System.out.println(mrp.getBestPath(dist, "max"));
        System.out.println(mrp.getBestPath(dist, "min"));

        Node maxNode = mrp.getBestPathNode(dist, "max");
        System.out.println(Tool.getNodePath(maxNode));

        Node minNode = mrp.getBestPathNode(dist, "min");
        System.out.println(Tool.getNodePath(minNode));

    }

    public int getBestPath(int[][] dist, String type) {
        int[] dp = new int[dist.length];

        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist[i].length; j++) {
                if (dist[i][j] != 0) {
                    int d = dp[i] + dist[i][j];
                    if (dp[j] == 0) {
                        dp[j] = d;
                        continue;
                    }

                    if (dp[j] < d && MAX.equals(type.toLowerCase())) {
                        dp[j] = d;
                    }

                    if (dp[j] > d && MIN.equals(type.toLowerCase())) {
                        dp[j] = d;
                    }
                }
            }
        }
        return dp[dist.length - 1];
    }


    public Node getBestPathNode(int[][] dist, String type) {
        Node[] dp = new Node[dist.length];

        for (int i = 0; i < dist.length; i++) {
            if (i == 0) {
                dp[0] = new Node(0, 0, null);
            }

            for (int j = 0; j < dist[i].length; j++) {
                if (dist[i][j] != 0) {
                    int d = dp[i].value + dist[i][j];

                    if (dp[j] == null) {
                        dp[j] = new Node(j, d, dp[i]);
                        continue;
                    }

                    if (dp[j].value < d && MAX.equals(type.toLowerCase())) {
                        dp[j] = new Node(j, d, dp[i]);
                    }

                    if (dp[j].value > d && MIN.equals(type.toLowerCase())) {
                        dp[j] = new Node(j, d, dp[i]);
                    }
                }
            }
        }
        return dp[dist.length - 1];
    }
}

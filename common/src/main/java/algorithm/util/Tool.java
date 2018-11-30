package algorithm.util;

import algorithm.pathplanning.Node;

/**
 * Created by yidxue on 2018/11/30
 */
public class Tool {
    public static String getNodePath(Node node) {
        if (node.parent == null) {
            return node.id + ":" + node.value;
        }
        return getNodePath(node.parent) + "-->" + node.id + ":" + node.value;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] aMatrix : matrix) {
            for (int anAMatrix : aMatrix) {
                System.out.print(anAMatrix + "\t");
            }
            System.out.println();
        }
    }
}

package leetcode;

import lombok.Data;

/**
 * User: caserwin
 * Date: 2020-06-02 22:31
 * Description: https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/submissions/
 */
public class Issue111 {
    @Data
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {

        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(9);
        TreeNode node3 = new TreeNode(20);
        TreeNode node4 = new TreeNode(15);
        TreeNode node5 = new TreeNode(7);

        node1.setLeft(node2);
        node1.setRight(node3);

        node3.setLeft(node4);
        node3.setRight(node5);

        System.out.println(minDepth(node1));
    }


    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return getDeep(root, 1);
    }

    private static int getDeep(TreeNode root, int deep) {
        if (root.left == null && root.right == null) {
            return deep;
        }

        int rdeep = root.right != null ? getDeep(root.right, deep + 1) : deep;
        int ldeep = root.left != null ? getDeep(root.left, deep + 1) : deep;

        return rdeep < ldeep ? rdeep : ldeep;
    }


//    private static int getDeep(TreeNode root, int minDeep) {
//        if (root.left == null && root.right == null) {
//            return minDeep;
//        }
//
//        int rdeep = root.right != null ? getDeep(root.right, deep + 1) : deep;
//        int ldeep = root.left != null ? getDeep(root.left, deep + 1) : deep;
//
//        return rdeep < ldeep ? rdeep : ldeep;
//    }
}

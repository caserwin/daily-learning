package leetcode;

import leetcode.pojo.TreeNode;

/**
 * User: caserwin
 * Date: 2020-06-02 22:31
 * Description: https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/submissions/
 */
public class Issue104 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(0);

        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(1);
        TreeNode node3 = new TreeNode(2);
        TreeNode node4 = new TreeNode(2);

        root.setLeft(node1);
        root.setRight(node2);
        node1.setLeft(node3);
        node1.setRight(node4);

        System.out.println(maxDepth(root));
    }

//    public static int maxDepth(TreeNode root) {
//        if (root == null) {
//            return 0;
//        }
//
//        return 1 + maxDepth(root.right) > 1 + maxDepth(root.left) ? 1 + maxDepth(root.right) : 1 + maxDepth(root.left);
//    }

    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int rdeep = maxDepth(root.right);
        int ldeep = maxDepth(root.left);

        return rdeep > ldeep ? 1 + rdeep : 1 + ldeep;
    }
}

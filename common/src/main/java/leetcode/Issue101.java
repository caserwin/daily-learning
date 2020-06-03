package leetcode;

import lombok.Data;

/**
 * User: caserwin
 * Date: 2020-05-31 16:28
 * Description:
 */
public class Issue101 {
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
        TreeNode root = new TreeNode(0);

        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(1);
        TreeNode node3 = new TreeNode(2);
        TreeNode node4 = new TreeNode(2);

        root.setLeft(node1);
        root.setRight(node2);
        node1.setLeft(node3);

//        node2.setLeft(node4); // 返回 false
        node2.setRight(node4);  // 返回 true

        System.out.println(isSymmetric(root));
    }


    public static boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isMirror(root.left, root.right);
    }

    private static boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        return (t1.val == t2.val)
                && isMirror(t1.right, t2.left)
                && isMirror(t1.left, t2.right);
    }
}

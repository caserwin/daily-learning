package leetcode;

import leetcode.pojo.TreeNode;

/**
 * User: caserwin
 * Date: 2020-06-02 22:31
 * Description:
 */
public class Issue110 {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);

        node1.setRight(node2);
        node2.setRight(node3);

        System.out.println(isBalanced(node1));
    }


    public static boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        int diff = getDeep(root.left, 0) - getDeep(root.right, 0);
        return (Math.abs(diff) == 1 || Math.abs(diff) == 0) && isBalanced(root.left) && isBalanced(root.right);
    }

    private static int getDeep(TreeNode root, int deep) {
        if (root == null) {
            return deep;
        }
        if (root.right == null && root.left == null) {
            return deep + 1;
        }
        int rdeep = root.right != null ? getDeep(root.right, deep + 1) : deep;
        int ldeep = root.left != null ? getDeep(root.left, deep + 1) : deep;

        return Math.max(rdeep, ldeep);
    }
}

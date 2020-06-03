package leetcode.pojo;

import lombok.Data;

/**
 * User: caserwin
 * Date: 2020-06-03 09:10
 * Description:
 */
@Data
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int x) {
        val = x;
    }
}

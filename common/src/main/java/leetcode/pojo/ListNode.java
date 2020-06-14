package leetcode.pojo;

import lombok.Data;

/**
 * User: caserwin
 * Date: 2020-06-14 16:35
 * Description:
 */
@Data
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }
}

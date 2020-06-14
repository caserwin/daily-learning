package leetcode;

import leetcode.pojo.ListNode;

/**
 * User: caserwin
 * Date: 2020-05-31 17:20
 * Description: 反转链表
 */
public class Issue206 {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        System.out.println(node1);
        System.out.println(reverseList(node1));

    }

    public static ListNode reverseList(ListNode node) {
        if (node == null || node.next == null) {
            return node;
        }

        ListNode cur = node;
        ListNode next = cur.next;
        cur.next = null;

        while (next != null) {
            ListNode tmp = next.next;
            next.next = cur;

            cur = next;
            next = tmp;
        }
        return cur;
    }
}

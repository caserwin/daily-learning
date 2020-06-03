package leetcode;

import lombok.Data;

/**
 * User: caserwin
 * Date: 2020-05-31 20:56
 * Description:
 */
public class Issue21 {
    @Data
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(4);
        ListNode node4 = new ListNode(1);
        ListNode node5 = new ListNode(3);
        ListNode node6 = new ListNode(4);

        node1.next = node2;
        node2.next = node3;

        node4.next = node5;
        node5.next = node6;

        System.out.println(node1);
        System.out.println(node4);
        System.out.println(mergeTwoLists(node1, node4));
    }

    private static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode resNode = new ListNode(-1);
        ListNode headNode = resNode;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                resNode.next = l1;
                l1 = l1.next;
            } else {
                resNode.next = l2;
                l2 = l2.next;
            }

            resNode = resNode.next;
        }

        // 把没遍历完的加上
        resNode.next = l1 == null ? l2 : l1;

        return headNode.next;
    }
}

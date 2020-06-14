package leetcode;


import leetcode.pojo.ListNode;

/**
 * User: caserwin
 * Date: 2020-05-31 21:29
 * Description: 移除链表元素
 */
public class Issue203 {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(6);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;

        System.out.println(node1);
        System.out.println(removeElements(node1, 6));

    }

    private static ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return head;
        }

        ListNode pre = head;
        ListNode cur = pre.next;

        while (cur != null) {
            if (cur.val == val) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        if (head.val == val) {
            return head.next;
        } else {
            return head;
        }
    }
}

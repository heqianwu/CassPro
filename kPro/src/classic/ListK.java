package classic;

public class ListK {


    //给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
    //输入: 0->1->2->NULL, k = 4    输出: 2->0->1->NULL
    //输入: 1->2->3->4->5->NULL, k = 2   输出: 4->5->1->2->3->NULL
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || k == 0) {
            return head;
        }
        ListNode temp = head;
        int len = 0;
        while (temp != null) {
            temp = temp.next;
            len++;
        }
        k = k % len;
        if (k == 0) {
            return head;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (k > 0) {
            k--;
            fast = fast.next;
        }
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        ListNode res = slow.next;
        slow.next = null;
        fast.next = head;
        return res;
    }
}

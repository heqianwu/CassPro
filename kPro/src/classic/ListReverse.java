package classic;

public class ListReverse {


    /**
     * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
     * <p>
     * k 是一个正整数，它的值小于或等于链表的长度。
     * <p>
     * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     * <p>
     * 示例 :
     * <p>
     * 给定这个链表：1->2->3->4->5
     * <p>
     * 当 k = 2 时，应当返回: 2->1->4->3->5
     * <p>
     * 当 k = 3 时，应当返回: 3->2->1->4->5
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        int count = 0;
        ListNode indexNode = head;
        while (indexNode != null && count != k) {
            count++;
            indexNode = indexNode.next;
        }
        if (count != k)
            return head;
        ListNode temp = head;
        ListNode tempk;
        ListNode res = new ListNode(0);
        while (count-- > 0) {
            tempk = temp.next;
            temp.next = res.next;
            res.next = temp;
            temp = tempk;
        }
        head.next = reverseKGroup(indexNode, k);
        return res.next;
    }

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        ListNode temp = head.next.next;
        head.next = swapPairs(temp);
        next.next = head;
        return next;
    }

}

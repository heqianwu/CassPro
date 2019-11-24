public class Main {
    public static void main(String[] args) {
        ListNode node1=new ListNode(1);
        ListNode node2=new ListNode(2);
        ListNode node3=new ListNode(3);
        ListNode node4=new ListNode(4);
        ListNode node5=new ListNode(5);
        node1.next=node2;
        node2.next=node3;
        node3.next=node4;
        node4.next=node5;
//        node5.next=node1;
        System.out.println("aaa");
        int res=new Main().findCycleLength(node1);
        System.out.println(res);
    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode sNode = head.next;
        ListNode tNode = head.next.next;
        sNode.next = head;
        head.next = reverseList(tNode);
        return sNode;
    }

    public boolean hasCycle(ListNode head)
    {
        if (head == null || head.next == null)
            return false;
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null)
        {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow)
                return true;
        }
        return false;
    }

    // 如果是求环的起始节点，没有环时返回null
    public ListNode detectCycle(ListNode head)
    {
        if (head == null || head.next == null)
            return null;
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null)
        {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow)
            {
                fast = head;
                while (fast != slow)
                {
                    fast = fast.next;
                    slow = slow.next;
                }
                return fast;
            }
        }
        return null;
    }

    // 求环长度，没有环时返回0
    public int findCycleLength(ListNode head)
    {
        int res = 0;
        if (head == null || head.next == null)
            return 0;
        ListNode fast = head;
        ListNode slow = head;
        ListNode beginNode;
        while (fast != null && fast.next != null)
        {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow)
            {
                fast = head;
                while (fast != slow)
                {
                    fast = fast.next;
                    slow = slow.next;
                }
                beginNode = slow;
                do
                {
                    slow = slow.next;
                    res++;
                }
                while (slow != beginNode);
                return res;
            }
        }
        return res;
    }

}
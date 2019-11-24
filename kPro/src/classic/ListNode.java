package classic;

public class ListNode {
    int val;
    ListNode next;

    public ListNode(int x) {
        val = x;
        next = null;
    }
    public ListNode(int x, ListNode node){
        val =x;
        next =node;
    }
}

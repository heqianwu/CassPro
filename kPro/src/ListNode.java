public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
    ListNode(int x,ListNode node){
        val =x;
        next =node;
    }
}

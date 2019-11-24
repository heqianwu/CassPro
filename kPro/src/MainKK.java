import java.util.HashSet;

public class MainKK {
//    public static void main(String[] args) {
//        ListNode listnode = new ListNode(2);
//        ListNode listnode1 = new ListNode(1,listnode);
//        ListNode listnode2 = new ListNode(3,listnode1);
//        ListNode listnode3 = new ListNode(2,listnode2);
//        ListNode listnode4 = new ListNode(1,listnode3);
//        ListNode listnode5 = new ListNode(3,listnode4);
//        ListNode result = new MainKK().removelist(listnode5);
//        while(result!=null){
//            System.out.println(result.val);
//            result=result.next;
//        }
//    }
//
//    public ListNode removelist(ListNode nodek) {
//        HashSet<Integer> hasValue = new HashSet<>();
//        ListNode result = new ListNode(10);
//        result.next = nodek;
//        ListNode nodeIndex = result;
//        while (nodeIndex.next != null) {
//            if (hasValue.contains(nodeIndex.next.val)) {
//                nodeIndex.next = nodeIndex.next.next;
//            } else {
//                hasValue.add(nodeIndex.next.val);
//                nodeIndex = nodeIndex.next;
//            }
//        }
//        return result.next;
//    }
}
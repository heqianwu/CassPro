package classic;

import java.util.*;

public class PriorityMultiListMerge {


    public static ListNode mergeKLists1(ArrayList<ListNode> lists) {
        if(lists==null || lists.size()==0){
            return null;
        }
        ListNode res=lists.get(0);
        for(int i=1;i<lists.size();i++){
            res=merge(res,lists.get(i));
        }
        return res;
    }

    public static ListNode merge(ListNode first, ListNode second){
        ListNode fake=new ListNode(0);
        ListNode pre=fake;
        while(first!=null && second!=null){
            if(first.val<second.val){
                pre.next=first;
                first=first.next;
            }else{
                pre.next=second;
                second=second.next;
            }
            pre=pre.next;
        }
        pre.next=(first==null)?second:first;
        return fake.next;
    }

    public static ListNode mergeKLists2(ArrayList<ListNode> lists) {
        if(lists==null||lists.size()==0)
            return null;
        ListNode lNode=new ListNode(0);
        int index=0;
        int val;
        ListNode temp;
        ListNode vNode=lNode;
        for(Iterator<ListNode> iterator = lists.iterator(); iterator.hasNext();){
            if(iterator.next()==null)
                iterator.remove();
        }
        while(lists.size()!=0){
            index=0;
            val=lists.get(0).val;
            for(int i=1;i<lists.size();i++){
                if(val>lists.get(i).val){
                    val=lists.get(i).val;
                    index=i;
                }
            }
            temp=lists.get(index).next;
            vNode.next=lists.get(index);
            vNode=vNode.next;
            vNode.next=null;
            lists.remove(vNode);
            if(temp!=null)
                lists.add(temp);
        }
        return lNode.next;
    }


    public static void main(String[] args){
        ListNode lNode=new ListNode(1);
        ListNode xNode=new ListNode(0);
        ArrayList<ListNode> lists=new ArrayList<ListNode>();
        lists.add(lNode);
        lists.add(xNode);
        lNode=mergeKLists3(lists);
        while(lNode!=null){
            System.out.print(lNode.val+"  ");
            lNode=lNode.next;
        }

    }

    public static ListNode mergeKLists3(ArrayList<ListNode> lists) {

        if(lists==null||lists.size()==0)
            return null;
        PriorityQueue<ListNode> queue=new PriorityQueue<>(lists.size(),(o1, o2) -> {
            if(o1.val<o2.val)
                return -1;
            else if(o1.val==o2.val)
                return 0;
            return 1;
        });
        ListNode xNode=new ListNode(0);
        ListNode vNode=xNode;
        for(ListNode node:lists){
            if(node!=null)
                queue.add(node);
        }
        while(!queue.isEmpty()){
            vNode.next=queue.poll();
            vNode=vNode.next;
            if(vNode.next!=null)
                queue.add(vNode.next);
        }
        return xNode.next;
    }
}

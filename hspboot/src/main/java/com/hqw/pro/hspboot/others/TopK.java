package com.hqw.pro.hspboot.others;

import java.util.Collection;
import java.util.PriorityQueue;

public class TopK<E> {
    private PriorityQueue<E> queue;
    private int k;

    public TopK(int k) {
        this.k = k;
        this.queue = new PriorityQueue<>(k);
    }

    public void addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
    }

    public void add(E e) {
        if (queue.size() < k) {
            queue.add(e);
            return;
        }
        Comparable<? super E> head = (Comparable<? super E>) queue.peek();
        if (head.compareTo(e) > 0) {
            //新元素比不上已有TopK中的最小值直接忽略
            return;
        }
        //新元素入驻TopK，将已有TopK中的堆顶最小值删除，将新元素加入
        queue.poll();
        queue.add(e);
    }

    public <T> T[] toArray(T[] a) {
        return queue.toArray(a);
    }

    public E getKth() {
        return queue.peek();
    }

    public PriorityQueue<E> getQueue() {
        return queue;
    }

    public static void main(String[] args) {
    }
}
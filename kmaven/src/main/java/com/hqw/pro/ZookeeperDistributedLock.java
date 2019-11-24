package com.hqw.pro;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


/**
 * zk使用临时节点实现分布式非公平锁；使用临时顺序节点实现分布式公平锁
 * 所有请求都去创建一个临时顺序节点，然后对所有节点进行排序，当前节点排第一，成功获得锁，否则监听前一个节点，使用CountdownLatch阻塞;
 * 当前拿到锁的节点执行完成后，删除当前节点，zookeeper通知监听的客户端，该客户端再尝试取得锁。
 */
public class ZookeeperDistributedLock implements Lock {

    private String hosts = "127.0.0.1:2181";
    private ZkClient client;
    private ThreadLocal<String> currentPath = new ThreadLocal<>();
    private ThreadLocal<String> beforePath = new ThreadLocal<>();

    public ZookeeperDistributedLock() {
        this.client = new ZkClient(hosts);  //获得客端
        this.client.setZkSerializer(new MyZkSerializer()); //设置序列化类
        //判断根节点是否存在，不存在则创建
        if (!this.client.exists(Constant.LOCKPATH)) {
            try {
                this.client.createPersistent(Constant.LOCKPATH);
            } catch (Exception e) {
                System.out.println("ZkClient create root node failed...");
                System.out.println(e);
            }
        }
    }

    public void lock() {
        //如果没有获得到锁，那么就等待，一直到获得到锁为止
        if (!tryLock()) {
            // 没有获得锁，阻塞自己
            waitForLock();
            // 再次尝试加锁
            lock();
        }
    }

    public void unlock() {
        this.client.delete(this.currentPath.get());
    }

    public boolean tryLock() {
        //当前节点为空，说明还没有线程来创建节点
        if (this.currentPath.get() == null) {
            this.currentPath.set(this.client.createEphemeralSequential(Constant.LOCKPATH + Constant.SEPARATOR, "data"));
        }
        //获取所有子节点
        List<String> children = this.client.getChildren(Constant.LOCKPATH);
        //排序
        Collections.sort(children);
        //判断当前节点是否是最小的节点
        if (this.currentPath.get().equals(Constant.LOCKPATH + Constant.SEPARATOR + children.get(0))) {
            return true;
        } else {
            //获取当前节点的位置
            int curIndex = children.indexOf(this.currentPath.get().substring(Constant.LOCKPATH.length() + 1));
            //设置前一个节点
            beforePath.set(Constant.LOCKPATH + Constant.SEPARATOR + children.get(curIndex - 1));
        }
        return false;
    }

    private void waitForLock() {
        //声明一个计数器
        final CountDownLatch cdl = new CountDownLatch(1);
        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String arg0, Object arg1) throws Exception {
            }

            @Override
            public void handleDataDeleted(String arg0) throws Exception {
                System.out.println("Ephemeral node has been deleted....");
                //计数器减一
                cdl.countDown();
            }
        };
        //完成watcher注册
        this.client.subscribeDataChanges(this.beforePath.get(), listener);
        //阻塞自己
        if (this.client.exists(this.beforePath.get())) {
            try {
                cdl.await();
            } catch (InterruptedException e) {
                System.out.println("CountDownLatch thread has been interrupted...");
                System.out.println(e);
            }
        }
        //取消注册
        this.client.unsubscribeDataChanges(this.beforePath.get(), listener);
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void lockInterruptibly() throws InterruptedException {
    }

    public Condition newCondition() {
        return null;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }
}


class DemoService {

    private static int count = 0; //生成计数器

    public void sayHello(String name) {
        ZookeeperDistributedLock lock = new ZookeeperDistributedLock();
        try {
            lock.lock();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++; //加一
            System.out.println(Thread.currentThread().getName() + " say hello to " + name + "_" + count);
        } finally {
            lock.unlock();
        }
    }
}


class DemoThread {

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

    static class DemoRun implements Runnable {

        private int i;

        public DemoRun(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                DemoService demoService = new DemoService();
                cyclicBarrier.await();
                demoService.sayHello("name_" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new DemoRun(i)).start();
        }
    }
}


class MyZkSerializer implements ZkSerializer {

    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("MyZkSerializer deserialize happened unsupportedEncodingException...");
            System.out.println(e);
            throw new ZkMarshallingError(e);
        }
    }

    public byte[] serialize(Object obj) throws ZkMarshallingError {
        try {
            return String.valueOf(obj).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("MyZkSerializer serialize happened unsupportedEncodingException...");
            System.out.println(e);
            throw new ZkMarshallingError(e);
        }
    }
}


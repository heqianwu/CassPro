package design;

import java.util.Calendar;
import java.util.concurrent.locks.ReentrantLock;


//https://www.cnblogs.com/xiaoxi/p/7651360.html
public class TestLock {

    private ReentrantLock lock = null;

    public TestLock() {
        // 创建一个自由竞争的可重入锁
        lock = new ReentrantLock();
    }

    public static void main(String[] args) {

        TestLock tester = new TestLock();

        try {
            // 测试可重入，方法testReentry() 在同一线程中,可重复获取锁,执行获取锁后，显示信息的功能
            tester.testReentry();
            // 能执行到这里而不阻塞，表示锁可重入
            tester.testReentry();
            // 再次重入
            tester.testReentry();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放重入测试的锁，要按重入的数量解锁，否则其他线程无法获取该锁。
            tester.getLock().unlock();
            tester.getLock().unlock();
            tester.getLock().unlock();

        }
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public void testReentry() {
        lock.lock();

        Calendar now = Calendar.getInstance();

        System.out.println(now.getTime() + " " + Thread.currentThread().getName()
                + " get lock.");
    }

}


//下面是锁的公平性
class Service {

    private ReentrantLock lock;

    public Service(boolean isFair) {
        lock = new ReentrantLock(isFair);
    }

    public void serviceMethod() {
        try {
            lock.lock();
            System.out.println("ThreadName=" + Thread.currentThread().getName()
                    + " 获得锁定");
        } finally {
            lock.unlock();
        }
    }
}

class Run {

    public static void main(String[] args) throws InterruptedException {
        final Service service = new Service(true);  //改为false就为非公平锁了
        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println("**线程： " + Thread.currentThread().getName()
                        + " 运行了 ");
                service.serviceMethod();
            }
        };

        Thread[] threadArray = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threadArray[i] = new Thread(runnable);
        }
        for (int i = 0; i < 10; i++) {
            threadArray[i].start();
        }
    }
}
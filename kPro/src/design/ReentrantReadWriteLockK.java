package design;


import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;


public class ReentrantReadWriteLockK {

    public static void main(String[] args) {
        MyQueue q = new MyQueue();
        new Thread(() -> q.read()).start();
        new Thread(() -> {
            q.write("helloWorld!");
        }, "MyWriteTask").start();
        for (int i = 1; i <= 100; i++) {
            new Thread(() -> q.read()).start();
        }
    }
}

class MyQueue {

    private Object obj = null;
    ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void write(Object obj) {
        rwLock.writeLock().lock();
        System.out.println("---------------");
        try {
            Thread.sleep(3000);
            this.obj = obj;
            System.out.println(Thread.currentThread().getName() + "\t" + obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void read() {
        ReadLock rlock = rwLock.readLock();
        rlock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + obj);
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rlock.unlock();
        }
    }
}


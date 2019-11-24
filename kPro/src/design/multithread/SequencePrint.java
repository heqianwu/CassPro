package design.multithread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SequencePrint {


}


class FooLockCondition {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private int state = 1;

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        lock.lock();
        try {
            while (state != 1) {
                condition.await();
            }
            printFirst.run();
            state = 2;
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.

        lock.lock();
        try {
            while (state != 2) {
                condition.await();
            }
            printSecond.run();
            state = 3;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.

        lock.lock();
        try {
            while (state != 3) {
                condition.await();
            }
            printThird.run();
            state = 1;
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }
}

class FooCountdownLatch {

    private CountDownLatch second = new CountDownLatch(1);
    private CountDownLatch third = new CountDownLatch(1);

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        second.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        second.await();
        printSecond.run();
        third.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        third.await();
        printThird.run();
    }
}

class FooSynchronized {

    private volatile int state = 1;

    public synchronized void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        while (state != 1) {
            wait();
        }
        printFirst.run();
        state = 2;
        notifyAll();
    }

    public synchronized void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        while (state != 2) {
            wait();
        }
        printSecond.run();
        state = 3;
        notifyAll();
    }

    public synchronized void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        while (state != 3) {
            wait();
        }
        printThird.run();
        state = 1;
        notifyAll();
    }
}

class FooSemaphore {

    public Semaphore sema_first_two = new Semaphore(0);
    public Semaphore sema_two_second = new Semaphore(0);

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        sema_first_two.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        sema_first_two.acquire();
        printSecond.run();
        sema_two_second.release();
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        sema_two_second.acquire();
        printThird.run();
    }
}

class FooAtomicInteger {

    private AtomicInteger state = new AtomicInteger(1);

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        while (!state.compareAndSet(1, 2)) {

        }
        printFirst.run();
        state.compareAndSet(2, 3);
    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        while (!state.compareAndSet(3, 4)) {

        }
        printSecond.run();
        state.compareAndSet(4, 5);
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        while (!state.compareAndSet(5, 6)) {

        }
        printThird.run();
        state.compareAndSet(6, 1);
    }
}
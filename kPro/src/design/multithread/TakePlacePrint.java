package design.multithread;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class TakePlacePrint {
}



class FooBarSemaphore {
    private int n;

    public FooBarSemaphore(int n) {
        this.n = n;
    }

    private Semaphore semaphoreFoo = new Semaphore(1);
    private Semaphore semaphoreBar = new Semaphore(0);

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printFoo.run() outputs "foo". Do not change or remove this line.
            semaphoreFoo.acquire();
            printFoo.run();
            semaphoreBar.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printBar.run() outputs "bar". Do not change or remove this line.
            semaphoreBar.acquire();
            printBar.run();
            semaphoreFoo.release();
        }
    }
}



class FooBarAtomicInteger {
    private int n;

    public FooBarAtomicInteger(int n) {
        this.n = n;
    }

    private final AtomicInteger state = new AtomicInteger(0);

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printFoo.run() outputs "foo". Do not change or remove this line.
            while(state.get()%2==1){
            }
            printFoo.run();
            state.getAndIncrement();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            // printBar.run() outputs "bar". Do not change or remove this line.
            while(state.get()%2==0){

            }
            printBar.run();
            state.getAndIncrement();
        }
    }
}
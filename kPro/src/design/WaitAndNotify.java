package design;

import java.util.LinkedList;


//https://www.cnblogs.com/moongeek/p/7631447.html

/**
 * 1、wait()、notify/notifyAll() 方法是Object的本地final方法，无法被重写。
 * 2、wait()使当前线程阻塞，前提是 必须先获得锁，一般配合synchronized 关键字使用，即，一般在synchronized 同步代码块里使用 wait()、notify/notifyAll() 方法。
 * 3、 由于 wait()、notify/notifyAll() 在synchronized 代码块执行，说明当前线程一定是获取了锁的。
 * 当线程执行wait()方法时候，会释放当前的锁，然后让出CPU，进入等待状态。
 * 只有当 notify/notifyAll() 被执行时候，才会唤醒一个或多个正处于等待状态的线程，然后继续往下执行，直到执行完synchronized 代码块的代码或是中途遇到wait() ，再次释放锁。
 * 也就是说，notify/notifyAll() 的执行只是唤醒沉睡的线程，而不会立即释放锁，锁的释放要看代码块的具体执行情况。所以在编程中，尽量在使用了notify/notifyAll() 后立即退出临界区，以唤醒其他线程
 * 4、wait() 需要被try catch包围，中断也可以使wait等待的线程唤醒。
 * 5、notify 和wait 的顺序不能错，如果A线程先执行notify方法，B线程在执行wait方法，那么B线程是无法被唤醒的。
 */

public class WaitAndNotify {

    //仓库最大容量
    private final int MAX_SIZE = 100;
    //仓库存储的载体
    private LinkedList list = new LinkedList();

    //生产产品
    public void produce(int num) {
        //同步
        synchronized (list) {
            //仓库剩余的容量不足以存放即将要生产的数量，暂停生产
            while (list.size() + num > MAX_SIZE) {
                System.out.println("【要生产的产品数量】:" + num + "\t【库存量】:"
                        + list.size() + "\t暂时不能执行生产任务!");

                try {
                    //条件不满足，生产阻塞
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < num; i++) {
                list.add(new Object());
            }

            System.out.println("【已经生产产品数】:" + num + "\t【现仓储量为】:" + list.size());

            //
            list.notifyAll();
        }
    }

    //消费产品
    public void consume(int num) {
        synchronized (list) {

            //不满足消费条件
            while (num > list.size()) {
                System.out.println("【要消费的产品数量】:" + num + "\t【库存量】:"
                        + list.size() + "\t暂时不能执行生产任务!");

                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //消费条件满足，开始消费
            for (int i = 0; i < num; i++) {
                list.remove();
            }

            System.out.println("【已经消费产品数】:" + num + "\t【现仓储量为】:" + list.size());

            list.notifyAll();
        }
    }

    //join要在线程启动后才有效
    public static void main(String[] args) throws InterruptedException {


        Thread1 thread1 = new Thread1("hqw");
        Thread1 thread2 = new Thread1("heqianwu");
        System.out.println("hhh");
        thread2.start();
        System.out.println("kkk");
        thread1.join();
        System.out.println("ggg");
        thread1.start();
        thread1.join();
        System.out.println("www");
    }


}


class Thread1 extends Thread {
    String name;

    public Thread1(String str) {
        name = str;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("adfkdfkj: " + name);
    }
}



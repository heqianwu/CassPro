package design;

public class WaitAndNoty {
    public static void main(String[] args) {
        try {
            boolean[] bList = new boolean[2];
            System.out.println(bList[0]);
            WaitAndNoty sl = new WaitAndNoty();
            Thread threadA = new Thread(() -> sl.methodD());
            threadA.start();
            Thread threadB = new Thread(() -> sl.methodF());
            threadB.start();
            Thread.sleep(2000);

            //wait，join，sleep等方法里面有对interrupt的检测，检测到时抛出InterruptedException，并清除中断状态
            //BlockingQueue#put、BlockingQueue#take、Object#wait、Thread#sleep、Thread#join
            //也可以在线程中自己实现检测和处理，循环标记变量/循环中断状态
            threadA.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void methodA() {
        try {
            WaitAndNoty.class.wait();
            WaitAndNoty.class.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void methodB() {
        try {
            wait();
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void methodC() {
        try {
            //IllegalMonitorStateException
            wait();
            //IllegalMonitorStateException
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void methodD() {
        try {
            System.out.println("kkk");
            wait(5000);
            System.out.println("hhh");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void methodF() {
        try {
            System.out.println("www");
            Thread.sleep(6000);
            System.out.println("sss");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

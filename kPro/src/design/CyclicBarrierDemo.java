package design;

import java.util.concurrent.*;

public class CyclicBarrierDemo {
    private static CountDownLatch endSignal = new CountDownLatch(6);
    //指定必须有6个运动员到达才行
    private static CyclicBarrier barrier = new CyclicBarrier(3, () -> {
        System.out.println("所有运动员入场，裁判员一声令下！！！！！");
    });

    public static void main(String[] args) throws InterruptedException {

        KKK();

        System.out.println("运动员准备进场，全场欢呼............");

        ExecutorService service = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 6; i++) {
            service.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 运动员，进场");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " 运动员，冲刺");
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + "  运动员到达终点");
                    endSignal.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        endSignal.await();
        System.out.println("比赛结束！");
        service.shutdown();
    }


    public static void KKK() {
        int threadNum = 5;
        CyclicBarrier barrier = new CyclicBarrier(threadNum, () ->{
                System.out.println(Thread.currentThread().getName() + " 完成最后任务");
            });

        for (int i = 0; i < threadNum; i++) {
            new TaskThread(barrier).start();
        }
    }


    static class TaskThread extends Thread {

        CyclicBarrier barrier;

        public TaskThread(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(getName() + " 到达栅栏 A");
                barrier.await();
                System.out.println(getName() + " 冲破栅栏 A");

                Thread.sleep(2000);
                System.out.println(getName() + " 到达栅栏 B");
                barrier.await();
                System.out.println(getName() + " 冲破栅栏 B");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

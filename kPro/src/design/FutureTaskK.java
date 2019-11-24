package design;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class FutureTaskK {

    public static void main(String[] args) throws InterruptedException {

        new FutureTaskK().getAllFuture();

        //Thread implements Runnable，同时持有Runnable
        //execute(Runnable)是Executor的方法，submit(callable)是ExecutorService的方法
        //所以execute(Runnable) 参数可以是Runnable，Thread，FutureTask
        //Executors对外提供四种 ThreadPoolExecutor
        //ThreadPoolExecutor extends AbstractExecutorService implements ExecutorService extends Executor

        //FutureTask implements RunableFuture contains Callable,
        //任务实现体还是在callable的call方法里面
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            return 10 / 2;
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            Integer result = futureTask.get();
            System.out.println("FutureTask Integer: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            //4.处理捕获的线程异常
        }


        testAfterExecute();

        ExecutorService executorService = Executors.newFixedThreadPool(8);

        Future future = executorService.submit(new CThread());
        Future<ArrayList<String>> kfuture = executorService.submit(new KThread());

        try {
            ArrayList<String> aList = kfuture.get();
            for (String str : aList) {
                System.out.println(str);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        Thread.sleep(3000);


        try {
            Object str = future.get();
            System.out.println(str);
        } catch (InterruptedException e) {
            System.out.println(String.format("handle exception in child thread. %s", e));
        } catch (ExecutionException e) {
            System.out.println(String.format("handle exception in child thread. %s", e));
        } finally {
            if (executorService != null) {
                executorService.shutdown();
            }
        }
    }

    private static void testAfterExecute() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                if (r instanceof Thread) {
                    if (t != null) {
                        //处理捕获的异常
                    }
                } else if (r instanceof FutureTask) {
                    FutureTask futureTask = (FutureTask) r;
                    try {
                        futureTask.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        //处理捕获的异常
                    }
                }

            }
        };


        Thread t1 = new Thread(() -> {
            int c = 1 / 0;
        });
        threadPoolExecutor.execute(t1);

        Callable<Integer> callable = () -> 2 / 0;
        threadPoolExecutor.submit(callable);
    }


    public void getAllFuture() {
        try {
            ExecutorService exec = Executors.newFixedThreadPool(10);

            final BlockingQueue<Future<String>> queue = new LinkedBlockingDeque<>(
                    10);
            final CompletionService<String> completionService = new ExecutorCompletionService<>(
                    exec, queue);

            for (int i = 0; i < 10; i++) {
                final int k = i;
                completionService.submit(() -> {
                    int sleepTime = new Random().nextInt(1000);
                    Thread.sleep(sleepTime);
                    return "线程" + k + "睡了" + sleepTime + "微秒";
                });
            }

            for (int i = 0; i < 10; i++) {
                // 获取包含返回结果的future对象（若整个阻塞队列中还没有一条线程返回结果，那么调用take将会被阻塞，当然你可以调用poll，不会被阻塞，若没有结果会返回null，poll和take返回正确的结果后会将该结果从队列中删除）
                Future<String> future = completionService.take();
                // 从future中取出执行结果，这里存储的future已经拥有执行结果，get不会被阻塞
                String result = future.get();
                System.out.println(result);
            }
        } catch (Exception ex) {

        }
    }

}


class CThread implements Callable {

    @Override
    public Object call() throws Exception {
        System.out.println("do something 1");
        exceptionMethod();
        System.out.println("do something 2");
        return "finish cthread";
    }

    private void exceptionMethod() {
        throw new RuntimeException("ChildThread1 exception");
    }

}


class KThread implements Callable<ArrayList<String>> {

    @Override
    public ArrayList<String> call() throws Exception {
        System.out.println("kThread start");
        ArrayList<String> aList = new ArrayList<>();
        aList.add("str1");
        aList.add("str2");
        aList.add("str3");
        return aList;
    }

    private void exceptionMethod() {
        throw new RuntimeException("KThread1 exception");
    }


}
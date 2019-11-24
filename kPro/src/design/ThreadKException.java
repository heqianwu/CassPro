package design;


/**
 * Thread.setDefaultUncaughtExceptionHandler为整个程序设置默认的异常处理器如果当前线程有异常处理器（默认没有），
 * 则优先使用该UncaughtExceptionHandler类；否则，如果当前线程所属的线程组有异常处理器，
 * 则使用线程组的ExceptionHandler；否则，使用全局默认的DefaultUncaughtExceptionHandler；
 * 如果都没有的话，子线程就会退出。
 *
 * 虽然是在回调方法中处理异常，但这个回调方法在执行时依然还在抛出异常的这个线程中
 */
public class ThreadKException {

    public static void main(String[] args){

        Thread tThread=new Thread(new ChildThread(false));
        Thread kThread=new Thread(new ChildThread(true));
        tThread.start();
        kThread.start();
    }


}


class ChildThread implements Runnable {
    private static ChildThreadExceptionHandler exceptionHandler;
    private boolean isDefaultExHandler;

    public ChildThread(boolean isDefaultExHandler){
        this.isDefaultExHandler=isDefaultExHandler;
    }

    static {
        exceptionHandler = new ChildThreadExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new DefThreadExceptionHandler());
    }

    public void run() {
        if(!isDefaultExHandler) {
            Thread.currentThread().setUncaughtExceptionHandler(exceptionHandler);
        }
        System.out.println("do something 1");
        exceptionMethod();
        System.out.println("do something 2");
    }

    public static class ChildThreadExceptionHandler implements Thread.UncaughtExceptionHandler {
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println(String.format("child exception handle. %s", e));
        }
    }

    public static class DefThreadExceptionHandler implements Thread.UncaughtExceptionHandler {
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println(String.format("default exception handle. %s", e));
        }
    }

    private void exceptionMethod() {
        throw new RuntimeException("ChildThread1 exception");
    }
}
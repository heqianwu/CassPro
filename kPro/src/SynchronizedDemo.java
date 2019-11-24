public class SynchronizedDemo {



    static volatile int  kkk=3;

    public static void main(String[] args) {

        kkk=kkk+3;

        synchronized (SynchronizedDemo.class) {
            method();
        }
    }

    private static synchronized void method() {
    }
}
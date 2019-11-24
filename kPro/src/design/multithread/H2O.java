package design.multithread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class H2O {

    private Semaphore osm = new Semaphore(1);
    private Semaphore hsm = new Semaphore(2);
    private CyclicBarrier cb = new CyclicBarrier(3);

    public H2O() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        hsm.acquire();
        try {
            cb.await();
        } catch (Exception ex) {
        }
        releaseHydrogen.run();
        hsm.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        osm.acquire();
        try {
            cb.await();
        } catch (Exception ex) {
        }
        releaseOxygen.run();
        osm.release();
    }

    public static void main(String[] args) {
        Hrelease h = new Hrelease();
        Orelease o = new Orelease();
        H2O h2o = new H2O();
        new OThread(h2o, o).start();
        new HThread(h2o, h).start();
        new HThread(h2o, h).start();
        new HThread(h2o, h).start();
        new HThread(h2o, h).start();
        new HThread(h2o, h).start();
        new HThread(h2o, h).start();
        new HThread(h2o, h).start();
        new OThread(h2o, o).start();
        new HThread(h2o, h).start();
        new OThread(h2o, o).start();
        new OThread(h2o, o).start();
    }

}

class HThread extends Thread {
    private H2O h2o;
    private Hrelease h;

    public HThread(H2O h2o, Hrelease h) {
        this.h2o = h2o;
        this.h = h;
    }

    @Override
    public void run() {
        try {
            h2o.hydrogen(h);
        } catch (Exception ex) {
        }
    }
}

class OThread extends Thread {
    private H2O h2o;
    private Orelease o;

    public OThread(H2O h2o, Orelease o) {
        this.h2o = h2o;
        this.o = o;
    }

    @Override
    public void run() {
        try {
            h2o.oxygen(o);
        } catch (Exception ex) {
        }
    }
}

class Hrelease implements Runnable {

    @Override
    public void run() {
        System.out.println("H");
    }
}


class Orelease implements Runnable {

    @Override
    public void run() {
        System.out.println("O");
    }
}

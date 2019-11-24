package design;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleAndTimer {


    private static class TestTask implements Runnable {
        private String TAG = "";

        public TestTask(String tag) {
            TAG = tag;
        }

        @Override
        public void run() {
            System.out.println(TAG + "\t" + System.currentTimeMillis());
        }
    }

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);

        int time = 3; // 延迟3秒执行

        TestTask zhang = new TestTask("zhang");
        TestTask phil = new TestTask("phil");

        mScheduledThreadPoolExecutor.schedule(zhang, time, TimeUnit.SECONDS);

        // 再上一个任务的3秒后执行
        mScheduledThreadPoolExecutor.schedule(phil, time * 2, TimeUnit.SECONDS);
    }

}

package iodesign.fileio.bigfileread;

import java.util.concurrent.atomic.AtomicInteger;


//https://www.cnblogs.com/metoy/p/4470418.html
public class HBigFileMain {
    static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        HBigFileReader.Builder builder = new HBigFileReader.Builder("/Users/hqwk/cancle/textkkk.txt", line -> {
            if (line.contains("hqw")) {
                System.out.println(line);
                count.getAndIncrement();
            }
        });
        builder.withThreadSize(10).withCharset("gbk").withBufferSize(1024 * 1024);
        HBigFileReader hBigFileReader = builder.build();
        hBigFileReader.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {

        }
        System.out.println("hqw count: " + count);
    }
}

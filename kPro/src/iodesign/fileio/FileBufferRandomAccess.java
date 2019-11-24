package iodesign.fileio;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileBufferRandomAccess {

    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("/Users/hqwk/cancle/textkkk.txt");
            int sum = 0;
            int n;
            long t1 = System.currentTimeMillis();
            try {
                while ((n = fis.read()) >= 0) {
                    // 数据处理
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            long t = System.currentTimeMillis() - t1;
            System.out.println("传统IOread文件,不使用缓冲区,用时:" + t);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fis = new FileInputStream("/Users/hqwk/cancle/textkkk.txt");
            BufferedInputStream bis = new BufferedInputStream(fis);
            int sum = 0;
            int n;
            long t1 = System.currentTimeMillis();
            try {
                while ((n = bis.read()) >= 0) {
                    // 数据处理
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
            long t = System.currentTimeMillis() - t1;
            System.out.println("传统IOread文件,使用缓冲区,用时:" + t);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File file=new File("/Users/hqwk/cancle/texthhh.txt");
        MappedByteBuffer buffer = null;
        try {
            buffer = new RandomAccessFile(file, "r").
                    getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            int sum = 0;
            int n;
            long t1 = System.currentTimeMillis();
            for (int i = 0; i < 1024 * 1024 * 10; i++) {
                // 数据处理
            }
            long t = System.currentTimeMillis() - t1;
            System.out.println("内存映射文件读取文件,用时:" + t);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }

}

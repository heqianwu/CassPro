package iodesign.channeltest;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTxt {
    public static void main(String args[]) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("/Users/hqwk/cancle/FileChannelTxt.txt", "rw");
        FileChannel inChannel = raf.getChannel();
        System.out.println("1. AAA:" + raf.getFilePointer() + ";" + "BBB:" + inChannel.position());


        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf);
        buf.flip();
        System.out.println("2. AAA:" + raf.getFilePointer() + ";" + "BBB:" + inChannel.position());
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
            //Buffer有两种模式，写模式和读模式。在写模式下调用flip()之后，Buffer从写模式变成读模式。
            buf.flip();
            System.out.println();
        }

        ByteBuffer buf2 = ByteBuffer.allocate(48);
        //写入数据
        buf2.put("filechannel test\n".getBytes());
        buf2.flip();
        inChannel.write(buf2);

        raf.close();
    }
}
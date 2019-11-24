package iodesign;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandle implements Runnable {

    private String host;
    private int port;

    private Selector selector;
    private SocketChannel socketChannel;

    private volatile boolean stop;

    public TimeClientHandle(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            //第一步：打开SocketChannel,用于创建客户端连接
            socketChannel = SocketChannel.open();
            //第二步：设置SocketChannel为非阻塞模式
            socketChannel.configureBlocking(false);
            //第三步：创建多路复用器（在Reactor线程中）
            selector = Selector.open();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            // 第四步：socketChannel发起连接
            if (socketChannel.connect(new InetSocketAddress(host, port))) {
                //第五步：如果直接连接成功，则注册到多路复用器上
                socketChannel.register(selector, SelectionKey.OP_READ);
                //第六步：发送请求消息，读应答
                byte[] req = "QUERY TIME ORDER kkk".getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
                writeBuffer.put(req);
                writeBuffer.flip();
                socketChannel.write(writeBuffer);
                if (!writeBuffer.hasRemaining())
                    System.out.println("Send order 2 server succeed kkk.");
            } else
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!stop) {
            try {
                //第七步：多路复用器在run的无限循环体内轮询准备就绪的Key
                selector.select(1000);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        if (key.isValid()) {
                            //第八步：将连接成功的Channel注册到多路复用器上
                            // 判断是否连接成功
                            SocketChannel sc = (SocketChannel) key.channel();
                            if (key.isConnectable()) {
                                if (sc.finishConnect()) {
                                    sc.register(selector, SelectionKey.OP_READ);
                                    //发送请求消息，读应答
                                    byte[] req = "QUERY TIME ORDER".getBytes();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
                                    writeBuffer.put(req);
                                    writeBuffer.flip();
                                    sc.write(writeBuffer);
                                    if (!writeBuffer.hasRemaining())
                                        System.out.println("Send order 2 server succeed.");
                                } else
                                    System.exit(1);// 连接失败，进程退出
                            }
                            //监听读操作，读取服务端写回的网络信息
                            if (key.isReadable()) {
                                //第九步：读取信息到缓冲区
                                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                                int readBytes = sc.read(readBuffer);
                                if (readBytes > 0) {
                                    readBuffer.flip();
                                    byte[] bytes = new byte[readBuffer.remaining()];
                                    readBuffer.get(bytes);
                                    String body = new String(bytes, "UTF-8");
                                    System.out.println("Now is : " + body);
                                    this.stop = true;
                                } else if (readBytes < 0) {
                                    // 对端链路关闭
                                    key.cancel();
                                    sc.close();
                                } else
                                    ; // 读到0字节，忽略
                            }
                        }
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null)
                                key.channel().close();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        // 多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
        if (selector != null)
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

}

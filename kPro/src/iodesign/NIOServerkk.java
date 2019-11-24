package iodesign;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServerkk implements Runnable {

    private Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public NIOServerkk(int port) {
        try {
            //1 打开多复用器
            selector = Selector.open();
            //2 打开服务器通道
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //3 设置服务器通道为非阻塞方式
            ssc.configureBlocking(false);
            //4 绑定地址
            ssc.bind(new InetSocketAddress(port));
            //5 把服务器通道注册到多路复用选择器上，并监听阻塞状态
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server start, port：" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                //1 必须让多路复用选择器开始监听
                selector.select();
                //2 返回所有已经注册到多路复用选择器上的通道的SelectionKey
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                //3 遍历keys
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (key.isValid()) { //如果key的状态是有效的
                        if (key.isAcceptable()) { //如果key是阻塞状态，则调用accept()方法
                            accept(key);
                        }
                        if (key.isReadable()) { //如果key是可读状态，则调用read()方法
                            read(key);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void accept(SelectionKey key) {
        try {
            //1 获取服务器通道
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            //2 执行阻塞方法
            SocketChannel sc = ssc.accept();
            //3 设置阻塞模式为非阻塞
            sc.configureBlocking(false);
            //4 注册到多路复用选择器上，并设置读取标识
            sc.register(selector, SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read(SelectionKey key) {
        try {
            //1 清空缓冲区中的旧数据
            buffer.clear();
            //2 获取之前注册的SocketChannel通道

            SocketChannel sc = (SocketChannel) key.channel();
            //3 将sc中的数据放入buffer中
            int count = sc.read(buffer);
            if (count == -1) { // == -1表示通道中没有数据
                key.channel().close();
                key.cancel();
                return;
            }
            //读取到了数据，将buffer的position复位到0
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            //将buffer中的数据写入byte[]中
            buffer.get(bytes);
            String body = new String(bytes).trim();
            System.out.println("Server：" + body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new NIOServerkk(8379)).start();
    }
}
package com.hqw.pro;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class NettyServerkk {
    public void start() {
        ChannelFactory factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),//boss线程池
                Executors.newCachedThreadPool(),//worker线程池
                8//worker线程数
        );
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        /**
         * 对于每一个连接channel, server都会调用PipelineFactory为该连接创建一个ChannelPipline
         */
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("handler", new ServerLogicHandler());
                return pipeline;
            }
        });

        Channel channel = bootstrap.bind(new InetSocketAddress("127.0.0.1", 8080));
        System.out.println("server start success!");
    }

    public static void main(String[] args) throws InterruptedException {
        NettyServerkk server = new NettyServerkk();
        server.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}

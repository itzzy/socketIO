package com.zzy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.helloworld.ServerHandler01;


/**
 * Created by root on 2017/7/12.
 */
public class Server01 {
    public static void main(String[] args) throws InterruptedException {
        //1 创建两个线程组
        //一个用于处理服务端接收客户端连接的
        //一个用于进行网络通信的（网络读写的）
        EventLoopGroup pGroup = new NioEventLoopGroup();
        EventLoopGroup cGroup = new NioEventLoopGroup();

        //2 创建辅助工具类，用于服务器通道的一系列配置
        ServerBootstrap b = new ServerBootstrap();
        b.group(pGroup,cGroup)//绑定两个线程组
                .channel(NioServerSocketChannel.class)  //指定NIO的模式
                .option(ChannelOption.SO_BACKLOG,1024)  //设置tcp缓冲区
                .option(ChannelOption.SO_SNDBUF,32*1024)    //设置发送缓冲大小
                .option(ChannelOption.SO_RCVBUF,32*1024)    //设置接收缓冲大小
                .option(ChannelOption.SO_KEEPALIVE,true)    //保持连接
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        //3 在这里配置具体数据接收方式的处理
                        sc.pipeline().addLast(new ServerHandler01());
                    }
                });
        //4 进行绑定
        ChannelFuture cf1 = b.bind(8765).sync();
        //ChannelFuture cf2 = b.bind(8764).sync();
        //5 等待关闭
        cf1.channel().closeFuture().sync();
        //cf2.channel().closeFuture().sync();
        pGroup.shutdownGracefully();
        cGroup.shutdownGracefully();
    }

}

package netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;


/**
 * Created by root on 2017/7/12.
 */
public class ServerHandler extends ChannelHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //((ByteBuf)msg).release();//ByteBuf--netty里的
        //try {
            //do something msg
            ByteBuf buf = (ByteBuf) msg;
            //不需要flip
            byte[] data = new byte[buf.readableBytes()];
            buf.readBytes(data);
            String request = new String(data,"utf-8");
            System.out.println("Server: " + request);

            //写给客户端
            String response = "我是反馈的信息";
            //writeAndFlush可以不用手动释放msg，Netty会帮我们释放
            ctx.writeAndFlush(Unpooled.copiedBuffer("888".getBytes()));
            //.addListener(ChannelFutureListener.CLOSE);//添加监听，Server端收到Client发送的信息后，会自动断开连接
//            ctx.write(Unpooled.copiedBuffer("888".getBytes()));
//            ctx.flush();

//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            //手动释放msg
//            ReferenceCountUtil.release(msg);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

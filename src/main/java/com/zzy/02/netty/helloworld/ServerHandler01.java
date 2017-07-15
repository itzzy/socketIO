package netty.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by root on 2017/7/12.
 */
public class ServerHandler01 extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server channel active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"utf-8");
        System.out.println("Server:" + body);
//        String response = "进行返回给客户端的响应:" + body;
//        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
//                //.addListener(ChannelFutureListener.CLOSE);
        //服务器端给客户端的响应
        String response = "Hi Client!!";
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
               // .addListener(ChannelFutureListener.CLOSE);//服务器端异步关闭与Client端连接,不要在Client端主动断开连接

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("读完了...");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

package netty.ende1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by root on 2017/7/13.
 */
public class ServerHandler extends ChannelHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channel active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String request = (String) msg;//之前这里是Buf 现在是String ByteBuf buf = (ByteBuf) msg;
        System.out.println("server: " + msg);
//        String response = "服务器响应: " + msg + "$_";
//        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
        String response = "服务器响应$_";
        //ctx.writeAndFlush(response);
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

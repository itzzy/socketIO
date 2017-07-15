package netty.test;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by root on 2017/7/12.
 */
public class ClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //((ByteBuf)msg).release();//ByteBuf--netty里的
        try {
            //do something msg
            ByteBuf buf = (ByteBuf) msg;
            //不需要flip
            byte[] data = new byte[buf.readableBytes()];
            buf.readBytes(data);
            String request = new String(data,"utf-8");
            System.out.println("Client: " + request);

            //写给客户端
//            String response = "我是反馈的信息";
//            ctx.write(Unpooled.copiedBuffer("888".getBytes()));
//            ctx.flush();//flush的时候才会将数据发送给Server端
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //手动释放
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

package aio;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

/**
 * Created by root on 2017/7/12.
 */
public class Client implements Runnable{

    private AsynchronousSocketChannel asc;

    public Client() throws IOException {
        asc = AsynchronousSocketChannel.open();
    }

    public void connect(){
        asc.connect(new InetSocketAddress("127.0.0.1",8765));
    }

    public void write(String request) throws ExecutionException, InterruptedException {
//        asc.write(ByteBuffer.allocateDirect(ByteBuffer.wrap(request.getBytes()).get()));
        try{
            asc.write(ByteBuffer.wrap(request.getBytes())).get();
            read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void read(){
        ByteBuffer buf = ByteBuffer.allocate(1024);
        try {
            asc.read(buf).get();
            buf.flip();
            byte[] respByte = new byte[buf.remaining()];
            buf.get(respByte);
            System.out.println(new String(respByte,"utf-8").trim());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void run() {//run什么事都没做，Client端会一直运行
        while(true){

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Client c1 = new Client();
        c1.connect();

        Client c2 = new Client();
        c2.connect();

        Client c3 = new Client();
        c3.connect();

        new Thread(c1,"c1").start();
        new Thread(c2,"c2").start();
        new Thread(c3,"c3").start();

        Thread.sleep(2000);

        c1.write("c1 aaaa");
        c2.write("c2 bbbb");
        c3.write("c3 cccc");
    }
}

package socket.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;

/**
 * Created by admin on 2019/10/28.
 */
public class AIOClient {
    private AsynchronousSocketChannel channel;
    public AIOClient(String host,int port){
        init(host,port);
    }
    public static void main(String[] args) {
        AIOClient client = new AIOClient("localhost",9999);
        try {
            System.out.println("enter message send to server");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            client.write(line);
            client.read();
        } catch (Exception e) {
        	e.printStackTrace();
        }finally {
            client.destory();
        }

    }
    private void init(String host,int port){
        try {
            channel = AsynchronousSocketChannel.open();
            channel.connect(new InetSocketAddress(host,port));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void write(String line){
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //将控制台输入的字符串写入Buffer中，写入的数据是一个字节数组
            buffer.put(line.getBytes("UTF-8"));
            buffer.flip();
            channel.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void read(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            //read方式是一个异步操作，具体实现由os实现。可以增加get方法，实现阻塞，等待os的写操作结果
            channel.read(buffer).get();
            buffer.flip();
            System.out.println("from server :" + new String(buffer.array(), "UTF-8"));
        } catch (Exception e) {
        	// TODO: handle exception
        }
    }
    private void destory(){
        if(null!=channel){
           try {
           	    channel.close();
           } catch (Exception e) {
               e.printStackTrace();
           }
        }
    }

}

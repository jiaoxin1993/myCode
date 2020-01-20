package socket.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by admin on 2019/10/28.
 */
public class NIOClient {
    public static void main(String[] args) {
        InetSocketAddress remote = new InetSocketAddress("localhost",9999);
        SocketChannel channel = null;

        //定义缓存
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            //开启通道
            channel = SocketChannel.open();
            //连接到远程服务器
            channel.connect(remote);
            Scanner scanner = new Scanner(System.in);
            while(true){
                System.out.println("put message for send to server");
                String line = scanner.nextLine();
                if(line.equals("exit")){
                    break;
                }
                //将控制台输入的数据写入缓存
                buffer.put(line.getBytes("UTF-8"));
                //重置游标
                buffer.flip();
                //将数据发送给服务器
                channel.write(buffer);
                //清空缓存
                buffer.clear();

                //读取服务器返回数据
                int readLength = channel.read(buffer);
                if(readLength == -1){
                    break;
                }
                //重置游标
                buffer.flip();
                byte[] datas = new byte[buffer.remaining()];
                //将buffer中有效的数据保存到字节数组中
                buffer.get(datas);
                System.out.println("from server " + new String(datas, "UTF-8"));
                //清空缓存
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(null != channel){
                    channel.close();
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }

        }
    }
}

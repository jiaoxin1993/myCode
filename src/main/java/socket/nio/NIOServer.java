package socket.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by admin on 2019/10/28.
 */
public class NIOServer implements Runnable{
    //多路复用器 选择器 用于注册通道的
    private Selector selector;
    //定义了两个缓存，分别用于读和写 初始化空间大小单位为字节
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer witeBuffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) {
        new Thread(new NIOServer(9999)).start();
    }
    public NIOServer(int prot){
        init(prot);
    }

    public void run() {
        while(true){
            try {
                //阻塞方法，当至少一个通道被选中，此方法返回。
                //通道是否选择，由注册到多路复用器中的通道标记决定
                this.selector.select();
                //返回以选中的通道标记集合，集合中保存的是通道的标记，相当于通道的Id
                Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
                while(keys.hasNext()){
                    SelectionKey key =  keys.next();
                    //将本次要处理的通道从集合中删除，下次循环根据最新的通道列表再次执行必要的业务逻辑
                    keys.remove();
                    //通道是否有效
                    if(key.isValid()){
                        //阻塞状态
                        try {
                            if(key.isAcceptable()){
                                accept(key);
                            }
                        } catch (Exception e) {
                            //断开连接
                            key.cancel();
                        }
                        //可读状态
                        try {
                            if(key.isReadable()){
                                read(key);
                            }
                        } catch (Exception e) {
                            //断开连接
                            key.cancel();
                        }
                        //可写状态
                        try {
                            if(key.isWritable()){
                                write(key);
                            }
                        } catch (Exception e) {
                            //端口连接
                            key.cancel();
                        }

                    }
                }
            } catch (Exception e) {
            	// TODO: handle exception
            }
        }
    }

    private void init(int prot){
        try {
            System.out.println("server starting at port "+ prot+"....");
            //开启多路复用器
            this.selector = Selector.open();
            //开启服务通道
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            //非阻塞，如果传递参数true，为阻塞模式
            socketChannel.configureBlocking(false);
            //绑定端口
            socketChannel.bind(new InetSocketAddress(prot));
            //注意 并标记当前服务通道状态
            /*
                register(selector,int)
                OP_ACCEPT:连接成功的标记
                OP_READ：可以读取数据的标记
                OP_WRITE：可以写入数据的标记
                OP_CONNECT：连接建立后的标记
             */
            socketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
            System.out.println("server started ...");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void accept(SelectionKey key){
        try {
            //此通道为init方法中注册到Selector 上的ServerSocketChannel
            ServerSocketChannel socketChannel = (ServerSocketChannel)key.channel();
            //阻塞方法，当客户端发起请求后返回，此通道和客户端一一对应
            SocketChannel channel = socketChannel.accept();
            channel.configureBlocking(false);
            //设置对应客户端的通道标记状态，此通道为读取数据使用的
            channel.register(this.selector, SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void read(SelectionKey key){
        try {
            //清空读缓存
            this.readBuffer.clear();
            //获取通道
            SocketChannel channel = (SocketChannel)key.channel();
            //将通道中的数据读取到缓存中。通道中的数据，就是客户端发送给服务器的数据
            int readLength = channel.read(readBuffer);
            //检查客户端是否写入数据
            if(readLength == -1){
                //关闭通道
                key.channel().close();
                //关闭连接
                key.cancel();
            }
            /*
                flip , NIO中最复杂的操作就是Buffer的控制
                buffer中有一个游标，游标信息在操作后不会归零，如果直接访问buffer的话，数据有不一致的问题
                flip是重置游标的方法。NIO编程中，flip方法是常用方法
             */
            this.readBuffer.flip();
            //字节数组，保存具体数据的。buffer.remaining ->是获取buffer中有效数据长度的方法
            byte[] datas = new byte[this.readBuffer.remaining()];
            //将buffer中有效的数据保存到字节数组中
            this.readBuffer.get(datas);
            System.out.println("from " + channel.getRemoteAddress() + " client: " + new String(datas, "UTF-8"));
            //注册通道，标记为写操作
            channel.register(this.selector, SelectionKey.OP_WRITE);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //关闭通道
                key.channel().close();
                //关闭连接
                key.cancel();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    private void write(SelectionKey key){
        //清空写缓存
        this.witeBuffer.clear();
        //获取通道
        SocketChannel channel = (SocketChannel)key.channel();
        Scanner scanner = new Scanner(System.in);
        try {
            String line = scanner.nextLine();
            //将控制台输入的字符串写入Buffer中，写入的数据是一个字节数组
            this.witeBuffer.put(line.getBytes("UTF-8"));
            this.witeBuffer.flip();
            channel.write(witeBuffer);
            //注册通道，标记为写操作
            channel.register(this.selector, SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

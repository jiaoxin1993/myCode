package netty.fixedlength;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2019/10/29.
 */
public class ClientFixedLength {
    //处理请求和处理服务端响应的线程组
    private EventLoopGroup group = null;
    //客户端启动相关的配置信息 Bootstrap引导
    private Bootstrap bootstrap = null;
    public static void main(String[] args) {
        ClientFixedLength client = null;
        ChannelFuture future = null;
        try {
            client = new ClientFixedLength();
            future = client.doRequest("localhost",9999);
            Scanner scanner = null;
            while (true){
                scanner = new Scanner(System.in);
                System.out.println("enter message send to server (enter 'exit' for close client)");
                String line = scanner.nextLine();
                if ("exit".equals(line)){
                    //addListener --增加监听，当某条件满足的时候触发监听器
                    //ChannelFutureListener.CLOSE 关闭监听器 代表ChannelFuture执行返回后，关闭连接。
                    future.channel().writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8"))).addListener(ChannelFutureListener.CLOSE);
                    break;
                }
                future.channel().writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8")));
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != future){
                try {
                    future.channel().closeFuture().sync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public ClientFixedLength(){
        init();
    }
    private void init(){
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        //绑定线程
        bootstrap.group(group);
        //设置通讯模式为NIO
        bootstrap.channel(NioSocketChannel.class);
    }
    public ChannelFuture doRequest(String host,int port)throws InterruptedException{
        this.bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelHandler[] handlers = new ChannelHandler[3];
                //定长Handler。通过构造参数设置消息长度(单位字节)，发送的消息长度不足可以使用空格补全
                //Decoder 解码器
                handlers[0] = new FixedLengthFrameDecoder(4);
                //字符串解码器Handler，会自动处理channelRead方法的msg参数，将ByteBuf类型的数据转换为字符串
                handlers[1] = new StringDecoder(Charset.forName("UTF-8"));
                handlers[2] = new ClientFixedLengthHandler();
                socketChannel.pipeline().addLast(handlers);
            }
        });
        //建立连接
        ChannelFuture future =  this.bootstrap.connect(host,port).sync();
        return  future;
    }
    public void release(){
        this.group.shutdownGracefully();
    }
}

package netty.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2019/10/29.
 */
public class ClientProtocol {
    //处理请求和处理服务端响应的线程组
    private EventLoopGroup group = null;
    //客户端启动相关的配置信息 Bootstrap引导
    private Bootstrap bootstrap = null;
    public static void main(String[] args) {
        ClientProtocol client = null;
        ChannelFuture future = null;
        try {
            client = new ClientProtocol();
            future = client.doRequest("localhost",9999,new ClientProtocolHandler());
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
                line = ClientProtocolHandler.ProtocolParser.transferTo(line);
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
    public ClientProtocol(){
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
    public ChannelFuture doRequest(String host,int port,final ChannelHandler... acceptorHandlers)throws InterruptedException{
        this.bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                socketChannel.pipeline().addLast(acceptorHandlers);
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

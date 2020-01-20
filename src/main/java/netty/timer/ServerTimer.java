package netty.timer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import netty.fixedlength.ServerFixedLengthHandler;

import java.nio.charset.Charset;

/**
 * Created by admin on 2019/10/29.
 */
public class ServerTimer {
    //监听线程组，监听客户端请求
    private EventLoopGroup acceptorGroup = null;
    //处理客户端相关操作线程组，负责处理与客户端的数据通讯
    private EventLoopGroup clientGroup = null;
    //服务启动相关的配置信息 Bootstrap引导
    private ServerBootstrap bootstrap = null;

    public static void main(String[] args) {
        ChannelFuture future = null;
        ServerTimer server = null;
        try {
            server = new ServerTimer();
            future = server.doAccept(9999);
            System.out.println("server started.");
            //关闭连接的
            future.channel().closeFuture().sync();
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
            if(null != server){
                try {
                    server.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public ServerTimer(){
        init();
    }
    private void init(){
        //初始化线程组，构建线程组的时候，如果不传递参数，则默认构建的线程组线程数是CPU核心数量
        acceptorGroup = new NioEventLoopGroup();
        clientGroup = new NioEventLoopGroup();
        //初始化服务的配置
        bootstrap = new ServerBootstrap();
        //绑定线程组
        bootstrap.group(acceptorGroup, clientGroup);
        //设定通讯模式为NIO，同步非阻塞
        bootstrap.channel(NioServerSocketChannel.class);
        //设置缓冲区大小，缓存区单位是字节
        bootstrap.option(ChannelOption.SO_BACKLOG,1024);
        //SO_SNDBUF 发送缓存区   SO_RCVBUF接受缓存区   SO_KEEPALIVE 开启心跳检测（保证连接有效）
        bootstrap.option(ChannelOption.SO_SNDBUF,16*1024).option(ChannelOption.SO_RCVBUF,16*1024).option(ChannelOption.SO_KEEPALIVE,true);

    }
    /*
        监听处理逻辑
            port 监听端口
            acceptorHandlers 处理器，如何处理客户端请求
     */
    public ChannelFuture doAccept(int port) throws InterruptedException{
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelHandler[] acceptorHandlers = new ChannelHandler[3];
                //定长Handler。通过构造参数设置消息长度(单位字节)，发送的消息长度不足可以使用空格补全
                //Decoder 解码器
                acceptorHandlers[0] = new FixedLengthFrameDecoder(4);
                //字符串解码器Handler，会自动处理channelRead方法的msg参数，将ByteBuf类型的数据转换为字符串
                acceptorHandlers[1] = new StringDecoder(Charset.forName("UTF-8"));
                acceptorHandlers[2] = new ServerFixedLengthHandler();
                socketChannel.pipeline().addLast(acceptorHandlers);
            }
        });
        //bind方法--绑定监听端口的。ServerBootstrap可以绑定多个监听端口。多次调用bind方法即可
        //sync-开始监听逻辑。返回一个ChannelFuture。返回结果代表的是监听成功后的一个对应的未来结果
        //可以使用ChannelFuture实现后续的服务器和客户端的交互
        ChannelFuture future = bootstrap.bind(port).sync();
        return  future;
    }
    /*
        shutdownGracefully-- 方法是一个安全关闭的方法。可以保证不放弃任何一个已接受的客户端请求
     */
    public void release(){
        this.acceptorGroup.shutdownGracefully();
        this.clientGroup.shutdownGracefully();
    }

}

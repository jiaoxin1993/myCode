package netty.Serializable;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2019/10/29.
 */
public class ClientSerializable {
    //处理请求和处理服务端响应的线程组
    private EventLoopGroup group = null;
    //客户端启动相关的配置信息 Bootstrap引导
    private Bootstrap bootstrap = null;
    public static void main(String[] args) {
        ClientSerializable client = null;
        ChannelFuture future = null;
        try {
            client = new ClientSerializable();
            future = client.doRequest("localhost",9999,new ClienSerializableHandler());
            RequestMessage request = new RequestMessage();
            request.setId(new Random().nextLong());
            request.setMessage("client test1");
            //压缩信息
            String attachment = "一段要被压缩的文件";
            request.setAttachment(GzipUtils.zip(attachment.getBytes()));
            future.channel().writeAndFlush(request);
            TimeUnit.SECONDS.sleep(1);
            future.addListener(ChannelFutureListener.CLOSE);
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
    public ClientSerializable(){
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
                socketChannel.pipeline().addLast(SerializableFactoryMarshalling.buildMarshallingDecoder());
                socketChannel.pipeline().addLast(SerializableFactoryMarshalling.buildMarshallingEncoder());
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

package netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by admin on 2019/11/1.
 */
public class HttpStaticFileServer {
    private final int port;

    public HttpStaticFileServer(int port) {
        this.port = port;
    }
    public static void main(String[] args) throws Exception{
        int prot = 8089;
        if(args.length >0){
            prot = Integer.parseInt(args[0]);
        }else {
            prot = 8089;
        }
        new HttpStaticFileServer(prot).run();

    }
    public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();//线程1监听
        EventLoopGroup workerGroup = new NioEventLoopGroup();//线程2处理channel
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new HttpStaticFileServerInitializer());
            bootstrap.bind(port).sync().channel().closeFuture().sync();
        }finally {
            //优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

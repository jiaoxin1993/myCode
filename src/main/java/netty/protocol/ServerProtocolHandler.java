package netty.protocol;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by admin on 2019/10/29.
 */
/*
    @Sharable注解
        代表当前Handler是一个可以分享的处理器，也就意味着，服务器注册此Handler后，可以分享给多个客户端同时使用
        如果不使用注解描述类型，则每次客户端请求时，必须为客户端重新创建一个新的Handler对象
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new xxHandler());
            }
        });
 */
@ChannelHandler.Sharable
public class ServerProtocolHandler extends ChannelHandlerAdapter {
    /*
        业务处理逻辑
        用于处理读取数据请求的逻辑
            ctx 上下文对象，其中包含客户端建立连接的所有资源。如：对应的Channel
            msg 读取到的数据 默认类型是ByteBuf 是Netty自定义的 是对ByteBuffer的封装,不需要考虑复位下标
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = msg.toString();
        System.out.println("server receive protocol content:"+ message);
        message = ProtocolParser.parse(message);
        if(null == message){
            System.out.println("error request from client");
            return;
        }
        System.out.println("from client: "+message);

        String line = "server message";
        line = ProtocolParser.transferTo(line);
        System.out.println(" server send : "+line);
        //写操作自动释放缓存，避免内存溢出问题
        ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8")));

    }

    /*
            异常处理逻辑 当客户端异常退出也会运行
            ChannelHandlerContext 关闭 代表当前与客户端连接资源关闭
         */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("server exceptionCaught method run...");
        cause.printStackTrace();
        ctx.close();
    }
    static class ProtocolParser{
        public static String parse(String messsage){
            String[] temp = messsage.split("HEADBOOY");
            temp[0] = temp[0].substring(4);
            temp[1] = temp[0].substring(0,(temp[1].length()-4));
            int length = Integer.parseInt(temp[0].substring(temp[0].indexOf(":")+1));
            if(length != temp[1].length()){
                return null;
            }
            return temp[1];
        }
        public static String transferTo(String message){
            message = "HEADcontent-length:"+message.length()+"HEADBOOY"+message+"BOOY";
            return message;
        }
    }
}

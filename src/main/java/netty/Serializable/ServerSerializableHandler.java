package netty.Serializable;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by admin on 2019/10/29.
 */
@ChannelHandler.Sharable
public class ServerSerializableHandler extends ChannelHandlerAdapter {
    /*
        业务处理逻辑
        用于处理读取数据请求的逻辑
            ctx 上下文对象，其中包含客户端建立连接的所有资源。如：对应的Channel
            msg 读取到的数据 默认类型是ByteBuf 是Netty自定义的 是对ByteBuffer的封装,不需要考虑复位下标
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("from client :ClassName -:" + msg.getClass().getName() + "; message :" + msg.toString());
        if(msg instanceof RequestMessage){
            RequestMessage request = (RequestMessage)msg;
            byte[] attachment = GzipUtils.unzip(request.getAttachment());
            System.out.println(new String(attachment));
        }
        ResponseMessage response = new ResponseMessage();
        response.setId(0L);
        response.setMessage("test1 response");
        ctx.writeAndFlush(response);

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
}

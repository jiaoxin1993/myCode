package netty.heatbeat;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2019/10/29.
 */
@ChannelHandler.Sharable
public class ServerHeatBeatHandler extends ChannelHandlerAdapter {
    private static List<String> credentials = new ArrayList<String>();
    private static final String HEATBEAT_SUCCESS = "SERVER_RETURN_HEATBEAT_SUCCESS";
    public ServerHeatBeatHandler(){
        //初始化客户端信息
        credentials.add("169.254.41.228_DESKTOP-1KVRQ89");
    }
    /*
        业务处理逻辑
        用于处理读取数据请求的逻辑
            ctx 上下文对象，其中包含客户端建立连接的所有资源。如：对应的Channel
            msg 读取到的数据 默认类型是ByteBuf 是Netty自定义的 是对ByteBuffer的封装,不需要考虑复位下标
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof String){
            this.checkCredential(ctx,msg.toString());
        }else if(msg instanceof HeatBeatMessage){
            this.readHeatBeatMessage(ctx,msg);
        }else {
            ctx.writeAndFlush("wrong message").addListener(ChannelFutureListener.CLOSE);
        }
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
    /*
        身份检查，检查客户端身份是否有效
        客户端身份信息应该由数据库信息或配置信息获取
        身份通过 返回确认消息
        身份无效 断开连接
     */
    private void checkCredential(ChannelHandlerContext ctx, String credential){
        System.out.println(credential);
        System.out.println(credentials);
        if(credentials.contains(credential)){
            ctx.writeAndFlush(HEATBEAT_SUCCESS);
        }else {
            ctx.writeAndFlush("no credential contains").addListener(ChannelFutureListener.CLOSE);
        }
    }
    /*
        获取心跳信息
     */
    private void readHeatBeatMessage(ChannelHandlerContext ctx, Object msg){
        HeatBeatMessage message = (HeatBeatMessage)msg;
        System.out.println(message);
        System.out.println("===================");
        ctx.writeAndFlush("receive heatbeat message");
    }
}

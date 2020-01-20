package netty.protocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by admin on 2019/10/29.
 */
public class ClientProtocolHandler extends ChannelHandlerAdapter {
    /*
        业务处理逻辑
        用于处理读取数据请求的逻辑
            ctx 上下文对象，其中包含客户端建立连接的所有资源。如：对应的Channel
            msg 读取到的数据 默认类型是ByteBuf 是Netty自定义的 是对ByteBuffer的封装,不需要考虑复位下标
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            String message = msg.toString();
            System.out.println("client receive protocol content:"+ message);
            message = ProtocolParser.parse(message);
            if(null == message){
                System.out.println("error response from server");
                return;
            }
            System.out.println("from server: "+message);
        }finally {
            //用于释放缓存，避免内存溢出
            ReferenceCountUtil.release(msg);
        }

    }

    /*
            异常处理逻辑 当客户端异常退出也会运行
            ChannelHandlerContext 关闭 代表当前与客户端连接资源关闭
         */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exceptionCaught method run...");
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
            message = "content-length:"+message.length()+"HEADBOOY"+message+"BOOY";
            return message;
        }
    }
}

package netty.first;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by admin on 2019/10/29.
 */
public class ClientHelloWordHandler extends ChannelHandlerAdapter {
    /*
        业务处理逻辑
        用于处理读取数据请求的逻辑
            ctx 上下文对象，其中包含客户端建立连接的所有资源。如：对应的Channel
            msg 读取到的数据 默认类型是ByteBuf 是Netty自定义的 是对ByteBuffer的封装,不需要考虑复位下标
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            //获取读取的数据，是一个缓冲
            ByteBuf readBuffer = (ByteBuf)msg;
            //创建一个字节数组，用于保存缓存中的数据
            byte[] tempDatas = new byte[readBuffer.readableBytes()];
            //将缓存中的数据读取到字节数组中
            readBuffer.readBytes(tempDatas);
            String message = new String(tempDatas,"UTF-8");
            System.out.println("from server :"+ message);

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
        ctx.close();
    }
}

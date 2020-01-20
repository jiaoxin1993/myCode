package netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * Created by admin on 2019/11/1.
 */
public class HttpStaticFileServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //获取通道的连接点
        ChannelPipeline pipeline = socketChannel.pipeline();
        /**
         *  ReadTimeoutHandler, 用于控制读取数据的时候的超时，10表示如果10秒钟没有数据读取，那么就引发通道channel关闭
         *  WriteTimeoutHandler, 用于控制数据输出的时候的超时，构造参数1表示持续1秒没有数据写，那么就引发通道channel关闭
         *
         */
        pipeline.addLast("decoder",new HttpRequestDecoder());//http-request解码器，
        pipeline.addLast("aggregator",new HttpObjectAggregator(65536));//对传输文件大小限制
        pipeline.addLast("encoder",new HttpResponseEncoder());//http-response编码器
        pipeline.addLast("chunkedWriter",new ChunkedWriteHandler());//向客户端发送数据的一个Handler
        pipeline.addLast();

    }
}

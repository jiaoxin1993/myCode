package netty.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

import java.io.File;

/**
 * Created by admin on 2019/11/3.
 */
public class HttpStaticFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
    public static final String HTTP_DATE_FORMAT = "EEE,dd MM yyyy HH:mm:ss zzz";
    public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";//时区
    public static final int HTTP_CACHE_SECONDS = 60;
    private final boolean useSendFiel;
    public HttpStaticFileServerHandler(boolean useSendFiel) {
        this.useSendFiel = useSendFiel;
    }
    /*
        类似channelRead方法
     */
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, FullHttpRequest request) throws Exception {
        if(!request.decoderResult().isSuccess()){//是否请求成功
            sendError(channelHandlerContext);//BAD_REQUEST
        }
        if(request.method() != HttpMethod.GET){
            sendError(channelHandlerContext);//METHOD_NOT_ALLOWED
        }
        final String uri = request.uri();
        System.out.println("----uri----"+uri);
        final String path = sanitizeUri(uri);
        System.out.println("---path---"+path);
        if(path == null){
            sendError(channelHandlerContext);//FORBIDDEN

        }
        File file = new File(path);
    }
    public void sendError(ChannelHandlerContext channelHandlerContext){

    }
    public String sanitizeUri(String uri){
        return uri;
    }
}

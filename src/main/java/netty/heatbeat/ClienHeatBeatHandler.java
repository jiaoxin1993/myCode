package netty.heatbeat;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2019/10/29.
 */
public class ClienHeatBeatHandler extends ChannelHandlerAdapter {
    //计划任务线程池
    private ScheduledExecutorService executorService= Executors.newScheduledThreadPool(1);
    private ScheduledFuture heatbeat;
    private InetAddress remoteAddr;
    private static final String HEATBEAT_SUCCESS = "SERVER_RETURN_HEATBEAT_SUCCESS";
    /*
        客户端一旦建立连接，则首先执行该方法，只运行一次
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //获取本地INET信息
        this.remoteAddr = InetAddress.getLocalHost();
        //获取本地计算机名称
        String computerName = System.getenv().get("COMPUTERNAME");
        String credentials = this.remoteAddr.getHostAddress()+"_"+computerName;
        System.out.println(credentials);
        //发送到服务器，最为信息的对比证书
        ctx.writeAndFlush(credentials);
    }

    /*
            业务处理逻辑
            用于处理读取数据请求的逻辑
                ctx 上下文对象，其中包含客户端建立连接的所有资源。如：对应的Channel
                msg 读取到的数据 默认类型是ByteBuf 是Netty自定义的 是对ByteBuffer的封装,不需要考虑复位下标
         */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if(msg instanceof String){
                //如果服务端身份认证成功，返回成功认证，客户端加入心跳定时任务
                if(HEATBEAT_SUCCESS.equals(msg)){
                    this.heatbeat = this.executorService.scheduleWithFixedDelay(new HeatbeatTask(ctx),0L,2L, TimeUnit.SECONDS);
                    System.out.println("client receive - "+ msg);
                }else {
                    System.out.println("client receive - "+ msg);
                }
            }
        }finally {
            //用于释放缓存，避免内存溢出
            ReferenceCountUtil.release(msg);
        }
        System.out.println("from server :ClassName -:" + msg.getClass().getName() + "; message :" + msg.toString());
    }

    /*
            异常处理逻辑 当客户端异常退出也会运行
            ChannelHandlerContext 关闭 代表当前与客户端连接资源关闭
         */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exceptionCaught method run...");
        //回收资源
        if(this.heatbeat != null){
            this.heatbeat.cancel(true);
            this.heatbeat = null;
        }
        ctx.close();
        cause.printStackTrace();
    }
}

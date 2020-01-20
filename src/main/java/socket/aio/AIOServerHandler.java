package socket.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Scanner;

/**
 * Created by admin on 2019/10/28.
 */
public class AIOServerHandler implements CompletionHandler<AsynchronousSocketChannel,AIOServer> {
    /*
       业务处理逻辑，当请求到来后，监听成功，应该做什么
       一定要实现的逻辑：为下次客户端请求开启监听。accept方法调用
       result参数：就是和客户端直接建立关联的通道
        无论BIO、NIO、AIO中,一旦连接建立。两端就是平等的
        result中由通道中的所有相关数据，入：os操作系统准备的读取数据缓存，或等待返回数据的缓存
    */
    @Override
    public void completed(AsynchronousSocketChannel result, AIOServer attachment) {
        //处理下一次的客户端请求，类似递归逻辑
        attachment.getServerChannel().accept(attachment,this);
        doRead(result);
    }
    /*
        异常处理逻辑，当服务端代码出现异常的时候，做什么事情
     */
    @Override
    public void failed(Throwable exc, AIOServer attachment) {
        exc.printStackTrace();
    }
    private void doRead(final AsynchronousSocketChannel channel){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        /*
            异步读操作，read(ByteBuffer dst,
                  A attachment,
                  CompletionHandler<Integer,? super A> handler);
            dst 目的地，是处理客户端传递数据的中转站缓存
            attachment -处理客户端传递的数据的对象 通常使用Buffer
            handler - 处理逻辑
         */
        channel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            /*
                业务逻辑，读取客户端传输数据
                attachment - 在completed方法执行的时候，os已经将客户端请求的数据写入到Buffer中了。
                但是未复位（flip），使用前一定要复位
             */
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                try {
                    System.out.println(attachment.capacity());//返回缓冲区容量
                    attachment.flip();
                    System.out.println("from client :" + new String(attachment.array(), "UTF-8"));
                    doWrite(channel);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }
    //项目中服务器返回结果应该是经过计算返回结果
    private void doWrite(AsynchronousSocketChannel result){
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            System.out.println("enter message send to client");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            //将控制台输入的字符串写入Buffer中，写入的数据是一个字节数组
            buffer.put(line.getBytes("UTF-8"));
            buffer.flip();
            //write方式是一个异步操作，具体实现由os实现。可以增加get方法，实现阻塞，等待os的写操作结果
            result.write(buffer);
            //result.write(buffer).get();  调用get代表服务器端线程阻塞，等待os的写操作完成
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package socket.nio;

import java.nio.ByteBuffer;

/**
 * Created by admin on 2019/10/28.
 * 关于buffer.flip();的使用原因
 */
public class BufferTest {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        byte[] temp = new byte[]{3,2,1};
        //java.nio.HeapByteBuffer[pos=0 lim=8 cap=8]
        System.out.println("写入数据之前： " + buffer);
        //将字节数组写入buffer中
        buffer.put(temp);
        //java.nio.HeapByteBuffer[pos=3 lim=8 cap=8]
        //pos 游标位置   lim 限制数量  cap 最大容量
        System.out.println("写入数据之后： " + buffer);
        for (int i = 0; i < buffer.remaining(); i++) {
        	int data = buffer.get(i);
            System.out.println(i+"---"+data);
        }
        System.out.println("重置游标后");
        buffer.flip();
        for (int i = 0; i < buffer.remaining(); i++) {
            int data = buffer.get(i);
            System.out.println(i+"---"+data);
        }
    }

}

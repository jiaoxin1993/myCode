package thread.container;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by admin on 2019/10/23.
 * put & take 自动阻塞
 */
public class ConcurrentQueue2 {
    public static void main(String[] args) {
        LinkedBlockingDeque queue = new LinkedBlockingDeque();
        for (int i = 0; i < 10; i++) {
        	queue.offer("value"+i);
        }
        System.out.println(queue);
        //peek() 查看queue中的首数据
        System.out.println(queue.peek());
        System.out.println(queue.size());
        //poll() 取出queue中的首数据
        System.out.println(queue.poll());
        System.out.println(queue.size());
    }
}

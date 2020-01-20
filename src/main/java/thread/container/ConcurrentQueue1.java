package thread.container;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by admin on 2019/10/23.
 */
public class ConcurrentQueue1 {
    public static void main(String[] args) {
        Queue<String> queue = new ConcurrentLinkedQueue<String>();
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

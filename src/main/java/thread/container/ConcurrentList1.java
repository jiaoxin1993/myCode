package thread.container;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by admin on 2019/10/23.
 */
public class ConcurrentList1 {
    public static void main(String[] args) {
        //final List<String> list = new ArrayList<String>();
        //final List<String> list = new Vector<String>();//线程安全synchronized
        final List<String> list = new CopyOnWriteArrayList<String>();
        final Random random = new Random();
        Thread[] threads = new Thread[100];
        //加门闩
        final CountDownLatch latch = new CountDownLatch(threads.length);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
        	threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                    	list.add("value" + random.nextInt(1000));
                    }
                    latch.countDown();
                }
            });
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        //等待线程运行完毕
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-begin+"毫秒");
    }
}

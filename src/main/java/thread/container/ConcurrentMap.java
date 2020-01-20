package thread.container;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by admin on 2019/10/23.
 */
public class ConcurrentMap {
    public static void main(String[] args) {
        //final Map<String,String> map = new Hashtable<String, String>();
        //final Map<String,String> map = new ConcurrentHashMap<String, String>();
        final Map<String,String> map = new ConcurrentSkipListMap<String, String>();
        final Random random = new Random();
        Thread[] threads = new Thread[100];
        //加门闩
        final CountDownLatch latch = new CountDownLatch(threads.length);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
        	threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                    	map.put("key"+random.nextInt(100000),"value"+random.nextInt(100000));
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

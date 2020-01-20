package thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2019/10/22.
 * ExecutorService 线程池接口
 */
public class ExecutorService5 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        System.out.println(service);
        for (int i = 0; i < 5; i++) {
            service.execute(new Runnable() {
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"- execute--start");
                }
            });
        }
    }
}

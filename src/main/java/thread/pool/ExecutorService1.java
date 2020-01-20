package thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2019/10/22.
 * ExecutorService 线程池接口
 */
public class ExecutorService1 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 6; i++) {
        	service.execute(new Runnable() {
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"- ExecutorService--start");
                }
            });
        }
        System.out.println(service);
        service.shutdown();
        //是否已经结束，相当于回收了资源
        System.out.println(service.isTerminated());
        //是否已经关闭，是否调用过shutdown方法
        System.out.println(service.isShutdown());
        System.out.println(service);
        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);
    }
}

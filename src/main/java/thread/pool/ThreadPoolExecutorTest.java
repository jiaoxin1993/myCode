package thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2019/10/22.
 * ThreadPoolExecutor线程池底层实现，除了ForkJoinPool外，其他常用线程池底层都是使用ThreadPoolExecutor实现的
 */
public class ThreadPoolExecutorTest {
    public static void main(String[] args) {
        //模拟FixedThreadPool，核心线程5个，最大容量5个，线程的生命周期无限
        ExecutorService service = new ThreadPoolExecutor(5,5,0L, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>());
        for (int i = 0; i < 6; i++) {
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

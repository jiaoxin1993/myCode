package thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2019/10/22.
 * ExecutorService 线程池接口
 */
public class ExecutorService4 {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
        System.out.println(service);
        //定时完成任务  scheduleAtFixedRate（Runnable，start-limit，limit,TimeUnit）任务  任务开启间隔  任务间隔  任务间隔单位
        service.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"- scheduleAtFixedRate--start");
            }
        },0,300,TimeUnit.MILLISECONDS);

    }
}

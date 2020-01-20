package thread.pool;

import java.util.concurrent.*;

/**
 * Created by admin on 2019/10/22.
 * ExecutorService 线程池接口
 */
public class ExecutorService2 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<String> futute = service.submit(new Callable<String>() {
            public String call() throws Exception {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("call()运行");
                return Thread.currentThread().getName()+"- test1 submit";
            }
        });
        System.out.println(futute);
        System.out.println(futute.isDone());//查看线程是否结束，任务是否完成 call方法执行是否完成
        try {
            System.out.println(futute.get()); //获取call方法返回值
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(futute.isDone());
        service.shutdown();
    }
}

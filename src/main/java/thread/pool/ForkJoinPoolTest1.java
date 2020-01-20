package thread.pool;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by admin on 2019/10/22.
 * ForkJoinPool  分支合并线程池
 */
public class ForkJoinPoolTest1 {
    final static int[] numbers = new int[1000000];
    final static int MaX_SIZE = 50000;
    final static Random r = new Random();
    static {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = r.nextInt(1000);
        }
    }
    static class AddTask extends RecursiveTask<Long>{ //RecursiveAction
        int begin,end;
        public AddTask(int begin,int end){
            this.begin = begin;
            this.end = end;
        }
        @Override
        protected Long compute() {
            if((end-begin)<MaX_SIZE){ //满足分支线执行规模开始执行，否则继续进行分支操作
                Long sum =0L;
                for (int i = begin; i < end; i++) {
                    sum += numbers[i];
                }
                return sum;
            }else{//不满足分支执行规模，打分支
                int middle = begin+(end-begin)/2;
                //创建分支
                AddTask task1 = new AddTask(begin,middle);
                AddTask task2 = new AddTask(middle,end);
                task1.fork();//就是用于开启新的任务，就是分支工作的。就是开启一个新的线程任务
                task2.fork();
                //join  合并，将任务的结果获取，这是一个阻塞方法，滴定会得到结果数据
                return task1.join() + task2.join();
            }
        }
    }
    public static void main(String[] args) throws Exception{
        long result = 0L;
        for (int i = 0; i < numbers.length; i++) {
        	result += numbers[i];
        }
        System.out.println(result);

        //通过分支合并计算
        ForkJoinPool pool = new ForkJoinPool();
        AddTask task = new AddTask(0,numbers.length);
        Future<Long> future = pool.submit(task);
        System.out.println(future.get());
    }
}

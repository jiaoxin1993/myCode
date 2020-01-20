package thread;

/**
 * Created by admin on 2019/10/18.
 * synchronized关键字
 *  同步方法 原子性
 *  加锁的目的保证操作的原子性
 */
public class SynTest3 implements Runnable{
    private int count = 0;

    public synchronized void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"count="+count++);

    }
    public static void main(String[] args) {
        SynTest3 thread = new SynTest3();
        for (int i = 0; i < 10; i++) {
            //开启一个子线程并启动它
        	new Thread(thread,"thread"+i).start();
        }

    }
}

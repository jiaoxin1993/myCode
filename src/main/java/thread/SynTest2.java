package thread;

/**
 * Created by admin on 2019/10/18.
 * synchronized
 * 同步方法 static
 * 静态同步方法 锁的的是类的类对象，这本代码中就是SynTest2类对象
 */
public class SynTest2 {
    private static int count = 0;
    public static synchronized void testsync4(){
        System.out.println(Thread.currentThread().getName()+"count="+count++);
    }
    public void testsync5(){
        synchronized (SynTest2.class){
            System.out.println(Thread.currentThread().getName()+"count="+count++);
        }
    }
}

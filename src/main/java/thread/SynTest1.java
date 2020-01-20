package thread;

/**
 * Created by admin on 2019/10/18.
 *  synchronized关键字
 *  锁对象 synchronized (this)和 synchronized修饰方法都是锁当前对象
 */
public class SynTest1 {
    private int count = 0;
    private Object o = new Object();
    public void testsync1(){
        synchronized (o){
            System.out.println(Thread.currentThread().getName()+"count="+count++);
        }
    }
    public void testsync2(){
        synchronized (this){
            System.out.println(Thread.currentThread().getName()+"count="+count++);
        }
    }
    public synchronized void testsync3(){
        System.out.println(Thread.currentThread().getName()+"count="+count++);
    }
}

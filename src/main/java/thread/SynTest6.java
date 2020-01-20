package thread;

/**
 * Created by admin on 2019/10/18.
 * synchronized关键字
 * 同步方法，调用其他同步方法
 * 锁可重入：同一个线程，多次调用同步代码，锁定同一个锁对象，可重入
 */
public class SynTest6 {
    public synchronized void m1(){
        System.out.println("m1方法执行-------start");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
        System.out.println("m1方法执行-------end");
    }
    public synchronized void m2(){
        System.out.println("m2方法执行-------start");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2方法执行-------end");
    }
    public static void main(String[] args) {
        SynTest6 s = new SynTest6();
        s.m1();

    }
}

package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by admin on 2019/10/21.
 * ReentrantLock 可重入锁  类似synchronized
 * 可打断锁 lockInterruptibly
 * 阻塞状态： 包括普通阻塞、等待队列、锁池队列
 *      普通队列：sleep()   thread.interrupt()方法，可以打断阻塞状态，抛出异常
 *      等待队列：wait()方法被调用  也是一种阻塞状态  只能由notify唤醒
 *      锁池队列：无法获取锁标记  不是所有的锁池对象都可以被打断
 *        ReentrantLock的 lock()方法，获取锁标记时，如果需要阻塞等待标记，无法被打断
 *        ReentrantLock的 lockInterruptibly() 获取锁标记时，如果需要阻塞等待标记，可以被打断
 */
public class ReentranLockTest3 {
    Lock lock= new ReentrantLock();
    void m1(){
        try {
            lock.lock();//加锁  形似 synchronized域的开始和结束
            System.out.println("m1()===start");
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+i);
                Thread.sleep(100);
            }
            System.out.println("m1()===end");
        } catch (Exception e) {
        	e.printStackTrace();
        }finally {
            lock.unlock();//解锁
        }
    }
    void m2(){
        boolean isLocked = false;
        try {
            lock.lockInterruptibly(); //阻塞等待锁  可尝试打断 可以被其他的线程打断阻塞状态
            System.out.println("m2() Method");
        } catch (Exception e) {
            System.out.println("m2() Method interrupt");
        }finally {
            //尝试锁在解除标记时，一定要判断是否获取锁标记
            if(isLocked){
                lock.unlock();//解锁
            }
        }
    }
    public static void main(String[] args) {
        final ReentranLockTest3 s =new ReentranLockTest3();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                s.m1();
            }
        },"jiao");
        t1.start();
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                s.m2();
            }
        },"xin");
        t2.start();
        t2.interrupt(); //打断线程休眠 ，非正常结束阻塞状态的线程，都会出现异常

    }
}

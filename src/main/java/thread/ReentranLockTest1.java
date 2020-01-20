package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by admin on 2019/10/21.
 * ReentrantLock 可重入锁  类似synchronized
 */
public class ReentranLockTest1 {
    Lock lock= new ReentrantLock();
    void m1(){
        try {
            lock.lock();//加锁  形似 synchronized域的开始和结束
            System.out.println("m1()===start");
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+i);
            }
            System.out.println("m1()===end");
        } catch (Exception e) {
        	e.printStackTrace();
        }finally {
            lock.unlock();//解锁
        }
    }
    void m2(){
        try {
            lock.lock();//加锁  形似 synchronized域的开始和结束
            System.out.println("m2()===start");
            for (int i = 0; i < 2; i++) {
                System.out.println(Thread.currentThread().getName()+i);
            }
            System.out.println("m2()===end");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();//解锁
        }
    }
    public static void main(String[] args) {
        final ReentranLockTest1 s =new ReentranLockTest1();
        new Thread(new Runnable() {
            public void run() {
                s.m1();
            }
        },"jiao").start();
        new Thread(new Runnable() {
            public void run() {
                s.m2();
            }
        },"xin").start();

    }
}

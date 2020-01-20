package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by admin on 2019/10/21.
 * ReentrantLock 可重入锁  类似synchronized
 * 尝试锁 lock.tryLock()
 */
public class ReentranLockTest2 {
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
            //尝试锁  如果有锁，无法获取锁标记 返回false
            //如果获取锁标记  返回true
            //isLocked = lock.tryLock();

            //阻塞尝试锁 阻塞参数代表时长，尝试获取锁标记  如果超时，不等待，直接返回
            isLocked = lock.tryLock(5, TimeUnit.SECONDS);
            if(isLocked){
                System.out.println("执行m2()的同步代码块");
            }else{
                System.out.println("执行m2()的非同步代码块");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //尝试锁在解除标记时，一定要判断是否获取锁标记
            if(isLocked){
                lock.unlock();//解锁
            }
        }
    }
    public static void main(String[] args) {
        final ReentranLockTest2 s =new ReentranLockTest2();
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

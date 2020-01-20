package thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by admin on 2019/10/21.
 * ReentrantLock 可重入锁  类似synchronized
 * 公平锁
 */
public class ReentranLockTest4 {
    public static void main(String[] args) {
        TestReentranLock t =new ReentranLockTest4.TestReentranLock();
        new Thread(t,"L1").start();
        new Thread(t,"L2").start();
        new Thread(t,"L3").start();
    }
    static class  TestReentranLock extends Thread{
        //定义一个公平锁
        private  ReentrantLock lock = new ReentrantLock(true);
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    lock.lock();//加锁  形似 synchronized域的开始和结束
                        System.out.println(Thread.currentThread().getName()+i);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();//解锁
                }
            }
        }
    }

}

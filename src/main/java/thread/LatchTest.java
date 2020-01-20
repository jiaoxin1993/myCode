package thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by admin on 2019/10/21.
 * 门闩 CountDownLatch（栏持）
 * 可以和锁混合使用，或者替代锁的功能
 * 在门闩未完全开放之前等待，当门闩完全开放后执行
 * 避免锁的效率低下问题
 */
public class LatchTest {
    CountDownLatch latch = new CountDownLatch(5);//上5层闩
    void m1(){
        try {
            latch.await(); //等待门闩开放
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m1()");
    }
    void m2(){
        for (int i = 0; i < 10; i++) {
        	if(latch.getCount() != 0){//即还存在门闩
                latch.countDown();//使得门闩数减1
            }
            System.out.println("m2()==="+i);
        }
    }
    public static void main(String[] args) {
        final LatchTest l =new LatchTest();
        new Thread(new Runnable() {
            public void run() {
                l.m1();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                l.m2();
            }
        }).start();

    }
}

package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by admin on 2019/10/21.
 * AtomicXXX（e 套美克）
 * 同步类型
 * 原子操作类型，其中的每个方法都是原子操作，可以保证线程安全
 */
public class AtomicTest1 {
    AtomicInteger count = new AtomicInteger();
    void m(){
        for (int i = 0; i < 10000; i++) {
            count.incrementAndGet();
        }

    }
    public static void main(String[] args) {
        final AtomicTest1 a = new AtomicTest1();
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(new Runnable() {
                public void run() {
                    a.m();
                }
            }));
        }
        for (Thread thread:threads){
            thread.start();
        }
        for (Thread thread:threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(a.count);
    }
}

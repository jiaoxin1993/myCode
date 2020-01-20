package thread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2019/10/21.
 * volatile关键字
 * volatile的非原子性问题
 * volatile 只能保证可见性，不能保证原子性
 * 不是加锁问题，只是内存数据可见
 */
public class VolTest2 {
    volatile int count = 0;
    void m(){
        for (int i = 0; i < 10000; i++) {
            count++;
        }

    }
    public static void main(String[] args) {
        final VolTest2 t = new VolTest2();
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
        	threads.add(new Thread(new Runnable() {
                public void run() {
                    t.m();
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
        System.out.println(t.count);
    }
}
